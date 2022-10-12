import java.lang.ref.PhantomReference;
import java.sql.*;
import java.util.Random;

public class CreateData {
    private Connection connection;
    private int highgradesp,classcommitteep;
    private String[] courses=new String[]{"课程A","课程B","课程C","课程D","课程E"};
    private PreparedStatement ps;
    private String sql;
    private int[][] studetail=new int[90][2];


    public CreateData(int highgradesp,int classcommitteep,Connection connection) {
        this.highgradesp=highgradesp;
        this.classcommitteep=classcommitteep;
        this.connection=connection;
    }



    public void create() {

        Random random=new Random();
        int []student=new int[90];
        for(int i=0;i<90;i++)
        {
            studetail[i][0]=random.nextInt(101)>(100-highgradesp)?1:0;
            studetail[i][1]=random.nextInt(101)>(100-classcommitteep)?1:0;
        }
        try {
            DatabaseMetaData dbMetaData=connection.getMetaData();
            ResultSet tabs = null;
            String[]   types   =   { "TABLE" };
            tabs = dbMetaData.getTables(null, null, "stuDetail", types);
            if (tabs.next()) {
                sql = "drop table " + "stuDetail";
                ps = connection.prepareStatement(sql);
                ps.executeUpdate();
               // System.out.println("该表已经存在");
            }
            sql="create table stuDetail (id int primary key ,highgrades  tinyint(1), classcommittee tinyint(1),flag tinyint(1), wrongtimes int(2))";
            ps=connection.prepareStatement(sql);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        for(int i=0;i<90;i++)
        {
            sql = "insert into " + "stuDetail"+ "(id,highgrades,classcommittee,flag,wrongtimes)" + " value(?,?,?,?,?)";
            try {
                ps = connection.prepareStatement(sql);
                ps.setInt(1, i);
                ps.setInt(2, studetail[i][0]);
                ps.setInt(3, studetail[i][1]);
                ps.setInt(4, 0);
                ps.setInt(5, 0);
                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        //选取缺席的5-8同学
        int [][]samling=new int[5][8];
        for (int i = 0; i < 5; i++) {
            //samling[i]=sampling(student,random.nextInt(4)+5);5到8人
            samling[i]=sampling(student,8);//固定为8人
        }


        //创建课程数据表 记录没有到课的同学

        for(int i=0;i< courses.length;i++)
        {
            try {
                DatabaseMetaData dbMetaData=connection.getMetaData();
                ResultSet tabs = null;
                String[]   types   =   { "TABLE" };
                tabs = dbMetaData.getTables(null, null, courses[i], types);
                if (tabs.next()) {
                    sql = "drop table " + courses[i];
                    ps = connection.prepareStatement(sql);
                    ps.executeUpdate();
                   // System.out.println("该表已经存在");
                }
                sql="create table "+courses[i]+" (id int primary key ,stuId varchar(500))";
                ps=connection.prepareStatement(sql);
                ps.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }
            String stuId="";
            stuId+=samling[i][0];
            for(int j=1;j<samling[i].length;j++)
           {
               stuId+=","+samling[i][j];
           }
            String[] allStuId=new String[90];
            for(int j=0;j<90;j++) {
                allStuId[j]=stuId;
                int ranstu=random.nextInt(4);
                for (int k = 0; k < ranstu; k++) {
                    String id = String.valueOf(random.nextInt(90));
                    if (stuId.contains(id)) {
                        k--;
                    } else {
                        allStuId[j]+= "," + id;
                    }
                }
            }

            for(int j=0;j<90;j++) {
                sql = "insert into " + courses[i] + "(id,stuId)" + " value(?,?)";
                try {
                    ps = connection.prepareStatement(sql);
                    ps.setInt(1, j);
                    ps.setString(2, allStuId[j]);
                    ps.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    //抽样算法 从n个学生中抽取m个学生为缺席了该学期80%的课的人
    public  int[] sampling(int[] array, int m) {
        int[] result = new int[m];
        Random random=new Random();
        int n = array.length;
        for(int i=0; i<n; i++) {

            if(i < m) {
                result[i] =i;
            } else {
                int tmp = random.nextInt( i+1);
                if(tmp < m&&studetail[i][0]==0&&studetail[i][1]==0) {
                    result[tmp] = i;
                }
            }
        }
        return result;
    }
}
