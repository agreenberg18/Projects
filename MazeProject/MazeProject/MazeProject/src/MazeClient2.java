import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class MazeClient2 {
	
	public static String filename = "C:/Users/ssiddiqui/Desktop/DOCS/MazeProject/MazeProject/src/images/player2.png";
	
	public static void main(String[] args)throws IOException {
		MazeClient2 client = new MazeClient2();
		client.connectToServer();
	}
	
	public void connectToServer() throws IOException {
		String serverAddress = getServerAddress();
		System.out.println("Connecting to " + serverAddress);
		Socket s = new Socket(serverAddress, MazeServer.SERVER_PORT);
		new ClientThread(s).start();
	}

	private static String getServerAddress() {
		JFrame frame = new JFrame("");
		return JOptionPane.showInputDialog(frame, "Enter your IP Address:",
				"Welcome to the Maze", JOptionPane.QUESTION_MESSAGE);
	}
	
	private static class ClientThread extends Thread {
		private Socket socket;
		BufferedReader in;
		PrintWriter out;
		String playerName;
		
		public ClientThread(Socket socket){	       
			this.socket = socket;
			initializeIOStreams();
		}
		
		private void initializeIOStreams() {
			try {
				this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				this.out = new PrintWriter(socket.getOutputStream(), true);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		public void run(){
			try{
                while (true) {
                	String input = in.readLine();
                	System.out.println("input read => " + input);
	                if (input == null) {
	                } else if (input.startsWith("Welcome")) {
						String splited[] = input.split("\\s");
						this.playerName = splited[1];
						System.out.println("The client2-player: "+getPlayerName()+" is connected to server");
					}  else if(input.startsWith("INITIALIZE")){
						System.out.println("Initialize Maze for " + getPlayerName());
						MazeC2 maze = new MazeC2(18, socket, getPlayerName());	            	            
			            StdDraw2.show(0);
			        	maze.draw();
					} else if(input.startsWith("END")){
						System.out.println("Game is Finished.");
						System.out.println(input);
						String message = input.replaceFirst("END", "");
						MazeC2.finishGame(message);
						MazeC2.msg1.setText("");
						MazeC2.msg2.setText("");
						MazeC2.msg1.setVisible(false);
						MazeC2.msg2.setVisible(false);
						MazeC2.msg3.setText(message);
						socket.close();
					} else if(input.startsWith("Down")){
	            		String[] splited = input.split("\\s");                             
	            		if(!splited[1].equalsIgnoreCase(getPlayerName())){
							int posp = Integer.parseInt(splited[2]);
							int posq = Integer.parseInt(splited[3]);
							int countva = Integer.parseInt(splited[4]);
							
							System.out.println("Your COUNT is " + MazeC2.getCount() + ". Rival Player COUNT is " + countva);
							
							StdDraw2.setPenColor(StdDraw2.ORANGE);
	                		StdDraw2.filledCircle(posp + 0.5, posq + 0.5, 0.5);
	                		StdDraw2.show(10);
	                		StdDraw2.picture(posp + 0.5, (posq-1) + 0.5, filename);
	                		StdDraw2.show(10);
	                		MazeC2.vis[posp][posq] = false;
	                		MazeC2.setRivalCount(countva);
	                		MazeC2.msg2.setText("Rival COUNT is " + countva);	
	            		}
           	       	} else if(input.startsWith("Right")){
           	       		String[] splited = input.split("\\s+");                             
           	       		if(!splited[1].equalsIgnoreCase(getPlayerName())){
							int posp = Integer.parseInt(splited[2]);
							int posq = Integer.parseInt(splited[3]);
							int countva = Integer.parseInt(splited[4]);
						
							System.out.println("Your COUNT is " + MazeC2.getCount() + ". Rival Player COUNT is " + countva);
							
							StdDraw2.setPenColor(StdDraw2.ORANGE);
		            		StdDraw2.filledCircle(posp + 0.5, posq + 0.5, 0.5);
		            	    StdDraw2.show(10);   
		            	    StdDraw2.picture((posp+1) + 0.5, posq + 0.5, filename);
		            	    StdDraw2.show(10);
		            	    MazeC2.vis[posp][posq] = false;
		            	    MazeC2.setRivalCount(countva);
		            	    MazeC2.msg2.setText("Rival COUNT is " + countva);
           	       		}
           	       	} else if(input.startsWith("Left")){
           	       		String[] splited = input.split("\\s+");                             
           	       		if(!splited[1].equalsIgnoreCase(getPlayerName())){
							int posp = Integer.parseInt(splited[2]);
							int posq = Integer.parseInt(splited[3]);
							int countva = Integer.parseInt(splited[4]);
						
							System.out.println("Your COUNT is " + MazeC2.getCount() + ". Rival Player COUNT is " + countva);
							
							StdDraw2.setPenColor(StdDraw2.ORANGE);
		                	StdDraw2.filledCircle(posp + 0.5, posq + 0.5, 0.5);
		             	    StdDraw2.show(10);
		             	    StdDraw2.picture((posp-1) + 0.5, posq + 0.5, filename);
		           	        StdDraw2.show(10);
		             	    MazeC2.vis[posp][posq] = false;
		             	    MazeC2.setRivalCount(countva);
		             	    MazeC2.msg2.setText("Rival COUNT is " + countva);
           	       		}
           	       	} else if(input.startsWith("Up")){
		            	String[] splited = input.split("\\s+");                             
		            	if(!splited[1].equalsIgnoreCase(getPlayerName())){
							int posp = Integer.parseInt(splited[2]);
							int posq = Integer.parseInt(splited[3]);
							int countva = Integer.parseInt(splited[4]);
							
							System.out.println("Your COUNT is " + MazeC2.getCount() + ". Rival Player COUNT is " + countva);
							
							StdDraw2.setPenColor(StdDraw2.ORANGE);
		                	StdDraw2.filledCircle(posp + 0.5, posq + 0.5, 0.5);
		              	    StdDraw2.show(10);
		              	    StdDraw2.picture(posp + 0.5, (posq+1) + 0.5, filename);
		          	        StdDraw2.show(10);
		              	    MazeC2.vis[posp][posq] = false;
		              	    MazeC2.setRivalCount(countva);
		              	    MazeC2.msg2.setText("Rival COUNT is " + countva);
		            	}
           	       	}
                }
			} catch (IOException e) {
				System.out.println("Error handling client2 " + e);
			} finally {
				try {
					socket.close();
				} catch (IOException e) {
					System.out.println("Couldn't close a socket:" + e.getMessage());
				}
				System.out.println("Client2 connection closed");
			}
		}
		
		public String getPlayerName() {
			return playerName;
		}

		public void setPlayerName(String playerName) {
			this.playerName = playerName;
		}
	}
}