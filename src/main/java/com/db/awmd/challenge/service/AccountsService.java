package com.db.awmd.challenge.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.domain.BenificiaryAccount;
import com.db.awmd.challenge.repository.AccountsRepository;

import lombok.Getter;

@Service
public class AccountsService {

	@Getter
	private final AccountsRepository accountsRepository;

	@Autowired
	public AccountsService(AccountsRepository accountsRepository) {
		this.accountsRepository = accountsRepository;
	}

	public void createAccount(Account account) {
		this.accountsRepository.createAccount(account);
	}

	public Account getAccount(String accountId) {
		return this.accountsRepository.getAccount(accountId);
	}

	public List<Account> getAllAccounts() {
		return this.accountsRepository.getAllAccounts();
	}

	public void transferMoney(String accountFrom, String accountTo, BenificiaryAccount benificiaryAccount) {
		accountsRepository.transferMoney(accountFrom, accountTo, benificiaryAccount);
	}
}
