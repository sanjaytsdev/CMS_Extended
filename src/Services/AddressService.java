package Services;

import java.util.ArrayList;
import java.util.List;

import Domain.DAO.AddressDAO;
import Domain.DTO.AddressDTO;
import Domain.Entities.AddressEntity;
import Domain.Transfer.TransferValues;

public class AddressService {

    public static boolean isAddressExists(int id) throws Exception {
        return (new AddressDAO()).alreadyExists(id);
    }

    public static List<AddressDTO> retrieveAddress() throws Exception {
        List<AddressEntity> ret = (new AddressDAO()).getAddress();
        List<AddressDTO> retval = new ArrayList<>();

        for (AddressEntity c : ret) {
            retval.add(TransferValues.toAddressDto(c));
        }
        return retval;
    }

    public static boolean Delete(int id) {
        try {
            return (new AddressDAO()).deleteAddressesByContactId(id);
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean addAddress(AddressEntity cont) {
        try {
            return AddressDAO.createOrUpdateAddress(cont);
        } catch (Exception e) {
            return false;
        }
    }

    public static Object[][] getAddrAsArray() {
        try {
            return (new AddressDAO()).getAddrAsArray();
        } catch (Exception e) {
            return null;
        }
    }

    public static List<AddressDTO> getAddrByContactId(int cid) throws Exception {
        List<AddressEntity> ret = AddressDAO.getAddressesByContactId(cid);
        List<AddressDTO> retval = new ArrayList<>();

        for (AddressEntity c : ret) {
            retval.add(TransferValues.toAddressDto(c));
        }
        return retval;
    }

   public static boolean addAddress(AddressDTO adto) throws Exception {
        AddressEntity ret = TransferValues.fromAddressDto(adto);
        return AddressDAO.createOrUpdateAddress(ret); 
    }

    public static boolean updateAddress(AddressDTO adto, String oldAddr) throws Exception {
        AddressEntity ret = TransferValues.fromAddressDto(adto);
        return AddressDAO.updateAddress(ret, oldAddr); 

    }
    
    public static boolean deleteAddr(int cid, String addr) throws Exception {
        return AddressDAO.deleteAddress(cid, addr);
    }
   
    public List<AddressDTO> getAddr(int cid) throws Exception {
        List<AddressEntity> ret = AddressDAO.getAddrForCid(cid);
        List<AddressDTO> retval = new ArrayList<>();

        for (AddressEntity c : ret) {
            retval.add(TransferValues.toAddressDto(c));
        }
        return retval;
    }

}