package Domain.Transfer;

import java.util.ArrayList;
import java.util.List;

import Domain.DTO.AddressDTO;
import Domain.DTO.ContactDTO;
import Domain.DTO.ContactGrpDTO;
import Domain.DTO.PhoneDTO;
import Domain.Entities.AddressEntity;
import Domain.Entities.ContactEntity;
import Domain.Entities.ContactGrpEntity;
import Domain.Entities.PhoneEntity;

public class TransferValues {

    public static PhoneEntity fromPhoneDto(PhoneDTO pdto) {
        PhoneEntity ph = new PhoneEntity();
        ph.setPid(pdto.getPid());
        ph.setCid(pdto.getCid());
        ph.setPh(pdto.getPh());
        return ph;
    }

    public static PhoneDTO toPhoneDto(PhoneEntity ph) {
        PhoneDTO pdto = new PhoneDTO();
        pdto.setPid(ph.getPid());
        pdto.setCid(ph.getCid());
        pdto.setPh(ph.getPh());
        return pdto;
    }

    public static AddressEntity fromAddressDto(AddressDTO adto) {
        AddressEntity addr = new AddressEntity();
        addr.setAid(adto.getAid());
        addr.setCid(adto.getCid());
        addr.setAddress(adto.getAddr());
        return addr;
    }

    public static AddressDTO toAddressDto(AddressEntity addr) {
        AddressDTO adto = new AddressDTO();
        adto.setAid(addr.getAid());
        adto.setCid(addr.getCid());
        adto.setAddr(addr.getAddress());
        return adto;
    }
    
    public static ContactGrpEntity fromGroupDto(ContactGrpDTO grpdto) {
        ContactGrpEntity grp = new ContactGrpEntity();
        grp.setGrpId(grpdto.getGrpId());
        grp.setGrpName(grpdto.getGrpName());
        grp.setGrpDesc(grpdto.getGrpDesc());
        return grp;
    }

    public static ContactGrpDTO toContactGrpDto(ContactGrpEntity grp) {
        ContactGrpDTO grpdto = new ContactGrpDTO();
        grpdto.setGrpId(grp.getGrpId());
        grpdto.setGrpDesc(grp.getGrpDesc());
        grpdto.setGrpName(grp.getGrpName());
        return grpdto;
    }

    public static ContactDTO toContactDto(ContactEntity cont) {
        ContactDTO cdto = new ContactDTO();
        cdto.setcId(cont.getcId());
        cdto.setcName(cont.getcName());
        
        List<AddressDTO> adtoList = new ArrayList<>();
        for (AddressEntity address : cont.getAddr()) {
            AddressDTO adto = TransferValues.toAddressDto(address);
            adtoList.add(adto);
        }
        
        List<PhoneDTO> pdtoList = new ArrayList<>();
        for (PhoneEntity phone : cont.getPh()) {
            PhoneDTO pdto = TransferValues.toPhoneDto(phone);
            pdtoList.add(pdto);
        }
        
        List<ContactGrpDTO> grpdtoList = new ArrayList<>();
        for (ContactGrpEntity grp : cont.getGrp()) {
            ContactGrpDTO grpdto = TransferValues.toContactGrpDto(grp);
            grpdtoList.add(grpdto);
        }
    
        cdto.setAddr(adtoList);
        cdto.setPh(pdtoList);
        cdto.setGrp(grpdtoList);
        return cdto;
    }

    public static ContactEntity fromContactDto(ContactDTO cdto) {
        ContactEntity cont = new ContactEntity();
        cont.setcId(cdto.getcId());
        cont.setcName(cdto.getcName());
        
        List<AddressEntity> addrList = new ArrayList<>();
        for (AddressDTO adto : cdto.getAddr()) {
            AddressEntity addr = TransferValues.fromAddressDto(adto);
            addrList.add(addr);
        }
        
        List<PhoneEntity> phList = new ArrayList<>();
        for (PhoneDTO pdto : cdto.getPh()) {
            PhoneEntity ph = TransferValues.fromPhoneDto(pdto);
            phList.add(ph);
        }
        
        List<ContactGrpEntity> grpList = new ArrayList<>();
        for (ContactGrpDTO grpdto : cdto.getGrp()) {
            ContactGrpEntity grp = TransferValues.fromGroupDto(grpdto);
            grpList.add(grp);
        }

        cont.setAddr(addrList);
        cont.setPh(phList);
        cont.setGrp(grpList);
        return cont;
    }
}
