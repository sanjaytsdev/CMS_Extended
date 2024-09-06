package Domain.DTO;

import java.util.List;
import java.util.ArrayList;

public class ContactDTO {
    
    private int cId;
    private String cName;
    private List<AddressDTO> addr = new ArrayList<>();
    private List<PhoneDTO> ph = new ArrayList<>();
    private List<ContactGrpDTO> grp = new ArrayList<>();
    
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
    public List<AddressDTO> getAddr() {
        return addr;
    }
    public void setAddr(List<AddressDTO> addr) {
        this.addr = addr;
    }
    public List<PhoneDTO> getPh() {
        return ph;
    }
    public void setPh(List<PhoneDTO> ph) {
        this.ph = ph;
    }
    public List<ContactGrpDTO> getGrp() {
        return grp;
    }
    public void setGrp(List<ContactGrpDTO> grp) {
        this.grp = grp;
    }

    
   
}
