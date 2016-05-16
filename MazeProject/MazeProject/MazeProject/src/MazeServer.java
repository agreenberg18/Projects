import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class MazeServer {

	public static final int SERVER_PORT = 2189;
	
	public static void main(String[] args) throws IOException {
		System.out.println("The Maze server is running.");
		ServerSocket listener = new ServerSocket(SERVER_PORT);
		try {
			while (true) {
				System.out.println("Waiting for Player1 to connect ...");
				PlayerThread player1 = new PlayerThread(listener.accept(), "Player1");
				System.out.println("Waiting for Player2 to connect ...");
				PlayerThread player2 = new PlayerThread(listener.accept(), "Player2");
				player1.setRivalPlayer(player2);
				player2.setRivalPlayer(player1);
				System.out.println("Both players are connected. Start the Game Thread");
				player1.start();
				player2.start();
			}
		} finally {
			listener.close();
		}
	}

	private static class PlayerThread extends Thread {
		private Socket socket;
		private String playerName;
		long time = 60000;
		int pos_p;
		int pos_q;
		int count;
		PlayerThread rivalPlayer;
		BufferedReader in;
		PrintWriter out;
		
		public PlayerThread(Socket socket) {
			this.socket = socket;
			initializeIOStreams();
			
		}

		public PlayerThread(Socket socket, String playerName) {
			this.socket = socket;
			this.playerName = playerName;
			initializeIOStreams();
		}

		private void initializeIOStreams() {
			try {
				this.in = new BufferedReader( new InputStreamReader(socket.getInputStream()));
				this.out = new PrintWriter(socket.getOutputStream(), true);
				out.println("Welcome " + playerName);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		public void run(){
			try{
				
				out.println("INITIALIZE MAZE");
				long startTime = System.currentTimeMillis();
                //Start reading messages from the Players(MazeClient)
                while (true) {
                	String input = in.readLine();
                	System.out.println("input read => " + input);
	                if (input == null) {
	                	break;
	                } else if(input.startsWith("Down")){
	            		String[] splited = input.split("\\s");                             
	            		int posp=Integer.parseInt(splited[2]);
	            		int posq=Integer.parseInt(splited[3]);
	            		int countva=Integer.parseInt(splited[4]);
	                    if(splited[1].equals(getPlayerName())){
	                    	setPos_p(posp);
	                    	setPos_q(posq);
	                    	setCount(countva);
	                    } else {
	                    	this.rivalPlayer.setPos_p(posp);
	                    	this.rivalPlayer.setPos_q(posq);
	                    	this.rivalPlayer.setCount(countva);
	                    }
	                    
	            		long endTime = System.currentTimeMillis();
	            		if ((endTime - startTime) > time) {
							if(getCount() > this.rivalPlayer.getCount()){
	               				System.out.println("Game Over !! The Winner is "+ getPlayerName());
								out.println("END WINNER!!! Your COUNT is " + getCount() + ". Rival Player COUNT is " + this.rivalPlayer.getCount());
	               				rivalPlayer.out.println("END LOSER!!! Your COUNT is " + this.rivalPlayer.getCount() + ". Rival Player COUNT is " + getCount());
	               				break;
	                		} else if(getCount() == this.rivalPlayer.getCount()){
	                			System.out.println("Game Over !! There is a Tie");
	                			out.println("END TIE!!!  Your COUNT is " + getCount() + ". Rival Player COUNT is " + this.rivalPlayer.getCount());
	               				rivalPlayer.out.println("END TIE!!! Your COUNT is " + this.rivalPlayer.getCount() + ". Rival Player COUNT is " + getCount());
		         			   	break;
	                		} else {
	                			System.out.println("Game Over !! The Winner is "+ this.rivalPlayer.getPlayerName());
	                			out.println("END LOSER!!!  Your COUNT is " + getCount() + ". Rival Player COUNT is " + this.rivalPlayer.getCount());
	               				rivalPlayer.out.println("END WINNER!!! Your COUNT is " + this.rivalPlayer.getCount() + ". Rival Player COUNT is " + getCount());
		        	            break;
	                		}
						} else {
	                		System.out.println(getPlayerName()+" count is "+getCount()+"."+this.rivalPlayer.getPlayerName()+" count is "+this.rivalPlayer.getCount());
							out.println(input);
							rivalPlayer.out.println(input);
	            		}
           	       	} else if(input.startsWith("Right")){
           	       		String[] splited = input.split("\\s+");                             
	           	       	int posp=Integer.parseInt(splited[2]);
	            		int posq=Integer.parseInt(splited[3]);
	            		int countva=Integer.parseInt(splited[4]);
	                    if(splited[1].equals(getPlayerName())){
	                    	setPos_p(posp);
	                    	setPos_q(posq);
	                    	setCount(countva);
	                    } else {
	                    	this.rivalPlayer.setPos_p(posp);
	                    	this.rivalPlayer.setPos_q(posq);
	                    	this.rivalPlayer.setCount(countva);
	                    }
		                
		                long endTime = System.currentTimeMillis();
		                if ((endTime - startTime) > time) {
	            			if(getCount() > this.rivalPlayer.getCount()){
		             			System.out.println("Game Over !! The Winner is "+ getPlayerName());
	               				out.println("END WINNER!!! Your COUNT is " + getCount() + ". Rival Player COUNT is " + this.rivalPlayer.getCount());
	               				rivalPlayer.out.println("END LOSER!!! Your COUNT is " + this.rivalPlayer.getCount() + ". Rival Player COUNT is " + getCount());
	               				break;
		             		} else if(getCount() == this.rivalPlayer.getCount()){
		           				System.out.println("Game Over !! There is a Tie");
		           				out.println("END TIE!!!  Your COUNT is " + getCount() + ". Rival Player COUNT is " + this.rivalPlayer.getCount());
	               				rivalPlayer.out.println("END TIE!!! Your COUNT is " + this.rivalPlayer.getCount() + ". Rival Player COUNT is " + getCount());
		           			   	break;
		           		   	} else{
		                 	 	System.out.println("Game Over !! The Winner is "+ this.rivalPlayer.getPlayerName());
	                			out.println("END LOSER!!!  Your COUNT is " + getCount() + ". Rival Player COUNT is " + this.rivalPlayer.getCount());
	               				rivalPlayer.out.println("END WINNER!!! Your COUNT is " + this.rivalPlayer.getCount() + ". Rival Player COUNT is " + getCount());
		             		    break;
		           		   	}   
						} else {
		            		System.out.println(getPlayerName()+" count is "+getCount()+"."+this.rivalPlayer.getPlayerName()+" count is "+this.rivalPlayer.getCount());
							out.println(input);
							rivalPlayer.out.println(input);
		           	  	}   
           	       	} else if(input.startsWith("Left")){
           	       		String[] splited = input.split("\\s+");                             
	           	       	int posp=Integer.parseInt(splited[2]);
	            		int posq=Integer.parseInt(splited[3]);
	            		int countva=Integer.parseInt(splited[4]);
	                    if(splited[1].equals(getPlayerName())){
	                    	setPos_p(posp);
	                    	setPos_q(posq);
	                    	setCount(countva);
	                    } else {
	                    	this.rivalPlayer.setPos_p(posp);
	                    	this.rivalPlayer.setPos_q(posq);
	                    	this.rivalPlayer.setCount(countva);
	                    }
		                
		                long endTime = System.currentTimeMillis();
		                if ((endTime - startTime) > time) {
	                		if(getCount() > this.rivalPlayer.getCount()){
		             			System.out.println("Game Over !! The Winner is "+ getPlayerName());
	               				out.println("END WINNER!!! Your COUNT is " + getCount() + ". Rival Player COUNT is " + this.rivalPlayer.getCount());
	               				rivalPlayer.out.println("END LOSER!!! Your COUNT is " + this.rivalPlayer.getCount() + ". Rival Player COUNT is " + getCount());
		                  	    break;
		                	} else if(getCount() == this.rivalPlayer.getCount()){
		                		System.out.println("Game Over !! There is a Tie");
		           				out.println("END TIE!!!  Your COUNT is " + getCount() + ". Rival Player COUNT is " + this.rivalPlayer.getCount());
	               				rivalPlayer.out.println("END TIE!!! Your COUNT is " + this.rivalPlayer.getCount() + ". Rival Player COUNT is " + getCount());
		            			break;
		                	} else {
		                		System.out.println("Game Over !! The Winner is "+ this.rivalPlayer.getPlayerName());
	                			out.println("END LOSER!!!  Your COUNT is " + getCount() + ". Rival Player COUNT is " + this.rivalPlayer.getCount());
	               				rivalPlayer.out.println("END WINNER!!! Your COUNT is " + this.rivalPlayer.getCount() + ". Rival Player COUNT is " + getCount());
		             		    break;
		                	}
	            		} else {
		                	System.out.println(getPlayerName()+" count is "+getCount()+"."+this.rivalPlayer.getPlayerName()+" count is "+this.rivalPlayer.getCount());
							out.println(input);
							rivalPlayer.out.println(input);
		                }
           	       	} else if(input.startsWith("Up")){
		            	String[] splited = input.split("\\s+");                             
		            	int posp=Integer.parseInt(splited[2]);
	            		int posq=Integer.parseInt(splited[3]);
	            		int countva=Integer.parseInt(splited[4]);
	                    if(splited[1].equals(getPlayerName())){
	                    	setPos_p(posp);
	                    	setPos_q(posq);
	                    	setCount(countva);
	                    } else {
	                    	this.rivalPlayer.setPos_p(posp);
	                    	this.rivalPlayer.setPos_q(posq);
	                    	this.rivalPlayer.setCount(countva);
	                    }
		              	
		                long endTime = System.currentTimeMillis();
		                if ((endTime - startTime) > time) {
	                		if(getCount() > this.rivalPlayer.getCount()){
		             			System.out.println("Game Over !! The Winner is "+ getPlayerName());
	               				out.println("END WINNER!!! Your COUNT is " + getCount() + ". Rival Player COUNT is " + this.rivalPlayer.getCount());
	               				rivalPlayer.out.println("END LOSER!!! Your COUNT is " + this.rivalPlayer.getCount() + ". Rival Player COUNT is " + getCount());
		                  	    break;
		                	} else if(getCount() == this.rivalPlayer.getCount()){
		             			System.out.println("Game Over !! There is a Tie");
		           				out.println("END TIE!!!  Your COUNT is " + getCount() + ". Rival Player COUNT is " + this.rivalPlayer.getCount());
	               				rivalPlayer.out.println("END TIE!!! Your COUNT is " + this.rivalPlayer.getCount() + ". Rival Player COUNT is " + getCount());
		            			break;
		                	} else{
		           		   		System.out.println("Game Over !! The Winner is "+ this.rivalPlayer.getPlayerName());
	                			out.println("END LOSER!!!  Your COUNT is " + getCount() + ". Rival Player COUNT is " + this.rivalPlayer.getCount());
	               				rivalPlayer.out.println("END WINNER!!! Your COUNT is " + this.rivalPlayer.getCount() + ". Rival Player COUNT is " + getCount());
		             		    break;
		                	}
	                	} else{
		                	System.out.println(getPlayerName()+" count is "+getCount()+"."+this.rivalPlayer.getPlayerName()+" count is "+this.rivalPlayer.getCount());
							out.println(input);
							rivalPlayer.out.println(input);
		                }
           	       	}
                }
			} catch (IOException e) {
				System.out.println("Error occurred while connecting player : " +playerName);
				System.out.println(e.getMessage());
			} finally {
				try {
					socket.close();
				} catch (IOException e) {
					System.out.println("Couldn't close a socket. " + e.getMessage());
				}
				System.out.println("Connection with player " + playerName + " closed");
			}
		}
		
		public void setRivalPlayer(PlayerThread rivalPlayer) {
			this.rivalPlayer = rivalPlayer;
		}

		public PlayerThread getRivalPlayer(PlayerThread rivalPlayer) {
			return this.rivalPlayer;
		}

		public String getPlayerName() {
			return playerName;
		}

		public void setPlayerName(String playerName) {
			this.playerName = playerName;
		}

		public int getPos_p() {
			return pos_p;
		}

		public void setPos_p(int pos_p) {
			this.pos_p = pos_p;
		}

		public int getPos_q() {
			return pos_q;
		}

		public void setPos_q(int pos_q) {
			this.pos_q = pos_q;
		}

		public int getCount() {
			return count;
		}

		public void setCount(int count) {
			this.count = count;
		}
	}
}