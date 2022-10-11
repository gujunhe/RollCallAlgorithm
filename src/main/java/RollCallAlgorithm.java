import java.util.ArrayList;

public class RollCallAlgorithm {
    public  static ArrayList<Integer> StartRollCall(int course,int coursetimes,int [][]studetail) {
        ArrayList<Integer> stu = new ArrayList<>();
        int length=studetail.length;

        if (coursetimes <1) {
            for (int i = 0; i < length; i++)
            {
                if(studetail[i][1]==0&&studetail[i][2]==0)
                stu.add(i);
            }

            return stu;
        } else {
            for(int i=0;i<length;i++)
            {
                if(studetail[i][3]==1&&studetail[i][4]<=4)
                {
                    stu.add(i);
                }
            }
            return stu;
        }
    }
}
