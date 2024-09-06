package Services;

import java.util.ArrayList;
import java.util.List;

import Domain.DAO.PhoneDAO;
import Domain.DTO.PhoneDTO;
import Domain.Entities.PhoneEntity;
import Domain.Transfer.TransferValues;

public class PhoneService {

    public static boolean isPhoneExists(int id) throws Exception {
        return (new PhoneDAO()).alreadyExists(id);
    }

    public static List<PhoneDTO> retrieveNumbers () throws Exception {
        List<PhoneEntity> ret = (new PhoneDAO()).getPhones();
        List<PhoneDTO> retval = new ArrayList<>();

        for (PhoneEntity c : ret) {
            retval.add(TransferValues.toPhoneDto(c));
        }
        return retval;
    }

    public static boolean Delete(int id) {
        try {
            return (new PhoneDAO()).deletePhonesByContactId(id);
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean addPhone(PhoneEntity cont) {
        try {
            return PhoneDAO.createOrUpdatePhone(cont);
        } catch (Exception e) {
            return false;
        }
    }

    public static Object[][] getPhAsArray() {
        try {
            return (new PhoneDAO()).getPhAsArray();
        } catch (Exception e) {
            return null;
        }
    }

    public static List<PhoneDTO> getPhonesByContactId(int cid) throws Exception {
        List<PhoneEntity> ret = PhoneDAO.getPhonesByContactId(cid);
        List<PhoneDTO> retval = new ArrayList<>();

        for (PhoneEntity c : ret) {
            retval.add(TransferValues.toPhoneDto(c));
        }
        return retval;
    }

    public static boolean addPhone(PhoneDTO pdto) throws Exception {
        PhoneEntity ret = TransferValues.fromPhoneDto(pdto);
        return PhoneDAO.createOrUpdatePhone(ret); 
    }

    public static boolean updatePhone(PhoneDTO pdto, String oldPh) throws Exception {
        PhoneEntity ret = TransferValues.fromPhoneDto(pdto);
        return PhoneDAO.updatePhone(ret, oldPh); 

    }
    
    public static boolean deletePhone(int cid, String ph) throws Exception {
        return PhoneDAO.deletePhone(cid, ph);
    }

    public List<PhoneDTO> getPhones(int cid) throws Exception {
        List<PhoneEntity> ret = PhoneDAO.getPhonesForCid(cid);
        List<PhoneDTO> retval = new ArrayList<>();

        for (PhoneEntity c : ret) {
            retval.add(TransferValues.toPhoneDto(c));
        }
        return retval;
    }

}
