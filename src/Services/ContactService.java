package Services;

import java.util.ArrayList;
import java.util.List;

import Domain.DAO.ContactDAO;
import Domain.DTO.ContactDTO;
import Domain.Entities.ContactEntity;
import Domain.Transfer.TransferValues;

public class ContactService {
    
    public static boolean isContactExists(int id) throws Exception {
        return (new ContactDAO()).alreadyExists(id);
    }

    public static List<ContactDTO> retrieveContacts () throws Exception {
        List<ContactEntity> ret = (new ContactDAO()).listContact();
        List<ContactDTO> retval = new ArrayList<>();

        for (ContactEntity c : ret) {
            retval.add(TransferValues.toContactDto(c));
        }
        return retval;
    }

    public static boolean Delete(int id) {
        try {
            return (new ContactDAO()).deleteContact(id);
        } catch (Exception e) {
            return false;
        }
    }

    public static String[] getContactNameAsArray() throws Exception {
        return (new ContactDAO()).getContactNameAsArray();
    }

    public static boolean addContact(ContactEntity cont) {
        try {
            return (new ContactDAO()).createOrUpdateContact(cont);
        } catch (Exception e) {
            return false;
        }
    }

    public static ContactDTO getData(String str) {
        ContactEntity c = (new ContactDAO()).getData(str);
        return TransferValues.toContactDto(c);
    }

    public static boolean updateContact(ContactEntity cont) {
        try {
            return (new ContactDAO()).updateContact(cont);
        } catch (Exception e) {
            return false;
        }
    }
}

