package Presenter;
import java.util.List;

import Domain.DTO.ContactDTO;
import Domain.Entities.ContactEntity;
import Domain.Transfer.TransferValues;
import Services.ContactService;

public class ContactManager {
    public ContactManager() {

    }

    public static boolean DoesExists(int id) throws Exception {
        return ContactService.isContactExists(id);
    }

    public static List<ContactDTO> Get() {
        try {
            return ContactService.retrieveContacts();
        } catch (Exception e) {
            return null;
        }
    }

    public static ContactDTO getData(String str) {
        return ContactService.getData(str);
    }

    public static String[] getContactNameAsArray() throws Exception {
        return ContactService.getContactNameAsArray();
    }

    public static boolean addContact(ContactDTO cont) {
        ContactEntity cont1 = TransferValues.fromContactDto(cont);
        return ContactService.addContact(cont1);
    }

    public static boolean Delete(int id) {
        return ContactService.Delete(id);
    }

    public static boolean updateContact(ContactDTO cont) {
        ContactEntity cont1 = TransferValues.fromContactDto(cont);
        return ContactService.addContact(cont1);
    }

}
