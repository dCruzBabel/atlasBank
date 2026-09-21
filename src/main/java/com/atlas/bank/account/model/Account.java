package com.atlas.bank.account.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.stereotype.Service;

@Entity
@Getter
@Setter
@Service
@NoArgsConstructor
@AllArgsConstructor
public class Account {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String accountNumber;
  private String ownerName;
  private String email;
  private String type; //SAVING. CHECKING
  private BigDecimal balance; //ACTIVE,CLOSED, FROZEN
  private String status;
  private LocalDateTime createdAt;

  @PrePersist
  public void prePersist() {
    this.createdAt = LocalDateTime.now();
    if (status == null) status = "PENDING";
    if (balance == null) balance = BigDecimal.ZERO;
  }

  @Override
  public final boolean equals(Object o) {
    if (this == o) return true;
    if (o == null) return false;
    Class<?> oEffectiveClass = o instanceof HibernateProxy ?
        ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
    Class<?> thisEffectiveClass = this instanceof HibernateProxy ?
        ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() :
        this.getClass();
    if (thisEffectiveClass != oEffectiveClass) return false;
    Account account = (Account) o;
    return getId() != null && Objects.equals(getId(), account.getId());
  }

  @Override
  public final int hashCode() {
    return this instanceof HibernateProxy ?
        ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() :
        getClass().hashCode();
  }
}
