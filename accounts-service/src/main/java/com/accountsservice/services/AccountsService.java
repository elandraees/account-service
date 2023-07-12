package com.accountsservice.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.accountsservice.controllers.beans.CreateAccountRequestBean;
import com.accountsservice.controllers.beans.CreateAccountResponseBean;
import com.accountsservice.controllers.beans.ResponseBean;
import com.accountsservice.models.User;
import com.accountsservice.utils.LogManager;
import jakarta.transaction.Transactional;

import com.accountsservice.models.Account;
import com.accountsservice.models.repo.AccountsRepo;
import com.accountsservice.utils.AbstractResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountsService extends AbstractService {

    @Autowired
    AccountsRepo accountsRepo;

    @Autowired
    UserService userService;

    /*
     * Read operations
     */

    public List<Account> findAllAccounts() {

        return accountsRepo.findAll();

    }

    public Account getAccountByAccountCode(String accountCode) {

        return accountsRepo.loadAccountByAccountCode(accountCode);
    }

    /*
     * Create operations
     */

    @Transactional
    public AbstractResult createAccount(Account account) throws Exception {

        AbstractResult result = new AbstractResult();
        Account duplicateAccount = getAccountByAccountCode(account.getAccountCode());
        if (duplicateAccount != null)
            result.addError("This is a duplicate account!!!");

        if (result.isSuccess()) {
            result = super.saveSingleObject(account, accountsRepo, true);

            if (result.isSuccess()) {
                Account theAccount = (Account) result.getResults().get(0);
                account.setId(theAccount.getId());
                createUserForAccount(account);
            }
        }

        return result;
    }

    @Transactional
    private void createUserForAccount(Account theAccount) throws Exception {

        User user = new User(theAccount.getId(), theAccount.getEmailAddress(), theAccount.getPassword(),
                theAccount.getFirstName(),
                theAccount.getLastName(), theAccount.getEmailAddress(), theAccount.getContactNumber(),
                User.PRIMARY_USER);

        userService.createUser(user);

    }

    public ResponseBean getAccount(String accountCode, AccountsService accountsService) {

        ResponseBean bean = new ResponseBean();

        Account account = accountsService.getAccountByAccountCode(accountCode);

        bean.setResult(Arrays.asList(account));

        return bean;
    }

    public ResponseBean getAllAccountsResponseBean(AccountsService accountsService) {

        ResponseBean bean = new ResponseBean();

        List<Account> accounts = accountsService.findAllAccounts();
        bean.setResult(accounts);

        return bean;
    }

    public ResponseBean createAccount(CreateAccountRequestBean requestBean, int accountTypeId, AccountsService accountsService) {

        Account account = new Account(requestBean.firstName, requestBean.lastName, requestBean.businessName,
                requestBean.physicalAddress, requestBean.registrationNumber,
                requestBean.emailAddress, requestBean.taxNumber, requestBean.contactNumber,
                requestBean.alternateContactNumber,
                requestBean.cryptBankAccountNumber, requestBean.debitDay, requestBean.cryptCreditCardNumber,
                requestBean.creditCardCvv,
                requestBean.creditCardExpiryDate, requestBean.password);
        account.setTypeId(accountTypeId);

        ResponseBean bean = new ResponseBean();
        AbstractResult result = new AbstractResult();
        try {
            result = accountsService.createAccount(account);

            if (result.isSuccess()) {
                Account theAccount = (Account) result.getResults().get(0);
                List<Object> responseResults = new ArrayList<>();
                responseResults
                        .add(new CreateAccountResponseBean(theAccount.getAccountCode(), theAccount.getFullName()));
                bean.setDetails(true, "", responseResults);
            } else {
                bean.setDetailsFromResult(result);
            }
        } catch (Exception e) {
            result.addError(e.getMessage());
            bean.setDetailsFromResult(result);
            LogManager.addError(e.getMessage(), this.getClass());
        }

        return bean;
    }

}
