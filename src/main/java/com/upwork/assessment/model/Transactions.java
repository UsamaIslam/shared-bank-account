package com.upwork.assessment.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Jacksonized
@ToString
@Entity
@Table(name="TransactionDetails")
public class Transactions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="BeneficiaryName")
    private String beneficiaryName;

    @Column(name="BeneficiaryAccountNumber")
    private long beneficiaryAccountNumber;

    @Column(name="Amount")
    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "bank_account_id")
    private BankAccounts bankAccount;

//    @Override
//    public String toString() {
//        return "TransactionBean [Account Id =" + bankAccount.getAccountNumber() + ", beneficiaryName=" + beneficiaryName
//                + ", beneficiaryAccountNumber=" + beneficiaryAccountNumber + ", amount=" + amount + ", balanceAmount="
//                + bankAccount.getBalance() + "]";
//    }
}
