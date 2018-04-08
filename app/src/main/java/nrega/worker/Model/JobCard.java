package nrega.worker.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Shreyansh on 3/12/2018.
 */

public class JobCard implements Parcelable{
    private String JobCardNumber;
    private String FamilyId;
    private String Head;
    private String Father;
    private String Category;
    private String Address;
    private String Village;
    private String Panchayat;
    private String Block;
    private String District;
    private int daysLeft;

    public JobCard(String jobCardNumber, String familyId, String head, String father, String category, String address, String village, String panchayat, String block, String district,int daysLeft) {
        JobCardNumber = jobCardNumber;
        FamilyId = familyId;
        Head = head;
        Father = father;
        Category = category;
        Address = address;
        Village = village;
        Panchayat = panchayat;
        Block = block;
        District = district;
        this.daysLeft = daysLeft;
    }

    protected JobCard(Parcel in) {
        JobCardNumber = in.readString();
        FamilyId = in.readString();
        Head = in.readString();
        Father = in.readString();
        Category = in.readString();
        Address = in.readString();
        Village = in.readString();
        Panchayat = in.readString();
        Block = in.readString();
        District = in.readString();
        daysLeft = in.readInt();
    }

    public static final Creator<JobCard> CREATOR = new Creator<JobCard>() {
        @Override
        public JobCard createFromParcel(Parcel in) {
            return new JobCard(in);
        }

        @Override
        public JobCard[] newArray(int size) {
            return new JobCard[size];
        }
    };

    public String getJobCardNumber() {
        return JobCardNumber;
    }

    public void setJobCardNumber(String jobCardNumber) {
        JobCardNumber = jobCardNumber;
    }

    public String getFamilyId() {
        return FamilyId;
    }

    public void setFamilyId(String familyId) {
        FamilyId = familyId;
    }

    public String getHead() {
        return Head;
    }

    public void setHead(String head) {
        Head = head;
    }

    public String getFather() {
        return Father;
    }

    public void setFather(String father) {
        Father = father;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getVillage() {
        return Village;
    }

    public void setVillage(String village) {
        Village = village;
    }

    public String getPanchayat() {
        return Panchayat;
    }

    public void setPanchayat(String panchayat) {
        Panchayat = panchayat;
    }

    public String getBlock() {
        return Block;
    }

    public void setBlock(String block) {
        Block = block;
    }

    public String getDistrict() {
        return District;
    }

    public void setDistrict(String district) {
        District = district;
    }

    public int getDaysLeft() {
        return daysLeft;
    }

    public void setDaysLeft(int daysLeft) {
        this.daysLeft = daysLeft;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(JobCardNumber);
        dest.writeString(FamilyId);
        dest.writeString(Head);
        dest.writeString(Father);
        dest.writeString(Category);
        dest.writeString(Address);
        dest.writeString(Village);
        dest.writeString(Panchayat);
        dest.writeString(Block);
        dest.writeString(District);
        dest.writeInt(daysLeft);
    }
}
