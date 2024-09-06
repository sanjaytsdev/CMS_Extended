package Presenter;
import java.util.List;

import Domain.DTO.ContactGrpDTO;
import Domain.Entities.ContactGrpEntity;
import Domain.Transfer.TransferValues;
import Services.ContactGrpService;

public class ContactGrpManager {

    public ContactGrpManager() {

    }

    public static boolean doesExists(int id) throws Exception {
        return ContactGrpService.doesExists(id);
    }

    public static List<ContactGrpDTO> Get() {
        try {
            return ContactGrpService.retrieveContactGroups();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static ContactGrpDTO getContactGrpById(int grpid) {
        return ContactGrpService.getContactGrpById(grpid);
    }

    public static ContactGrpDTO getContactGrp(String str) {
        return TransferValues.toContactGrpDto(ContactGrpService.getGroup(str));
    }

    public static String[] getGrpNames() throws Exception {
        return ContactGrpService.getGroupNames();
    }

    public static Object[][] getGrpAsArray() {
        return ContactGrpService.getGroupAsArray();
    }

    public static List<ContactGrpDTO> getAllGrps() {
        return ContactGrpService.retrieveContactGroups();
    }

    public static boolean Add(ContactGrpDTO grpdto, int cid) throws Exception {
        return ContactGrpService.addGroupMapping(cid, grpdto.getGrpId());
    }

    public static boolean AddGrp(ContactGrpDTO cont) throws Exception {
        ContactGrpEntity cont1 = TransferValues.fromGroupDto(cont);
        return ContactGrpService.createGroup(cont1);
    }

    public static boolean Delete(int id) {
        return ContactGrpService.Delete(id);
    }

    public static boolean UpdateGrp(ContactGrpDTO cgdto) throws Exception {
        ContactGrpEntity cont1 = TransferValues.fromGroupDto(cgdto);
    
        if (cgdto.getGrpName() == null) {
            String existingName = ContactGrpService.getGroupNameById(cgdto.getGrpId());
            cont1.setGrpName(existingName);
        }
    
        if (cgdto.getGrpDesc() == null) {
            String existingDesc = ContactGrpService.getGroupDescById(cgdto.getGrpId());
            cont1.setGrpDesc(existingDesc);
        }

        return ContactGrpService.updateGroup(cont1);
    }

    public static List<ContactGrpDTO> getGroupsByContactId(int cid) throws Exception {
        return ContactGrpService.getGroupsByContactId(cid);
    }

    public static boolean addGroupMapping(int cid, int grpid) throws Exception {
        return ContactGrpService.addGroupMapping(cid, grpid);
    }

    public static boolean deleteGroupMapping(int cid, int grpid) throws Exception {
        return ContactGrpService.deleteGroupMapping(cid, grpid);
    }

    public static boolean doesMappingExists(int cid, int grpid) throws Exception {
        return ContactGrpService.mappingExists(cid, grpid);
    }

}
