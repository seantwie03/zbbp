package me.seantwiehaus.zbbp.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import me.seantwiehaus.zbbp.domain.ItemType;
import me.seantwiehaus.zbbp.domain.Transaction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class TransactionRequest {
  @NotNull
  private ItemType type;
  @NotNull
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate date;
  @NotBlank
  private String merchant;
  @NotNull
  @Min(0)
  private int amount;
  private Long lineItemId;
  private String description;

  public Transaction convertToTransaction() {
    return new Transaction(
        type,
        date,
        merchant,
        amount,
        lineItemId,
        description);
  }
}
