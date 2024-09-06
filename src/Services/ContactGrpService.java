package Services;


import Domain.DAO.ContactDAO;
import Domain.DAO.ContactGrpDAO;
import Domain.DTO.ContactGrpDTO;
import Domain.Entities.ContactEntity;
import Domain.Entities.ContactGrpEntity;
import Domain.Transfer.TransferValues;

import java.util.ArrayList;
import java.util.List;

public class ContactGrpService {

    public static String[] getGroupNames() throws Exception {
        return (new ContactGrpDAO()).getGroupNameAsArray();
    }

    public static Object[][] getGroupAsArray() {
        try {
            return (new ContactGrpDAO()).getGroupAsArray();
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean createGroup(ContactGrpEntity cg) throws Exception {
        return (new ContactGrpDAO()).createOrUpdateGroup(cg);
    }

    public static List<ContactGrpDTO> retrieveContactGroups() {
        try {
            List<ContactGrpEntity> lst = (new ContactGrpDAO()).getGroups();
            List<ContactGrpDTO> ret = new ArrayList<>();
            for (ContactGrpEntity rs : lst)
                ret.add(TransferValues.toContactGrpDto(rs));
            return ret;
        } catch (Exception e) {
            return null;
        }
    }

    public static ContactGrpEntity getGroup(String str) {
        return (new ContactGrpDAO()).getData(str);
    }

    public static boolean doesExists(int id) throws Exception {
        return (new ContactGrpDAO()).alreadyExists(id);
    }

    public static boolean canWeDelete(int id) throws Exception {
        List<ContactEntity> cnt = (new ContactDAO()).listContact();
        for (ContactEntity c : cnt) {
            for (ContactGrpEntity grp : c.getGrp()) {
                if (grp.getGrpId() == id) {
                    ContactGrpDAO.deleteGroupMapping(c.getcId(),id);
                } else {
                    return true;
                }
            }
        }
        return true; 
    }
    
    public static ContactGrpDTO getContactGrpById(int grpid) {
        ContactGrpEntity grp = null;
        try {
            grp = (new ContactGrpDAO()).getDataById(grpid);
            if (grp == null) {
                return null;
            }
            return TransferValues.toContactGrpDto(grp);
        } catch (Exception e) {
            e.printStackTrace(); 
        }
        return null;
    }

    public static boolean Delete(int id) {
        try {
            if ((!canWeDelete(id))){
                return false;
            }                
            return (new ContactGrpDAO()).deleteContactGrp(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean addGroupMapping(int cid, int grpid) throws Exception {
        return (new ContactGrpDAO()).addGroupMapping(cid, grpid);
    }

    public static boolean updateGroup(ContactGrpEntity cont1) throws Exception {
        return (new ContactGrpDAO()).updateContactGrp(cont1);
    }

    public static String getGroupNameById(int grpid) throws Exception {
        return (new ContactGrpDAO()).getGroupNameById(grpid);
    }

    public static String getGroupDescById(int grpid) throws Exception {
        return (new ContactGrpDAO()).getGroupDescById(grpid);
    }

    public static List<ContactGrpDTO> getGroupsByContactId(int cid) throws Exception {
        List<ContactGrpEntity> ret = ContactGrpDAO.getGroupsByContactId(cid);
        List<ContactGrpDTO> retval = new ArrayList<>();
        for (ContactGrpEntity rs : ret)
            retval.add(TransferValues.toContactGrpDto(rs));
        return retval;
    }

    public static boolean deleteGroupMapping(int cid, int grpid) throws Exception {
        return ContactGrpDAO.deleteGroupMapping(cid, grpid);
    }

    public static boolean mappingExists(int cid, int grpid) throws Exception {
        return ContactGrpDAO.mappingExists(cid, grpid);
    }



}