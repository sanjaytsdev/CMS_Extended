package Presenter;

import java.util.List;

import Domain.DTO.AddressDTO;
import Services.AddressService;


public class AddressManager {

    public AddressManager() {


    }

    public static boolean doesExists(int id) throws Exception {
        return AddressService.isAddressExists(id);
    }

    public static List<AddressDTO> Get() {
        try {
            return AddressService.retrieveAddress();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Object[][] getAddrAsArray() {
        return AddressService.getAddrAsArray();
    }

    public static boolean Delete(int id) {
        return AddressService.Delete(id);
    }

    public static List<AddressDTO> getAddress(int cid) throws Exception {
        return AddressService.getAddrByContactId(cid);
    }

    public static boolean addAddress(AddressDTO addr) throws Exception {
        return AddressService.addAddress(addr);
    }

    public static boolean updateAddress(AddressDTO addr, String oldAddr) throws Exception {
        return AddressService.updateAddress(addr, oldAddr);
    }

    public static boolean deleteAddress(int cid, String addr) throws Exception {
        return AddressService.deleteAddr(cid, addr);
    }

}
