import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MarkData {
    private  Connection connection;
    private PreparedStatement ps;
    private String sql;
    public MarkData(Connection connection)
    {
        this.connection=connection;
    }

    public void Mark(int id)
    {
        sql="update stuDetail set flag=1 where id ="+id;
        try {
            ps=connection.prepareStatement(sql);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void increasewt(int id)
    {
        sql="update stuDetail set wrongtimes = wrongtimes+1 where id ="+id;
        try {
            ps=connection.prepareStatement(sql);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
