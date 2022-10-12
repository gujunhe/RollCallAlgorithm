import java.util.ArrayList;

public class RollCallAlgorithm {
    public  static ArrayList<Integer> StartRollCall(int course,int coursetimes,int [][]studetail) {//生成点名队列
        ArrayList<Integer> stu = new ArrayList<>();
        int length=studetail.length;

        if (coursetimes <1) {//仅仅在每门课程的第一次时进行全部学生点名
            for (int i = 0; i < length; i++)
            {
                if(studetail[i][1]==0&&studetail[i][2]==0)
                stu.add(i);
            }

            return stu;
        } else {
            for(int i=0;i<length;i++)
            {
                if(studetail[i][3]==1&&studetail[i][4]<5)//当标记为经常迟到的学生且错点次数(点名了但却到了)不超过五次时添加进入点名队列
                {
                    stu.add(i);
                }
            }
            return stu;
        }
    }
}
