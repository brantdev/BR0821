package main.java;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import static java.time.temporal.TemporalAdjusters.firstInMonth;

public class RentalAgreement {
    private String toolCode;
    private int rentalDayCount;
    private int discountPercent;
    private String checkoutDate;
    private String toolType;
    private String toolBrand;
    private String formattedStart;
    private String formattedDueDate;
    private int chargeableDays;
    private  double prediscountCharge;
    private double discountAmount;
    private double finalCharge;
    private double toolDailyCharge;
    private LocalDate localCheckoutStart;
    private LocalDate localCheckoutEnd;

    public RentalAgreement(String toolCode, int rentalDayCount, int discountPercent, String checkoutDate) {
        if(rentalDayCount < 1){
            throw new IllegalArgumentException("Rental days can't be less than 1");
        }
        if(discountPercent < 0 ){
            throw new IllegalArgumentException("Discount percent can't be less than 0");
        }
        if(discountPercent > 100){
            throw new IllegalArgumentException("Discount percent can't be greater than 100");
        }
        toolCodeCheck(toolCode);
        this.toolCode = toolCode;
        this.rentalDayCount = rentalDayCount;
        this.discountPercent = discountPercent;
        this.checkoutDate = checkoutDate;
        parseCheckoutDate();
        setToolValues();
        setChargeDays();
        setPrediscountCharge();
        setDiscountAmount();
        setFinalCharge();
    }

    public void toolCodeCheck(String toolCode){
        if(!toolCode.equals("LADW") && !toolCode.equals("CHNS") && !toolCode.equals("JAKR") && !toolCode.equals("JAKD")){
            throw new IllegalArgumentException("Tool type was not found!");
        }
    }

    public void parseCheckoutDate(){
        String checkoutDate = getCheckoutDate();
        int rentalDayCount = getRentalDayCount();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("M/d/yy");
        LocalDate localDate = LocalDate.parse(checkoutDate,dateTimeFormatter);
        this.localCheckoutStart = localDate;

        //formatted checkout date
        dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yy");
        this.formattedStart=dateTimeFormatter.format(localDate);

        //This is the new due date
        localDate= localDate.plusDays(rentalDayCount);
        this.localCheckoutEnd = localDate;
        this.formattedDueDate = dateTimeFormatter.format(localDate);
    }

    public void setToolValues(){
        String toolCode = getToolCode();
        if(toolCode.equals("LADW")){
            this.toolType="Ladder";
            this.toolBrand="Werner";
            this.toolDailyCharge=1.99;
        }
        if(toolCode.equals("CHNS")){
            this.toolType="Chainsaw";
            this.toolBrand="Stihl";
            this.toolDailyCharge=1.49;
        }
        if(toolCode.equals("JAKR") || toolCode.equals("JAKD")){
            this.toolType="Jackhammer";
            this.toolDailyCharge=2.99;
        }
        if(toolCode.equals("JAKR")){
            this.toolBrand="Ridgid";
        }
        if(toolCode.equals("JAKD")){
            this.toolBrand="DeWalt";
        }
    }

    public void setChargeDays() {
        LocalDate startDate = getLocalCheckoutStart();
        LocalDate endDate = getLocalCheckoutEnd();
        startDate = startDate.plusDays(1);
        endDate = endDate.plusDays(1);

        //Ladder: Weekends are charged. No holiday charges
        if (getToolCode().equals("LADW")) {
            for(LocalDate start = startDate; start.isBefore(endDate); start = start.plusDays(1)){
                DayOfWeek dayOfWeek = start.getDayOfWeek();
                Month month = start.getMonth();
                LocalDate firstMonday = start.with(firstInMonth(DayOfWeek.MONDAY));

                //Checking for Labor Day (First Monday in September). Skip it.
                if(month.equals(Month.SEPTEMBER) && start.equals(firstMonday)){
                    continue;
                }

                //4th of July not charged
                if(month.equals(Month.JULY) ){
                    if(start.getDayOfMonth() == 3 && dayOfWeek.equals(dayOfWeek.FRIDAY)){
                        continue;   //Skip Friday
                    }
                    if(start.getDayOfMonth() == 5 && dayOfWeek.equals(dayOfWeek.MONDAY)){
                        continue;   //Skip Monday
                    }
                    if( (start.getDayOfMonth() == 4 && !dayOfWeek.equals(dayOfWeek.SATURDAY)) && (start.getDayOfMonth() == 4 && !dayOfWeek.equals(dayOfWeek.SUNDAY)) ){
                        continue;   //Skip weekdays where the 4th falls on
                    }
                }
                this.chargeableDays++;
            }
        }
        //Chainsaw: No weekend charges. Holdidays are charged
        if (getToolCode().equals("CHNS")) {
            for(LocalDate start = startDate; start.isBefore(endDate); start = start.plusDays(1)){
                DayOfWeek dayOfWeek = start.getDayOfWeek();
                Month month = start.getMonth();

                //4th of July is charged
                if(month.equals(Month.JULY) ){
                    if(start.getDayOfMonth() == 3 && dayOfWeek.equals(dayOfWeek.FRIDAY)){
                        this.chargeableDays++;  //Charge Friday
                        continue;
                    }
                    if(start.getDayOfMonth() == 5 && dayOfWeek.equals(dayOfWeek.MONDAY)){
                        this.chargeableDays++;  //Charge Monday
                        continue;
                    }
                }
                //Skip Weekends
                if(dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY ){
                    continue;
                }
                this.chargeableDays++;
            }
        }
        //Jackhammer: No weekend charges. No holiday charges
        if (getToolCode().equals("JAKR") || getToolCode().equals("JAKD")) {
            for(LocalDate start = startDate; start.isBefore(endDate); start = start.plusDays(1)){
                DayOfWeek dayOfWeek = start.getDayOfWeek();
                Month month = start.getMonth();
                LocalDate firstMonday = start.with(firstInMonth(DayOfWeek.MONDAY));

                //Checking for Labor Day (First Monday in September) and skip it.
                if(month.equals(Month.SEPTEMBER) && start.equals(firstMonday)){
                    continue;
                }
                //4th of July not charged
                if(month.equals(Month.JULY) ){
                    if(start.getDayOfMonth() == 3 && dayOfWeek.equals(dayOfWeek.FRIDAY)){
                        continue;   //Skip Friday
                    }
                    if(start.getDayOfMonth() == 5 && dayOfWeek.equals(dayOfWeek.MONDAY)){
                        continue;   //Skip Monday
                    }
                    if( (start.getDayOfMonth() == 4 && !dayOfWeek.equals(dayOfWeek.SATURDAY)) && (start.getDayOfMonth() == 4 && !dayOfWeek.equals(dayOfWeek.SUNDAY)) ){
                        continue;   //Skip weekdays where the 4th falls on
                    }
                }
                //Skip Weekends
                if(dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY ){
                    continue;
                }
                this.chargeableDays++;
            }
        }
    }

    public void setDiscountAmount(){
        float tmp = (float) getDiscountPercent() / 100;
        this.discountAmount = getPrediscountCharge() * tmp;
        this.discountAmount = Math.round(this.discountAmount  * 100.0)/100.0;
    }

    public void setPrediscountCharge(){
        double tmpCharge = getChargeableDays() * getToolDailyCharge();
        this.prediscountCharge = Math.round(tmpCharge * 100.0)/100.0;
    }

    public void setFinalCharge(){
        this.finalCharge = getPrediscountCharge() - getDiscountAmount();
        this.finalCharge = Math.round(this.finalCharge  * 100.0)/100.0;
    }

    public LocalDate getLocalCheckoutStart() {
        return localCheckoutStart;
    }

    public LocalDate getLocalCheckoutEnd() {
        return localCheckoutEnd;
    }

    public int getRentalDayCount() {
        return rentalDayCount;
    }

    public String getToolCode() {
        return toolCode;
    }

    public int getChargeableDays() {
        return chargeableDays;
    }

    public String getCheckoutDate() {
        return checkoutDate;
    }

    public double getPrediscountCharge() {
        return prediscountCharge;
    }

    public int getDiscountPercent() {
        return discountPercent;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public double getToolDailyCharge() {
        return toolDailyCharge;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Rental Agreement: \n");
        sb.append("Tool code: ").append(toolCode).append("\n");
        sb.append("Tool type: ").append(toolType).append("\n");
        sb.append("Tool brand: ").append(toolBrand).append("\n");
        sb.append("Rental days: ").append(rentalDayCount).append("\n");
        sb.append("Checkout date: ").append(formattedStart).append("\n");
        sb.append("Due date: ").append(formattedDueDate).append("\n");
        sb.append("Daily rental charge: $").append(toolDailyCharge).append("\n");
        sb.append("Charge days: ").append(chargeableDays).append("\n");
        sb.append("Pre-discount charge: $").append(prediscountCharge).append("\n");
        sb.append("Discount percent: ").append(discountPercent).append("%\n");
        sb.append("Discount amount: $").append(discountAmount).append("\n");
        sb.append("Final charge: $").append(finalCharge);
        return sb.toString();
    }

    //Optional print method
    public void printAgreement(){
        System.out.println(toString());
    }

}


