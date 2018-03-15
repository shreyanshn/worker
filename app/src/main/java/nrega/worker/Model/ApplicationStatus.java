package nrega.worker.Model;

import java.util.Date;


public class ApplicationStatus {
    private String name,worklocation,status,startdate,enddate;

   public ApplicationStatus(String name, String worklocation, String status, String startdate, String enddate){
       this.name=name;
       this.worklocation=worklocation;
       this.status=status;
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
    public String getWorklocation()
    {
        return worklocation;
    }
    public void setWorklocation(String worklocation)
    {
        this.worklocation=worklocation;
    }
    public String getStatus()
    {
        return status;
    }
    public void setStatus(String status)
    {
        this.status=status;
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
