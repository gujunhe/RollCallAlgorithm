import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class RollCallTest {

    private static Connection getConnection() {
        Connection connection=null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("数据库驱动加载成功");
        } catch (ClassNotFoundException var3) {
            var3.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/software?useSSL=false", "root", "www.7k7k.com");
            System.out.println("数据库连接成功");
        } catch (SQLException var2) {
            var2.printStackTrace();
        }
        return connection;
    }
    public static void main(String[] args) {
        int total = 0, valid = 0;
        Connection connection=getConnection();
        for(int l=0;l<5;l++) {//课程次数
            CreateData createData = new CreateData(50, 50, connection);
            createData.create();
            GetData getData = new GetData(connection);
            GetStuDetailfromdb getStuDetailfromdb = new GetStuDetailfromdb(connection);
            MarkData markData = new MarkData(connection);
            int i=0;
            System.out.println();
            System.out.print("课程"+l);
                for (int j = 0; j < 20; j++) {
                    String str[] = getData.get(i, j).split(",");
                    int[] stuId = new int[str.length];//生成数据集中没有到课的同学
                    for (int k = 0; k < str.length; k++) {
                        stuId[k] = Integer.parseInt(str[k]);
                    }
                    ArrayList<Integer> stu = RollCallAlgorithm.StartRollCall(i, j, getStuDetailfromdb.get());//算法生成的所需要点名的同学
                    total += stu.size();
                    System.out.println();
                    for (Integer integer : stu) {
                        System.out.print(integer + " ");
                    }
                    for (Integer integer : stu) {
                        int k = 0;
                        for (k = 0; k < stuId.length; k++) {
                            if (integer == stuId[k]) {
                                valid++;
                                markData.Mark(stuId[k]);
                                break;
                            }
                        }
                        if (k == stuId.length) {
                            markData.increasewt(integer);
                        }
                    }
                }
        }
        System.out.println();
        System.out.println("总次数"+total);
        System.out.println("有效次数"+valid);
        double e=valid*1.0/total;
        System.out.println(e);
    }
}
