package Domain.Entities;
import java.util.List;
import java.util.ArrayList;

public class ContactEntity {
    
    private int cId;
    private String cName;
    private List<AddressEntity> addr = new ArrayList<>();
    private List<PhoneEntity> ph = new ArrayList<>();
    private List<ContactGrpEntity> grp = new ArrayList<>();

    public int getcId() {
        return cId;
    }
    public void setcId(int cId) {
        this.cId = cId;
    }
    public String getcName() {
        return cName;
    }
    public void setcName(String cName) {
        this.cName = cName;
    }

    public List<AddressEntity> getAddr() {
        return addr;
    }
    public void setAddr(List<AddressEntity> addr) {
        this.addr = addr;
    }
    public List<PhoneEntity> getPh() {
        return ph;
    }
    public void setPh(List<PhoneEntity> ph) {
        this.ph = ph;
    }
    public List<ContactGrpEntity> getGrp() {
        return grp;
    }
    public void setGrp(List<ContactGrpEntity> grp) {
        this.grp = grp;
    }
    
}
