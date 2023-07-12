package com.accountsservice.controllers.api.v1;

import com.accountsservice.controllers.beans.CreateAccountRequestBean;
import com.accountsservice.controllers.beans.ResponseBean;
import com.accountsservice.controllers.beans.UserUpdateRequestBean;
import com.accountsservice.controllers.beans.abstractdata.GetAbstractDataResponseBean;
import com.accountsservice.models.Account;
import com.accountsservice.services.AccountsService;
import com.accountsservice.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("/api/v1/account")
public class AccountsController {

    @Autowired
    AccountsService accountsService;

    @Autowired
    UserService userService;

    @Autowired
    private HttpServletRequest request;


    /*
    Create business account
     */
    @PostMapping("/create/business/account")
    public ResponseBean createCustomerAccount(@RequestBody CreateAccountRequestBean requestBean) {

        return accountsService.createAccount(requestBean, Account.ACCOUNT_TYPE_BUSINESS, accountsService);
    }

    /*
    Get account details
     */
    @GetMapping("/details/{accountCode}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseBean getAccount(@PathVariable("accountCode") String accountCode) {

        ResponseBean bean = accountsService.getAccount(accountCode, accountsService);
        Account account = (Account)bean.getResult().get(0);

        // Convert normal account response to one defined in the entities class
        bean.setResult(Arrays.asList(new GetAbstractDataResponseBean(account)));

        return bean;
    }

    /*
    Get users of an account
     */
    @GetMapping("/users/{accountCode}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseBean getUsers(@PathVariable("accountCode") String accountCode) {

        return userService.getUsersResponseBean(accountCode, userService, accountsService);
    }

    @PostMapping("/update/user")
    public ResponseBean updateUser(@RequestBody UserUpdateRequestBean user) {

        return userService.updateUser(user, userService);
    }
}
