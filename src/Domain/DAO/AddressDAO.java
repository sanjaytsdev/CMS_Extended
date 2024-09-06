package Domain.DAO;

import Domain.Entities.AddressEntity;
import Infrastructure.SQLAccess;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AddressDAO extends BaseDao {

    public static String insertQuery(AddressEntity address) {
        return "INSERT INTO addresses (cid, address) VALUES ('" + address.getCid() + "', '" + address.getAddress() + "');";
    }

    public static String updateQuery(AddressEntity address, String oldAddr) {
        return "UPDATE addresses SET address = '" + address.getAddress() + "' WHERE cid = " + address.getCid() +  " AND address = '" + oldAddr + "';";
    }

    public static String selectQuery(int cid) {
        return "SELECT * FROM addresses WHERE cid = " + cid + ";";
    }

    public static String selectAllQuery() {
        return "SELECT * FROM addresses;";
    }
     
    public static String deleteQuery(int cid, String oldAddr){
        return "DELETE FROM addresses WHERE cid = " + cid + " AND address = '" + oldAddr + "'";
    }

    public String deleteBycIdQuery(int cid) {
        return "DELETE FROM addresses WHERE cid = " + cid + ";";
    }
    

    public static boolean createOrUpdateAddress(AddressEntity address) throws Exception {
        SQLAccess s = new SQLAccess(DRIVER_NAME);

        if (!s.Open(URL))
            return false;

        String queryExc = insertQuery(address);
        boolean result = s.executeNonQuery(queryExc);
        s.Close();
        return result;
    }

    public static boolean updateAddress(AddressEntity address, String oldAddr) throws Exception {
        SQLAccess s = new SQLAccess(DRIVER_NAME);

        if (!s.Open(URL))
            return false;

        String queryExc = updateQuery(address, oldAddr);
        boolean result = s.executeNonQuery(queryExc);
        s.Close();
        return result;
    }

    public boolean deleteAddressesByContactId(int cid) throws Exception {
        SQLAccess s = new SQLAccess(DRIVER_NAME);

        if (!s.Open(URL))
            return false;

        String queryExc = deleteBycIdQuery(cid);
        boolean result = s.executeNonQuery(queryExc);
        s.Close();
        return result;
    }

    public static boolean deleteAddress(int cid, String oldAddr) throws Exception {
        SQLAccess s = new SQLAccess(DRIVER_NAME);

        if (!s.Open(URL))
            return false;

        String queryExc = deleteQuery(cid, oldAddr);
        boolean result = s.executeNonQuery(queryExc);
        s.Close();
        return result;
    }

    public static List<AddressEntity> getAddrForCid(int cid) throws Exception {
        SQLAccess s = new SQLAccess(DRIVER_NAME);
    
        if (!s.Open(URL))
            return null;
    
        String queryExc = selectQuery(cid);
        ResultSet rs = s.Execute(queryExc);
    
        if (rs == null)
            return null;

        List<AddressEntity> address = new ArrayList<>();
            while (rs.next()) {
                AddressEntity addr = new AddressEntity();
                addr.setCid(cid);
                addr.setAddress(rs.getString("address"));
                address.add(addr);
            }
            return address;
        }

    public static List<AddressEntity> getAddressesByContactId(int cid) throws Exception {
        SQLAccess s = new SQLAccess(DRIVER_NAME);

        if (!s.Open(URL))
            return null;

        String queryExc = selectQuery(cid);
        ResultSet rs = s.Execute(queryExc);

        if (rs == null)
            return null;

        List<AddressEntity> addresses = new ArrayList<>();

        while (rs.next()) {
            int aid = rs.getInt("aid");
            String address = rs.getString("address");

            AddressEntity temp = new AddressEntity();
            temp.setAid(aid);
            temp.setCid(cid);
            temp.setAddress(address);
            addresses.add(temp);
        }
        rs.close();
        s.Close();
        return addresses;
    }

    public List<AddressEntity> getAddress() throws Exception {
        SQLAccess s = new SQLAccess(DRIVER_NAME);

        if (!s.Open(URL))
            return null;

        String queryExc = selectAllQuery();
        ResultSet rs = s.Execute(queryExc);

        if (rs == null)
            return null;

        List<AddressEntity> retval = new ArrayList<>();
        while (rs.next()) {
            int aid = rs.getInt("aid");
            int cid = rs.getInt("cid");
            String address = rs.getString("address");
            AddressEntity temp = new AddressEntity();
            temp.setAid(aid);
            temp.setCid(cid);
            temp.setAddress(address);
            retval.add(temp);
        }
        rs.close();
        s.Close();
        return retval;
    }

    public Object[][] getAddrAsArray() throws Exception {
        List<AddressEntity> rs = getAddress();
        Object[][] data = new Object[rs.size()][3];
        int i = 0;

        for (AddressEntity cg : rs) {
            Object[] temp = new Object[] {cg.getAid(), cg.getCid(), cg.getAddress()};
            data[i] = temp;
            i++;
        }
        return data;
    }

    public boolean alreadyExists(int id) throws Exception {
        return getAddressesByContactId(id) != null;
    }

}
