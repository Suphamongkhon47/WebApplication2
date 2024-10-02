package Mon;

import java.math.BigDecimal;

public class Clothes {
    private String name;
    private String email;
    private String clothesType;
    private String clothesColor;
    private String paymentType;
    private BigDecimal clothesPrice;
    
    public Clothes() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getClothesType() {
        return clothesType;
    }

    public void setClothesType(String clothesType) {
        this.clothesType = clothesType;
    }

    public String getClothesColor() {
        return clothesColor;
    }

    public void setClothesColor(String clothesColor) {
        this.clothesColor = clothesColor;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public BigDecimal getClothesPrice() {
        return clothesPrice;
    }

    public void setClothesPrice(BigDecimal clothesPrice) {
        this.clothesPrice = clothesPrice;
    }
}
