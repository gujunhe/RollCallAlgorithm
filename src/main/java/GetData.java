import java.sql.*;

public class GetData {
    private int coursename;
    private int courseid;
    private Connection connection;
    private String[] courses=new String[]{"课程a","课程b","课程b","课程d","课程e"};
    private PreparedStatement ps;
    private String sql;
    Statement stmt = null;
    ResultSet rs = null;

    public GetData(Connection connection)
    {
        this.connection=connection;
    }
    public  String get(int coursename,int courseid)
    {

        sql="select * from "+courses[coursename]+" where id = "+courseid;
        try {
            stmt=connection.createStatement();
            rs=stmt.executeQuery(sql);
            while (rs.next()) {

                String stuId = rs.getString("stuId");

                return  stuId;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  "";

    }
}
