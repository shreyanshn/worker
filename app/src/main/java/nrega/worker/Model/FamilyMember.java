package nrega.worker.Model;

/**
 * Created by HP on 22-01-2018.
 */

public class FamilyMember {
    private String name;
    private String DOB;
    private String bankName;
    private String gender;
    private String accountNo;
    private String aadharNo;


    public FamilyMember(String name, String DOB, String bankName, String gender, String accountNo, String aadharNo) {
        this.name = name;
        this.DOB = DOB;
        this.bankName = bankName;
        this.gender = gender;
        this.accountNo = accountNo;
        this.aadharNo = aadharNo;
    }

    public FamilyMember(String name, String DOB, String bankName) {
        this.name = name;
        this.DOB = DOB;
        this.bankName = bankName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getAadharNo() {
        return aadharNo;
    }

    public void setAadharNo(String aadharNo) {
        this.aadharNo = aadharNo;
    }

    public String getGender() {

        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
