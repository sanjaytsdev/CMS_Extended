package Domain.DAO;

import Domain.Entities.ContactEntity;
import Domain.Entities.PhoneEntity;
import Domain.Entities.AddressEntity;
import Domain.Entities.ContactGrpEntity;
import Infrastructure.SQLAccess;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ContactDAO extends BaseDao {

    private final PhoneDAO phoneDAO;
    private final AddressDAO addressDAO;
    private final ContactGrpDAO contactGrpDAO;

    public ContactDAO() {
        this.phoneDAO = new PhoneDAO();
        this.addressDAO = new AddressDAO();
        this.contactGrpDAO = new ContactGrpDAO();
    }

    public String insertQuery(ContactEntity cont) {
        return "INSERT INTO contacts (cid, cname) VALUES ('" + cont.getcId() + "', '" + cont.getcName() + "');";
    }
    
    public String updateQuery(ContactEntity cont) {
        return "UPDATE contacts SET cname ='" + cont.getcName() + "' WHERE cid =" + cont.getcId() + ";";
    }
    
    public String deleteQuery(int id){
        return "DELETE FROM contacts WHERE cid =" + id + ";";
    }

    public String selectQuery(int id) {
        return "SELECT * FROM contacts WHERE cid =" + id + ";";
    }

    public String selectAllQuery() {
        return "SELECT * FROM contacts";
    }

    public String selectQueryName(String name) {
        return "SELECT * FROM contacts WHERE cname = '" + name + "';";
    }

    public String mapQuery(ContactEntity cont, ContactGrpEntity group) {
        return "INSERT INTO contact_group_mapping (cid, grpid) VALUES (" + cont.getcId() + ", " + group.getGrpId() + ");";
    }

    public String deleteMapQuery(int id) {
        return "DELETE FROM contact_group_mapping WHERE cid = " + id + ";";
    }

    public boolean createOrUpdateContact(ContactEntity cont) throws Exception {
        SQLAccess s = new SQLAccess(DRIVER_NAME);

        if (!s.Open(URL))
            return false;

        String queryExc = insertQuery(cont);
        boolean result = s.executeNonQuery(queryExc);

        for (PhoneEntity phone : cont.getPh()) {
            phone.setCid(cont.getcId());
            PhoneDAO.createOrUpdatePhone(phone);
        }
        for (AddressEntity address : cont.getAddr()) {
            address.setCid(cont.getcId());
            AddressDAO.createOrUpdateAddress(address);
        }
        for (ContactGrpEntity group : cont.getGrp()) {
            contactGrpDAO.createOrUpdateGroup(group);
            
            String mapSql = mapQuery(cont, group);
            s.executeNonQuery(mapSql);
        }

        s.Close();
        return result;
    }

    public boolean updateContact(ContactEntity cont) throws Exception {
        SQLAccess s = new SQLAccess(DRIVER_NAME);

        if (!s.Open(URL))
            return false;

        String queryExc = updateQuery(cont);
        System.out.println(queryExc);
        boolean result = s.executeNonQuery(queryExc);
        s.Close();
        return result;
    }


    public boolean deleteContact(int id) throws Exception {
        SQLAccess s = new SQLAccess(DRIVER_NAME);

        if (!s.Open(URL))
            return false;

        phoneDAO.deletePhonesByContactId(id);
        addressDAO.deleteAddressesByContactId(id);
        contactGrpDAO.deleteGroupsByContactId(id);

        String queryExc = deleteMapQuery(id);
        s.executeNonQuery(queryExc);

        String queryExecute = deleteQuery(id);
        boolean result = s.executeNonQuery(queryExecute);
        s.Close();
        return result;
    }

    public List<ContactEntity> listContact() throws Exception {
        SQLAccess s = new SQLAccess(DRIVER_NAME);
    
        if (!s.Open(URL))
            return null;
    
        String queryExc = selectAllQuery();
        ResultSet rs = s.Execute(queryExc);
    
        if (rs == null)
            return null;
    
        List<ContactEntity> ret = new ArrayList<>();
    
        while (rs.next()) {
            int cid = rs.getInt("cid");
            String cname = rs.getString("cname");
    
            ContactEntity temp = new ContactEntity();
            temp.setcId(cid);
            temp.setcName(cname);
    
            temp.setPh(PhoneDAO.getPhonesByContactId(cid));
            temp.setAddr(AddressDAO.getAddressesByContactId(cid));
            temp.setGrp(ContactGrpDAO.getGroupsByContactId(cid));
    
            ret.add(temp);
        }
        rs.close();
        s.Close();
        return ret;
    }

    public Object[][] getContactAsArray() throws Exception {
        List<ContactEntity> contactList = this.listContact();
        Object[][] data = new Object[contactList.size()][3];  
        int i = 0;
    
        for (ContactEntity contact : contactList) {
            String phoneNumbers = "";
            List<PhoneEntity> phones = contact.getPh();
            for (int j = 0; j < phones.size(); j++) {
                phoneNumbers += phones.get(j).getPh();
                if (j < phones.size() - 1) {
                    phoneNumbers += ", ";
                }
            }
        
            String addresses = "";
            List<AddressEntity> addrList = contact.getAddr();
            for (int j = 0; j < addrList.size(); j++) {
                addresses += addrList.get(j).getAddress();
                if (j < addrList.size() - 1) {
                    addresses += ", ";
                }
            }
        
            String groups = "";
            List<ContactGrpEntity> grpList = contact.getGrp();
            for (int j = 0; j < grpList.size(); j++) {
                groups += grpList.get(j).getGrpName();
                if (j < grpList.size() - 1) {
                    groups += ", ";
                }
            }
        
    
            data[i] = new Object[] {contact.getcId(), contact.getcName(),addresses, phoneNumbers, groups};
            i++;
        }
        return data;
    }
    
    public String[] getContactNameAsArray() throws Exception {
        try {
            List<ContactEntity> rs = listContact();
            String[] data = new String[rs.size()];
            int i = 0;

            for (ContactEntity cg : rs) {
                data[i] = cg.getcName();
                i++;
            }
            return data;
        } catch (Exception e) {
            e.toString();
            return new String[] {"NULL"};
        }
    }

    public String getContactNameFromId(int id) throws Exception {
        SQLAccess s = new SQLAccess(DRIVER_NAME);

        if (!s.Open(URL))
            return null;

        String queryExc = selectQuery(id);
        ResultSet rs = s.Execute(queryExc);

        if (rs == null)
           return null;

        String ns = null;

        if (rs.next())
           ns = rs.getString("cname");

        rs.close();
        s.Close();
        return ns;
    }

    public boolean alreadyExists(int id) throws Exception {
        return getContactNameFromId(id) != null;
    }

    public ContactEntity getData(String contname) {
        try {
            SQLAccess s = new SQLAccess(DRIVER_NAME);

            if (!s.Open(URL))
                return null;

            String queryExc = selectQueryName(contname);
            ResultSet rs = s.Execute(queryExc);

            if (rs == null)
                return null;

            ContactEntity temp = null;

            if (rs.next()) {
                int cid = rs.getInt("cid");
                String cname = rs.getString("cname");
                temp = new ContactEntity();
                temp.setcId(cid);
                temp.setcName(cname);
                temp.setPh(PhoneDAO.getPhonesByContactId(cid));
                temp.setAddr(AddressDAO.getAddressesByContactId(cid));
                temp.setGrp(ContactGrpDAO.getGroupsByContactId(cid));
            }
            rs.close();
            s.Close();
            return temp;
        } catch (Exception e) {
            return null;
        }
    }

}

