package com.db.awmd.challenge.repository;

import java.util.List;

import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.domain.BenificiaryAccount;
import com.db.awmd.challenge.exception.DuplicateAccountIdException;

public interface AccountsRepository {

	void createAccount(Account account) throws DuplicateAccountIdException;

	Account getAccount(String accountId);

	void clearAccounts();

	List<Account> getAllAccounts();

	void transferMoney(String accountFrom, String accountTo, BenificiaryAccount benificiaryAccount);
}
