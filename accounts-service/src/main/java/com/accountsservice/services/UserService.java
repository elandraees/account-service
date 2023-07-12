package com.accountsservice.services;

import java.util.ArrayList;
import java.util.List;

import com.accountsservice.controllers.beans.CreateUserResponseBean;
import com.accountsservice.controllers.beans.ResponseBean;
import com.accountsservice.controllers.beans.UserUpdateRequestBean;
import com.accountsservice.models.Account;
import com.accountsservice.models.User;
import com.accountsservice.models.repo.AccountsRepo;
import com.accountsservice.models.repo.UsersRepo;
import com.accountsservice.utils.AbstractResult;
import com.accountsservice.utils.LogManager;
import com.accountsservice.utils.ValidationUtil;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService extends AbstractService implements UserDetailsService {

    @Autowired
    UsersRepo usersRepo;

    @Autowired
    AccountsRepo accountsRepo;

    // Used by authentication manager to load username and password pair
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = usersRepo.loadUsername(username);

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                new ArrayList<>());
    }

    @Transactional
    public AbstractResult createUser(User user) throws Exception {

        // First check if accountId is set on user
        if (user.getAccountId() == 0 && ValidationUtil.isObjectNotNullOrEmptyOrZero(user.getAccountCode())) {
            Account theAccount = accountsRepo.loadAccountByAccountCode(user.getAccountCode());
            if (theAccount != null)
                user.setAccountId(theAccount.getId());
        }

        AbstractResult result = super.saveSingleObject(user, usersRepo, true);
        return result;

    }

    public User loadByUsername(String username) {

        return usersRepo.loadUsername(username);
    }

    public List<User> findAllUsers(long accountId)
    {
        return usersRepo.loadUsersForAccount(accountId);
    }


    public AbstractResult updateUser(User user) throws Exception {

        AbstractResult result = super.saveSingleObject(user, usersRepo, false);
        return result;
    }

    public ResponseBean getUsersResponseBean(String accountCode, UserService userService, AccountsService accountsService) {

        ResponseBean bean = new ResponseBean();

        Account account = accountsService.getAccountByAccountCode(accountCode);

        if (account != null) {
            List<User> users = userService.findAllUsers(account.getId());
            bean.setResult(users);
        } else {
            bean.setSuccess(false);
            bean.setMessage("Cannot find account");
        }

        return bean;
    }

    public ResponseBean createUser(User user, UserService userService) {

        ResponseBean bean = new ResponseBean();
        AbstractResult result = new AbstractResult();
        try {
            user.setTypeId(User.SECONDARY_USER);
            result = userService.createUser(user);

            if (result.isSuccess()) {
                User theUser = (User) result.getResults().get(0);
                List<Object> responseResults = new ArrayList<Object>();
                responseResults.add(new CreateUserResponseBean(theUser.getEmailAddress(), theUser.getFullName()));
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

    public ResponseBean updateUser(UserUpdateRequestBean user, UserService userService) {

        ResponseBean bean = new ResponseBean();
        User newUser = userService.loadByUsername(user.username);

        if (newUser == null) {
            bean.setDetails(false, "No user found", null);
            return bean;
        }

        // Set new user details
        newUser.update(user);
        AbstractResult result = null;

        try {

            result = userService.updateUser(newUser);
            if (result.isSuccess()) {
                List<Object> responseResults = new ArrayList<Object>();
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
