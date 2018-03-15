package nrega.worker.Model;

import java.util.Date;


public class PaymentStatus {
    private String name,paystatus,startdate,enddate;

    public PaymentStatus(String name,String paystatus,String startdate,String enddate){
        this.name=name;
        this.paystatus=paystatus;
        this.startdate=startdate;
        this.enddate=enddate;
    }

    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name=name;
    }
    public String getPaystatus()
    {
        return paystatus;
    }
    public void setPayStatus(String status)
    {
        this.paystatus=paystatus;
    }
    public String getStartdate()
    {
        return startdate;
    }
    public void setStartdate(String startdate)
    {
        this.startdate=startdate;
    }
    public String getEnddate()
    {
        return enddate;
    }
    public void setEnddate(String enddate)
    {
        this.enddate=enddate;
    }

}

