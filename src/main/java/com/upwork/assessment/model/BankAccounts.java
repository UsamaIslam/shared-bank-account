package com.upwork.assessment.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "bank_accounts")
public class BankAccounts {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "title")
  private String title;

  @Column(name = "AccountNumber")
  private String accountNumber;

  @Column(name = "active")
  private boolean active;

  @ManyToMany(fetch = FetchType.LAZY,
      cascade = {
          CascadeType.PERSIST,
          CascadeType.MERGE
      })
  @JoinTable(name = "bank_account_users",
        joinColumns = { @JoinColumn(name = "BANK_ACCOUNT_ID") },
        inverseJoinColumns = { @JoinColumn(name = "USER_ID") })
  private Set<Users> users = new HashSet<>();


  public BankAccounts(String title, String accountNumber, boolean active) {
    this.title = title;
    this.accountNumber = accountNumber;
    this.active = active;
  }
  
  public void addUserToBankAccount(Users users) {
    this.users.add(users);
    users.getBankAccounts().add(this);
  }
  
  public void removeUserFromBankAccount(long userId) {
    Users user = this.users.stream().filter(t -> t.getId() == userId).findFirst().orElse(null);
    if (user != null) {
      this.users.remove(user);
      user.getBankAccounts().remove(this);
    }
  }

  @Override
  public String toString() {
    return "Bank [id=" + id + ", title=" + title + ", desc=" + accountNumber + ", published=" + active + "]";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    BankAccounts bankAccounts = (BankAccounts) o;
    return Objects.isNull(id) && Objects.equals(id, bankAccounts.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
