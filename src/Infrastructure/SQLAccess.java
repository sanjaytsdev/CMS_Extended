package Infrastructure;

import java.sql.*;

public class SQLAccess {
        private Connection con = null;
        private Statement stmt = null;
        private ResultSet rs = null;
        private String constr;

        public SQLAccess(String driverName) throws ClassNotFoundException {
            Class.forName(driverName);
        }

        public boolean Open(String constr) throws SQLException {
            this.constr = constr;
            if (con != null){
                con.close();
                con = null;
            }
            con = DriverManager.getConnection(constr);
            return true;
        }

        public ResultSet Execute(String SQL) throws SQLException{ 
            if (con == null)
                return null;

            if(stmt != null)
                stmt.close();

            stmt = con.createStatement();

            if (rs != null)
                rs.close();

            rs = stmt.executeQuery(SQL);
            return rs;
        }

        public boolean executeNonQuery(String SQL) {
            if (con == null)
                return false;

            try {
                if (stmt != null)
                    stmt.close();

                stmt = con.createStatement();
                stmt.executeUpdate(SQL);
                return true;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        public boolean Close() throws SQLException {
            if (stmt != null){
                stmt.close();
                stmt = null;
            }

            if (con != null){
                con.close();
                con = null;
            }
            return true;
        }
}

