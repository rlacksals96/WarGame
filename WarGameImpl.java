import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class WarGameImpl extends UnicastRemoteObject implements WarGame {
	
	private static final long serializedversion=1L;
	private String clientID[]= {"aaa","bbb"};
	private String clientPassword[]= {"1234","1234"};
//	private ArrayList<WarGame> clientList;
	private Game game;
	private boolean turn=true;
	private ArrayList<Card> deck0,deck1,deck2;
	private int clientCnt=0;
	//private HashMap<String,Integer> clientStatus;//어떤 클라이언트의 순서인지 확인할 수 있다(이름,0)/(이름,1) -> 전자는 비활성화, 후자는 활성화
	private ArrayList<String> client;
	private HashMap<String,Integer> readyStatus;
	private boolean boolean_readyStatus[];
	protected WarGameImpl() throws RemoteException{
		super();
		//clientList=new ArrayList<WarGame>();
		game=new Game();
		deck1=game.returnDeck1();
		deck2=game.returnDeck2();
		deck0=null;
		readyStatus=new HashMap<String,Integer>();
		client=new ArrayList<String>();
		//clientStatus=new HashMap<String,Integer>();
//		boolean_readyStatus=new boolean[2];
//		boolean_readyStatus[0]=false;
//		boolean_readyStatus[1]=false;
		//System.out.println(deck1);//test
	}
	
	public synchronized boolean checkClientCredentials(WarGame wg,String id,String password) throws RemoteException{
		boolean checkLog=false;
		for(int i=0;i<clientID.length;i++) {
			if(clientID[i].equals(id) && clientPassword[i].equals(password)) {
				checkLog=true;
				
			}
		}
		if(checkLog==false)
			System.out.println("no id or wrong password");
		return checkLog;
		
	}
//	 public synchronized void broadcastMsg(String name, String message) throws RemoteException{
//		 for(int i=0; i<clientList.size(); i++) {
//	            clientList.get(i).sendMsgToClient(name.toUpperCase() + " : "+ message);
//	        }
//	 }
//	 public synchronized void  sendMsgToClient(String message) throws RemoteException{
//		 //this will be coded in client side
//	 }
	@Override
	 public int checkGameStatus() throws RemoteException {
		// TODO Auto-generated method st
		String value=deck0.get(0).returnValue();
		if(value=="K")//공격카드의 값만큼 리턴 
			return 3;
		else if(value=="Q")
			return 2;
		else if(value=="J")
			return 1;
		else if(value=="A")
			return 3;
		else if(value=="2")
			return 2;
		else
			return -1;//평시 상태
	}
	 public boolean checkEndingStatus() throws RemoteException{
		 if(deck1.isEmpty()==true || deck2.isEmpty()==true)
			 return false;//game over
		 else 
			 return true;
	 }
	 public String whosTurn() throws RemoteException{ 
		if(turn)
			return client.get(0);
		else
			return client.get(1);
	 }
	 public void changeTurn() throws RemoteException{
		 turn=!turn;
	 }
	 public synchronized void setReadyStatus(String id,boolean status) throws RemoteException{
		 int tmp=0;
		 if(status==true)
			 tmp=1;
		 else
			 tmp=0;
		 if(!readyStatus.containsKey(id))//결국엔 같은건데 구분할까 말까? 
			 readyStatus.put(id, tmp);
		 else {
			 readyStatus.put(id,tmp);
			
		 }
		 
	 }
	 public synchronized boolean checkAllReady() throws RemoteException{
		 int cnt=0;
		 Iterator<Entry<String, Integer>> itr=readyStatus.entrySet().iterator();
		 Set<Entry<String,Integer>>set=readyStatus.entrySet();
		 while(itr.hasNext()) {
			 Map.Entry<String,Integer>e=(Map.Entry<String,Integer>)itr.next();
			 if(e.getValue()==1) {
				 cnt++;
			 }
			 
		 }
		 if(cnt==2) {
			 client.addAll(readyStatus.keySet());
//			 clientStatus.put(client.get(0),1);
//			 clientStatus.put(client.get(1),0);
			 return true;
		 }
			
		 else
			 return false;
	 }
	 public synchronized void doDrop(String id) throws RemoteException{
		 Card c=null;
		 if(turn==true) {//player 1's drop
			c=deck1.get(0);
			if(c==null) {
				System.out.println("Player2 win!!");
			}
			deck0.add(c);
				
		 }else {//player 2's drop
			 c=deck2.get(0);
			 if(c==null) {
				 System.out.println("Player1 win!!");
			 }
			 deck0.add(c);
		 }
	 }
	 public synchronized void doHit(String id) throws RemoteException{
		 Card []c=new Card[2];
		 c[0]=deck0.get(0);
		 c[1]=deck0.get(1);
		 if(c[0].returnType()==c[1].returnType()) {
			 System.out.println("Hit Success!!");
		 }
		 else
			 System.out.println("Hit Fail...");
		 
			 
	 }
	 
	 public void pop() {
		 if(turn==true) {
			
		 }else {
			 
		 }
	 }
	 public void push() {
		 if(turn==true) {
			 
		 }else {
			 
		 }
	 }

	
	 
}
