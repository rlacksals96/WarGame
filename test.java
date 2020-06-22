import java.util.ArrayList;
import java.util.HashMap;
public class test{
  public static void main(String[] args){
    HashMap<String,Integer> clientStatus=new HashMap<String,Integer>();
    ArrayList<String> client=new ArrayList<String>();

    clientStatus.put("aaa",1);
    clientStatus.put("bbb",1);

    client.addAll(clientStatus.keySet());
    System.out.println(client);
  }
}
