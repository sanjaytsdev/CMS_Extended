package Domain.DAO;

import Domain.Entities.PhoneEntity;
import Infrastructure.SQLAccess;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PhoneDAO extends BaseDao {
    public static String insertQuery(PhoneEntity phone) {
        return "INSERT INTO phones (cid, ph) VALUES (" + phone.getCid() + ", '" + phone.getPh() + "');";
    }
    
    public static String updateQuery(PhoneEntity phone, String oldPh) {
        return "UPDATE phones SET ph = '" + phone.getPh() + "' WHERE cid = " + phone.getCid() + " AND ph = '" + oldPh + "';";
    }
    
    public static String selectQuery(int cid) {
        return "SELECT * FROM phones WHERE cid = " + cid + ";";
    }

    public String selectAllQuery() {
        return "SELECT * FROM phones;";
    }
    
    public static String deleteQuery(int cid, String oldPh){
        return "DELETE FROM phones WHERE cid = " + cid + " AND ph = '" + oldPh + "'";
    }

    public String deleteQueryByCid(int cid) {
        return "DELETE FROM phones WHERE cid = " + cid + ";";
    }

    public static boolean createOrUpdatePhone(PhoneEntity phone) throws Exception {
        SQLAccess s = new SQLAccess(DRIVER_NAME);

        if (!s.Open(URL))
            return false;

        String queryExc = insertQuery(phone);
        boolean result = s.executeNonQuery(queryExc);
        s.Close();
        return result;
    }

    public static boolean updatePhone(PhoneEntity phone, String oldph) throws Exception {
        SQLAccess s = new SQLAccess(DRIVER_NAME);

        if (!s.Open(URL))
            return false;

        String queryExc = updateQuery(phone, oldph);
        boolean result = s.executeNonQuery(queryExc);
        s.Close();
        return result;
    }

    public static boolean deletePhone(int cid, String oldph) throws Exception {
        SQLAccess s = new SQLAccess(DRIVER_NAME);

        if (!s.Open(URL))
            return false;

        String queryExc = deleteQuery(cid, oldph);
        boolean result = s.executeNonQuery(queryExc);
        s.Close();
        return result;
    }

    public boolean deletePhonesByContactId(int cid) throws Exception {
        SQLAccess s = new SQLAccess(DRIVER_NAME);

        if (!s.Open(URL))
            return false;

        String queryExc = deleteQueryByCid(cid);
        boolean result = s.executeNonQuery(queryExc);
        s.Close();
        return result;
    }

    public static List<PhoneEntity> getPhonesForCid(int cid) throws Exception {
        SQLAccess s = new SQLAccess(DRIVER_NAME);
    
        if (!s.Open(URL))
            return null;
    
        String queryExc = selectQuery(cid);
        ResultSet rs = s.Execute(queryExc);
    
        if (rs == null)
            return null;
        List<PhoneEntity> phones = new ArrayList<>();
            while (rs.next()) {
                PhoneEntity phone = new PhoneEntity();
                phone.setCid(cid);
                phone.setPh(rs.getString("ph"));
                phones.add(phone);
            }
            return phones;
        }
    
    public static List<PhoneEntity> getPhonesByContactId(int cid) throws Exception {
        SQLAccess s = new SQLAccess(DRIVER_NAME);
    
        if (!s.Open(URL))
            return null;
    
        String queryExc = selectQuery(cid);
        ResultSet rs = s.Execute(queryExc);
    
        if (rs == null)
            return null;
    
        List<PhoneEntity> phones = new ArrayList<>();
    
        while (rs.next()) {
            int pid = rs.getInt("pid");
            String ph = rs.getString("ph");
    
            PhoneEntity temp = new PhoneEntity();
            temp.setPid(pid);
            temp.setCid(cid);
            temp.setPh(ph);
            phones.add(temp);
        }
        rs.close();
        s.Close();
        return phones;
    }

    public List<PhoneEntity> getPhones() throws Exception {
        SQLAccess s = new SQLAccess(DRIVER_NAME);

        if (!s.Open(URL))
            return null;

        String queryExc = selectAllQuery();
        ResultSet rs = s.Execute(queryExc);

        if (rs == null)
            return null;

        List<PhoneEntity> retval = new ArrayList<>();
        while (rs.next()) {
            int pid = rs.getInt("pid");
            int cid = rs.getInt("cid");
            String ph = rs.getString("ph");
            PhoneEntity temp = new PhoneEntity();
            temp.setPid(pid);
            temp.setCid(cid);
            temp.setPh(ph);
            retval.add(temp);
        }
        rs.close();
        s.Close();
        return retval;
    }

    public Object[][] getPhAsArray() throws Exception {
        List<PhoneEntity> rs = getPhones();
        Object[][] data = new Object[rs.size()][3];
        int i = 0;

        for (PhoneEntity cg : rs) {
            Object[] temp = new Object[] {cg.getPid(), cg.getCid(), cg.getPh()};
            data[i] = temp;
            i++;
        }
        return data;
    }

    public boolean alreadyExists(int id) throws Exception {
        return getPhonesByContactId(id) != null;
    }

}






