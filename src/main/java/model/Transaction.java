package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Builder
public class Transaction {
    private String userFrom;
    private Double money;
    private String userTo;

    public Transaction(String userFrom, Double money, String userTo) {
        this.userFrom = userFrom;
        this.money = money;
        this.userTo = userTo;
    }

    @Override
    public String toString(){
        return money +" from:"+userFrom+" to:" + userTo ;
    }

}
