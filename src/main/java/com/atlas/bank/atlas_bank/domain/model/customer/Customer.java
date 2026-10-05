package com.atlas.bank.atlas_bank.domain.model.customer;

import com.atlas.bank.atlas_bank.domain.model.shared.Email;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class Customer {

  @EqualsAndHashCode.Include
  private Long id;
  private String name;
  private Email email;
  private CustomerStatus status;
  private LocalDateTime createdAt;

  public boolean isActive() {
    return this.status == CustomerStatus.ACTIVE;
  }

  public void initDefaults() {
    if (status == null) status = CustomerStatus.ACTIVE;
    if (createdAt == null) createdAt = LocalDateTime.now();
  }
}