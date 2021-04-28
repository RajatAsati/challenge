package com.db.awmd.challenge.domain;

import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BenificiaryAccount {

	@NotNull
	@NotEmpty
	private String accountTo;

	@NotNull
	@Min(value = 0, message = "Transfer amount must be positive.")
	private BigDecimal amount;

	public BenificiaryAccount(String accountTo) {
		this.accountTo = accountTo;
		this.amount = BigDecimal.ZERO;
	}

	@JsonCreator
	public BenificiaryAccount(@JsonProperty("accountTo") String accountTo, @JsonProperty("amount") BigDecimal amount) {
		this.accountTo = accountTo;
		this.amount = amount;
	}

	public String getAccountTo() {
		return accountTo;
	}

	public void setAccountTo(String accountTo) {
		this.accountTo = accountTo;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

}
