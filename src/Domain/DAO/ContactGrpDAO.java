package Domain.DAO;


import Domain.Entities.ContactGrpEntity;
import Infrastructure.SQLAccess;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ContactGrpDAO extends BaseDao {

    public String insertQuery(ContactGrpEntity group) {
        return "INSERT INTO contactgrps (grpname, grpdesc) VALUES ('" + group.getGrpName() + "', '" + group.getGrpDesc() + "');";
    }

    public String insertGrpMapQuery(int cid, int grpid) {
        return "INSERT INTO contact_group_mapping (cid, grpid) VALUES ('" + cid + "','" + grpid + "');";
    }
    
    public String updateQuery(ContactGrpEntity group) {
        return "UPDATE contactgrps SET grpname = '" + group.getGrpName() + "', grpdesc = '" + group.getGrpDesc() + "' WHERE grpid = " + group.getGrpId() + ";";
    }
    
    public String selectQuery(int grpid) {
        return "SELECT * FROM contactgrps WHERE grpid = " + grpid + ";";
    }

    public String selectAllQuery() {
        return "SELECT * FROM contactgrps";
    }

    public String deleteQuery(int grpid){
        return "DELETE FROM contactgrps WHERE grpid = " + grpid + ";";
    }

    public static String deleteMap(int cid, int grpid) {
        return "DELETE FROM contact_group_mapping WHERE cid = "+ cid + " AND grpid =" + grpid +";";
    }

    public String deleteMapQuery(int cid) {
        return "DELETE FROM contact_group_mapping WHERE cid = " + cid + ";";
    }

    public String deleteGrpMapQuery(int grpid) {
        return "DELETE FROM contact_group_mapping WHERE grpid = " + grpid + ";";
    }

    public String selectQueryName(String name) {
        return "SELECT * FROM contactgrps WHERE grpname = '" + name + "';";
    }

    public String selectGrpNameQuery(int grpid) {
        return "SELECT grpname FROM contactgrps WHERE grpid = " + grpid + ";";
    }

    public String selectContGrpsById(int grpid) {
        return "SELECT * FROM contactgrps WHERE grpid = " + grpid + ";"; 
    }

    public String selectGrpDescQuery(int grpid) {
        return  "SELECT grpdesc FROM contactgrps WHERE grpid = " + grpid + ";";
    }

    public static String mappingExistsQuery(int cid, int grpid) {
        return "SELECT COUNT(*) AS count FROM contact_group_mapping WHERE cid = " + cid + " AND grpid = " + grpid + ";";
    }

    public static String getGrpsByCidQuery(int cid) {
        return "SELECT g.grpid, g.grpname, g.grpDesc FROM contact_group_mapping m " + "JOIN contactgrps g ON m.grpid = g.grpid WHERE m.cid = " + cid + ";";
    }

    public boolean createOrUpdateGroup(ContactGrpEntity cont) throws Exception {
        SQLAccess s = new SQLAccess(DRIVER_NAME);

        if (!s.Open(URL))
            return false;

        String queryExc = insertQuery(cont);
        boolean result = s.executeNonQuery(queryExc);
        s.Close();
        return result;
    }

    public boolean updateContactGrp(ContactGrpEntity cont) throws Exception {
        SQLAccess s = new SQLAccess(DRIVER_NAME);
    
        if (!s.Open(URL))
            return false;
    
        if (cont.getGrpName() == null) {
            String existingName = getGroupNameById(cont.getGrpId());
            cont.setGrpName(existingName);
        }
    
        String queryExc = updateQuery(cont);
        System.out.println(queryExc);
        boolean result = s.executeNonQuery(queryExc);
        s.Close();
        return result;
    }
    
    public String getGroupNameById(int grpid) throws Exception {
        SQLAccess s = new SQLAccess(DRIVER_NAME);
        String grpName = null;
    
        if (s.Open(URL)) {

            String queryExc = selectGrpNameQuery(grpid);
            ResultSet rs = s.Execute(queryExc);
    
            if (rs.next()) {
                grpName = rs.getString("grpname");
            }
    
            s.Close();
        }
        return grpName;
    }

    public String getGroupDescById(int grpid) throws Exception {
        SQLAccess s = new SQLAccess(DRIVER_NAME);
        String grpName = null;
    
        if (s.Open(URL)) {

            String queryExc = selectGrpDescQuery(grpid);
            ResultSet rs = s.Execute(queryExc);
    
            if (rs.next()) {
                grpName = rs.getString("grpdesc");
            }
    
            s.Close();
        }
        return grpName;
    }
    

    public boolean deleteGroupsByContactId(int cid) throws Exception {
        SQLAccess s = new SQLAccess(DRIVER_NAME);

        if (!s.Open(URL))
            return false;

        String queryExc = deleteMapQuery(cid);
        boolean result = s.executeNonQuery(queryExc);
        s.Close();
        return result;
    }

    public static List<ContactGrpEntity> getGroupsByContactId(int cid) throws Exception {
        SQLAccess s = new SQLAccess(DRIVER_NAME);
    
        if (!s.Open(URL))
            return null;
    
        String queryExc = getGrpsByCidQuery(cid);
        ResultSet rs = s.Execute(queryExc);
    
        if (rs == null)
            return null;
    
        List<ContactGrpEntity> groups = new ArrayList<>();
    
        while (rs.next()) {
            int grpId = rs.getInt("grpid");
            String grpName = rs.getString("grpname");
            String grpDesc = rs.getString("grpdesc");
    
            ContactGrpEntity temp = new ContactGrpEntity();
            temp.setGrpId(grpId);
            temp.setGrpName(grpName);
            temp.setGrpDesc(grpDesc);
            groups.add(temp);
        }
        rs.close();
        s.Close();
        return groups;
    }

    public List<ContactGrpEntity> getGroups() throws Exception {
        SQLAccess s = new SQLAccess(DRIVER_NAME);

        if (!s.Open(URL))
            return null;

        String queryExc = selectAllQuery();
        ResultSet rs = s.Execute(queryExc);

        if (rs == null)
            return null;

        List<ContactGrpEntity> groups = new ArrayList<>();

        while (rs.next()) {
            int grpId = rs.getInt("grpid");
            String grpName = rs.getString("grpname");
            String grpDesc = rs.getString("grpdesc");

            ContactGrpEntity temp = new ContactGrpEntity();
            temp.setGrpId(grpId);
            temp.setGrpName(grpName);
            temp.setGrpDesc(grpDesc);
            groups.add(temp);
        }
        rs.close();
        s.Close();
        return groups;
    }

    public ContactGrpEntity getData(String grpname) {
        try {
            SQLAccess s = new SQLAccess(DRIVER_NAME);

            if (!s.Open(URL))
                return null;

            String queryExc = selectQueryName(grpname);
            ResultSet rs = s.Execute(queryExc);

            if (rs == null)
                return null;

            ContactGrpEntity ns = null;

            if (rs.next()) {
                ns = new ContactGrpEntity();
                ns.setGrpId(rs.getInt("grpid"));
                ns.setGrpName(rs.getString("grpname"));
                ns.setGrpDesc(rs.getString("grpdesc"));
            }
            rs.close();
            s.Close();
            return ns;
        } catch (Exception e) {
            return null;
        }
    }

    public ContactGrpEntity getDataById(int grpid) {
        try {
            SQLAccess s = new SQLAccess(DRIVER_NAME);
            if (!s.Open(URL)) return null;
    
            String queryExc = selectContGrpsById(grpid);
            ResultSet rs = s.Execute(queryExc);
    
            ContactGrpEntity ns = null;
            if (rs != null && rs.next()) {
                ns = new ContactGrpEntity();
                ns.setGrpId(rs.getInt("grpid"));
                ns.setGrpName(rs.getString("grpname"));
                ns.setGrpDesc(rs.getString("grpdesc"));
            }
            rs.close();
            s.Close();
            return ns;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean deleteContactGrp(int id) throws Exception {
        SQLAccess s = new SQLAccess(DRIVER_NAME);
    
        if (!s.Open(URL))
            return false;
    
        String queryExc = deleteGrpMapQuery(id);
        s.executeNonQuery(queryExc);
        
    
        String queryExecute = deleteQuery(id);
        boolean result = s.executeNonQuery(queryExecute);
        s.Close();
        return result;
    }
    

    public String getNameFromId(int id) throws Exception {
        SQLAccess s = new SQLAccess(DRIVER_NAME);

        if (!s.Open(URL))
            return null;

        String queryExc = selectQuery(id);
        ResultSet rs = s.Execute(queryExc);

        if (rs == null)
            return null;

        String ns = null;

        if (rs.next())
            ns = rs.getString("grpid");

        rs.close();
        s.Close();
        return ns;
    }

    public Object[][] getGroupAsArray() throws Exception {
        List<ContactGrpEntity> groups = getGroups(); 
        Object[][] data = new Object[groups.size()][3];
        int i = 0;
    
        for (ContactGrpEntity group : groups) {
            data[i] = new Object[]{group.getGrpId(), group.getGrpName(), group.getGrpDesc()};
            i++;
        }
        return data;
    }
    
    public String[] getGroupNameAsArray() throws Exception {
        List<ContactGrpEntity> groups = getGroups(); 
        String[] names = new String[groups.size()];
        int i = 0;
    
        for (ContactGrpEntity group : groups) {
            names[i] = group.getGrpName();
            i++;
        }
        return names;
    }    

    public boolean alreadyExists(int id) throws Exception {
        return getNameFromId(id) != null;
    }

    public static boolean mappingExists(int cid, int grpid) throws Exception {
        SQLAccess s = new SQLAccess(DRIVER_NAME);
        if (!s.Open(URL)) {
            return false;
        }

        String queryExc = mappingExistsQuery(cid, grpid);
        ResultSet rs = s.Execute(queryExc);
        boolean exists = false;
    
        if (rs != null && rs.next() && rs.getInt("count") > 0) {
            exists = true;
        }
        
        rs.close();
        s.Close();
        return exists;
    }

    public boolean addGroupMapping(int cid, int grpid) throws Exception {
        SQLAccess s = new SQLAccess(DRIVER_NAME);

        if (!s.Open(URL)) {
            return false;
        }

        if (mappingExists(cid, grpid)) {
            return false;
        }

        String queryExc = insertGrpMapQuery(cid, grpid);
        boolean result = s.executeNonQuery(queryExc);
        s.Close();
        return result;
        
    }

    public static boolean deleteGroupMapping(int cid, int grpid) throws Exception {
        SQLAccess s = new SQLAccess(DRIVER_NAME);

        if (!s.Open(URL)) {
            return false;
        }

        String queryExc = deleteMap(cid, grpid);
        boolean result = s.executeNonQuery(queryExc);
        s.Close();
        return result;
        
    }

}
