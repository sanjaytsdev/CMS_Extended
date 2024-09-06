package Presenter;

import java.util.List;

import Domain.DTO.PhoneDTO;
import Services.PhoneService;


public class PhoneManager {

    public PhoneManager() {

    }

    public static boolean doesExists(int id) throws Exception {
        return PhoneService.isPhoneExists(id);
    }

    public static List<PhoneDTO> Get() {
        try {
            return PhoneService.retrieveNumbers();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Object[][] getPhAsArray() {
        return PhoneService.getPhAsArray();
    }

    public static boolean Delete(int id) {
        return PhoneService.Delete(id);
    }

    public static List<PhoneDTO> getPhones(int cid) throws Exception {
        return PhoneService.getPhonesByContactId(cid);
    }

    public static boolean addPhone(PhoneDTO pdto) throws Exception {
        return PhoneService.addPhone(pdto);
    }

    public static boolean updatePhone(PhoneDTO pdto, String oldPh) throws Exception {
        return PhoneService.updatePhone(pdto, oldPh);
    }

    public static boolean deletePhone(int cid, String ph) throws Exception {
        return PhoneService.deletePhone(cid, ph);
    }

}
