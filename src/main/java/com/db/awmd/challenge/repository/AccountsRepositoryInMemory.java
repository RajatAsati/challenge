package com.db.awmd.challenge.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.domain.BenificiaryAccount;
import com.db.awmd.challenge.exception.AccountNotFoundException;
import com.db.awmd.challenge.exception.AmountNotCorrectException;
import com.db.awmd.challenge.exception.DuplicateAccountIdException;
import com.db.awmd.challenge.exception.InsufficientBalanceException;
import com.db.awmd.challenge.service.NotificationService;

@Repository
public class AccountsRepositoryInMemory implements AccountsRepository {

	@Autowired
	private NotificationService notificationService;

	private final Map<String, Account> accounts = new ConcurrentHashMap<>();

	@Override
	public void createAccount(Account account) throws DuplicateAccountIdException {
		Account previousAccount = accounts.putIfAbsent(account.getAccountId(), account);
		if (previousAccount != null) {
			throw new DuplicateAccountIdException("Account id " + account.getAccountId() + " already exists!");
		}
	}

	@Override
	public Account getAccount(String accountId) {
		if (accounts.get(accountId) == null) {
			throw new AccountNotFoundException("Account id " + accountId + " not exists!");
		} else {
			return accounts.get(accountId);
		}
	}

	@Override
	public void clearAccounts() {
		accounts.clear();
	}

	@Override
	public List<Account> getAllAccounts() {
		return accounts.values().stream().collect(Collectors.toList());
	}

	@Override
	public void transferMoney(String accountFrom, String accountTo, BenificiaryAccount benificiaryAccount)
			throws AmountNotCorrectException {

		if (benificiaryAccount.getAmount().intValue() > 0) {

			Account payeeAccount = getAccount(accountFrom);
			Account payableAccount = getAccount(accountTo);

			if (payeeAccount.getBalance().compareTo(benificiaryAccount.getAmount()) == 1) {
				BigDecimal totalAmount = payableAccount.getBalance().add(benificiaryAccount.getAmount());
				payableAccount.setBalance(totalAmount);

				BigDecimal remainingAmount = payeeAccount.getBalance().subtract(benificiaryAccount.getAmount());
				payeeAccount.setBalance(remainingAmount);

				notificationService.notifyAboutTransfer(payeeAccount,
						benificiaryAccount.getAmount() + " amount has been transferd.");
				notificationService.notifyAboutTransfer(payableAccount,
						benificiaryAccount.getAmount() + " amount has been transferd.");

			} else {
				throw new InsufficientBalanceException("Insufficient Balance in your account.");
			}

		} else {
			throw new AmountNotCorrectException("Amount should be positive.");
		}

	}

}
