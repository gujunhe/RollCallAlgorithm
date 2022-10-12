import java.sql.*;

public class GetStuDetailfromdb {
    private Connection connection;
    private PreparedStatement ps;
    private String sql;
    Statement stmt = null;
    ResultSet rs = null;
    public GetStuDetailfromdb(Connection connection)
    {
        this.connection=connection;
    }


    public int[][] get()
    {
        int [][] studetail=new int[90][5];
        sql="select id,highgrades,classcommittee,flag,wrongtimes from studetail ";
        try {
            stmt=connection.createStatement();
            rs=stmt.executeQuery(sql);
            int i=0;
            while (rs.next()) {

                int stuId = rs.getInt("id");
                int highgrades=rs.getInt("highgrades");
                int classcommittee= rs.getInt("classcommittee");
                int flag=rs.getInt("flag");
                int wrongtimes=rs.getInt("wrongtimes");
                studetail[i][0]=stuId;
                studetail[i][1]=highgrades;
                studetail[i][2]=classcommittee;
                studetail[i][3]=flag;
                studetail[i][4]=wrongtimes;
                i++;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  studetail;
    }
}
