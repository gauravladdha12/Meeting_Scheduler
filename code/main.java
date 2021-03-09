import java.util.*;
import java.text.*;
import java.time.format.DateTimeFormatter;
import java.time.*; 
import java.time.temporal.ChronoUnit;

class meeting{
	int meeting_id;
    String date;
	int start_time;
	int end_time;
    int room_no;
    meeting(int meeting_id,String date,int start_time,int end_time,int room_no){
        this.meeting_id=meeting_id;
        this.date=date;
        this.start_time=start_time;
        this.end_time=end_time;
        this.room_no=room_no;
    }
}
class slot{
String date;
int start,end;
slot(String date,int start,int end){
    this.date=date;
    this.start=start;
    this.end=end;
}
}
class main{
    static int meeting_id = 0;
    static HashMap<Integer,ArrayList<slot>> room=new HashMap<>();
     static long betweenDates(Date firstDate, Date secondDate)
{
    return ChronoUnit.DAYS.between(firstDate.toInstant(), secondDate.toInstant());
}
	static int canhappen(int start_convert,int end_convert,String date){
	for(int k:room.keySet()){
        int cnt=0;
        if(room.get(k)==null){
            return k;
        }
     for(int j=0;j<room.get(k).size();j++)
     {
		   String g_date=room.get(k).get(j).date;
 		   if(g_date.compareTo(date)!=0)
            cnt++;
           else
           {
               if((room.get(k).get(j).start>=start_convert&&room.get(k).get(j).start>=end_convert)||(room.get(k).get(j).end<=start_convert&&room.get(k).get(j).end<=end_convert))
               cnt++;
           }
     }
     if(cnt==room.get(k).size())
     {
         
         return k;
     }
    }
    return -1;
}
static boolean cancel(HashMap<Integer,ArrayList<meeting>> hm,int cancel_id,int emp_id){
    if(cancel_id>meeting_id){
        System.out.println("meeting id is not valid");
        return false;
    }
    ArrayList<meeting> m=new ArrayList<>();
    m=hm.get(emp_id);
    for(int i=0;i<m.size();i++){
        if(m.get(i).meeting_id==cancel_id){
            int rn=m.get(i).room_no;
            ArrayList<slot> s=new ArrayList<>();
            s=room.get(rn);
            for(int j=0;j<s.size();j++){
                if(s.get(j).date.compareTo(m.get(i).date)==0&&s.get(j).start==m.get(i).start_time&&s.get(j).end==m.get(i).end_time){
                    s.remove(j);
                    break;
                }
            }
         m.remove(i);
         return true;
        }
    }
    System.out.println("you are not the organizer of this meeting");
    return false;
}
	public static void main(String[] args) {
        try{
		Scanner sc=new Scanner(System.in);

        System.out.println("Enter the data in the given format to initialsize the System");
        System.out.println("init_meeting_system <M (number of rooms)> <N (number of employees) >");
        System.out.println();
         String init[]=sc.nextLine().split(" ");
        int m=0,n=0;
        if(init.length==3&&init[0].compareTo("init_meeting_system")==0){
            m=Integer.parseInt(init[1]);
            n=Integer.parseInt(init[2]);
            System.out.println("****success****");
        }
        else{
            System.out.println("enter proper command to initialsize scheduler");
         
            return;
        }
        for(int i=0;i<m;i++){
            room.put(i+1,null);
        }
		HashMap<Integer,ArrayList<meeting>> hm=new HashMap<>(); //employee and his meetings
   		HashMap<Integer,Integer> employeemeetings=new HashMap<>();  //count of each employee’s meetings

        System.out.println("Enter the data in given format for the commonds");
            System.out.println("a) Booking:    book <employee_id> <Start Time> <End Time>");
            System.out.println("a) Cancelling: cancel <employee_id> <Meeting Id>");
            System.out.println();

        o: while (sc.hasNextLine()) {

            System.out.println();
            String sin[]=sc.nextLine().split(" ");
            if(sin.length==4&&sin[0].compareTo("book")==0){
            
		int emp_id=Integer.parseInt(sin[1]);
        if(emp_id>n){
            System.out.println("Employee Id is not valid");
            continue o;
        }
        //sc.nextLine();
		String start_time=sin[2];
        String end_time=sin[3];

		String start_date=start_time.substring(0,10);
        String end_date=end_time.substring(0,10);

		int start_hour=Integer.parseInt(start_time.substring(11,13));
		int end_hour=Integer.parseInt(end_time.substring(11,13));

		int start_minute=Integer.parseInt(start_time.substring(14,16));
		int end_minute=Integer.parseInt(end_time.substring(14,16));

		int start_second=Integer.parseInt(start_time.substring(17,19));
		int end_second=Integer.parseInt(end_time.substring(17,19));

        int start_convert=start_hour*3600+start_minute*60+start_second;
        int end_convert=end_hour*3600+end_minute*60+end_second;

        String start_cal[]=start_date.split("-");
        String end_cal[]=end_date.split("-");



        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");  
        LocalDateTime now = LocalDateTime.now();  

        String curr[]=dtf.format(now).split("-");  

       Date d_start=new SimpleDateFormat("yyyy-MM-dd").parse(start_date);
       Date d_end=new SimpleDateFormat("yyyy-MM-dd").parse(end_date);
       Date d_curr=new SimpleDateFormat("yyyy-MM-dd").parse(dtf.format(now));
       
        if(employeemeetings.get(emp_id)!=null&&employeemeetings.get(emp_id)>=2){
            System.out.println("can only be the organizer of 2 scheduled meetings");
            continue o;
        }
        if(d_start.compareTo(d_curr)<0){
            System.out.println("start date should be greater than current date");
            continue o;
        }
        if(d_end.compareTo(d_start)<0){
            System.out.println("end date should be greater than start date");
            continue o;
        }

       if(betweenDates(d_start,d_end)!=0){
            System.out.println("Cannot book a meeting of more than 3 hrs duration");
            continue o;
       }
        if(betweenDates(d_curr,d_start)>30){
            System.out.println("Cannot book beyond 1 month from today");
            continue o;
       }
		if((end_convert-start_convert)>3*60*60){
		System.out.println("Cannot book a meeting of more than 3 hrs duration");
        continue o;
        }
        if(end_convert<start_convert){
            System.out.println("end time should be greate or equal to start time");
            continue o;
        }
        int can=canhappen(start_convert,end_convert,start_date);
        if(can==-1){
            System.out.println("All rooms busy for the given time interval");
            continue o;
        }
        slot b=new slot(start_date,start_convert,end_convert);
        if(room.get(can)!=null){
            ArrayList<slot> out=room.get(can);
            out.add(b);
            room.put(can,out);
        }
        else{
            ArrayList<slot> s=new ArrayList<>();
            s.add(b);
            room.put(can,s);
        }
        meeting m_new=new meeting(++meeting_id,start_date,start_convert,end_convert,can);
        if(hm.get(emp_id)!=null){
            ArrayList<meeting> out=hm.get(emp_id);
            out.add(m_new);
            hm.put(emp_id,out);
        }
        else{
            ArrayList<meeting> mm=new ArrayList<>();
            mm.add(m_new);
            hm.put(emp_id,mm);
           
        }
        if(employeemeetings.get(emp_id)!=null){
            employeemeetings.put(emp_id,employeemeetings.get(emp_id)+1);
        }
        else{
            employeemeetings.put(emp_id,1);
        }

        System.out.println("****success****");
        System.out.println("room id : "+can);
        System.out.println("meeting id : "+meeting_id);
    }
    else if(sin.length==3&&sin[0].compareTo("cancel")==0){
        int cancel_id=Integer.parseInt(sin[2]);
        int cancel_emp_id=Integer.parseInt(sin[1]);
        boolean c=cancel(hm,cancel_id,cancel_emp_id);
        if(c){
            employeemeetings.put(cancel_emp_id,employeemeetings.get(cancel_emp_id)-1);
            if(employeemeetings.get(cancel_emp_id)==0){
                employeemeetings.remove(cancel_emp_id);
            }
            System.out.println("****success****");
        }
    }
    else{
        System.out.println("Enter proper commands");
    }
    }
    }catch(Exception e){
        e.printStackTrace();
    }

	}
}

