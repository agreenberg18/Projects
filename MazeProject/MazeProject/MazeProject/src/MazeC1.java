import java.awt.BorderLayout;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MazeC1 {
	static PrintWriter out;
	static BufferedReader input;
	static Socket socket;
	public static int N; // dimension of maze
	public static boolean[][] north; // is there a wall to north of cell i, j
	public static boolean[][] east;
	public static boolean[][] south;
	public static boolean[][] west;
	private boolean[][] visited;
	private static String playerName = "Player1";
	public static JFrame frame = new JFrame("Player1 Score");
    public static JLabel msg1 = new JLabel("");
    public static JLabel msg2 = new JLabel("");
    public static JLabel msg3 = new JLabel("");
    public static JPanel boardPanel;
    public static JPanel msgPanel;
    public static boolean end = false;
    public static String filename = "C:/Users/ssiddiqui/Desktop/DOCS/MazeProject/MazeProject/src/images/player2.png";
    public static double er[] ={2.0, 0.0, 3.0, 2.0, 1.0, 0.0, 3.0, 3.0, 2.0, 0.0, 2.0, 1.0, 0.0, 0.0, 3.0, 2.0, 3.0, 3.0, 2.0, 2.0, 
    	0.0, 2.0, 2.0, 2.0, 0.0, 2.0, 0.0, 1.0, 2.0, 0.0, 2.0, 2.0, 0.0, 3.0, 1.0, 1.0, 0.0, 0.0, 3.0, 0.0, 3.0, 0.0, 3.0, 1.0, 1.0, 0.0, 
    	2.0, 2.0, 3.0, 1.0, 2.0, 1.0, 0.0, 0.0, 0.0, 0.0, 3.0, 0.0, 3.0, 1.0, 1.0, 0.0, 2.0, 3.0, 0.0, 2.0, 2.0, 2.0, 1.0, 2.0, 3.0, 1.0, 2.0, 0.0, 3.0,
		3.0, 2.0, 1.0, 1.0, 2.0, 2.0, 0.0, 3.0, 0.0, 1.0, 2.0, 0.0, 3.0, 1.0, 1.0, 1.0, 0.0, 3.0, 1.0, 3.0, 2.0, 0.0, 3.0, 1.0, 3.0,
		2.0, 3.0, 3.0, 3.0, 3.0, 0.0, 2.0, 0.0, 1.0, 1.0, 1.0, 0.0, 0.0, 3.0, 3.0, 3.0, 2.0, 1.0, 3.0, 1.0, 3.0, 1.0, 2.0, 2.0, 2.0,
		2.0, 0.0, 0.0, 1.0, 1.0, 2.0, 0.0, 2.0, 3.0, 1.0, 0.0, 2.0, 1.0, 0.0, 2.0, 2.0, 1.0, 0.0, 2.0, 2.0, 1.0, 1.0, 2.0, 1.0, 2.0,
		0.0, 2.0, 0.0, 3.0, 2.0, 0.0, 2.0, 1.0, 1.0, 0.0, 0.0, 1.0, 3.0, 2.0, 1.0, 2.0, 2.0, 1.0, 0.0, 1.0, 0.0, 3.0, 3.0, 2.0, 3.0,
		2.0, 0.0, 3.0, 3.0, 1.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 2.0, 2.0, 0.0, 2.0, 2.0, 0.0, 2.0, 0.0, 2.0, 0.0, 2.0, 2.0,
		2.0, 2.0, 2.0, 2.0, 0.0, 1.0, 1.0, 1.0, 2.0, 0.0, 3.0, 2.0, 0.0, 1.0, 3.0, 2.0, 2.0, 1.0, 2.0, 3.0, 3.0, 0.0, 2.0, 1.0, 1.0,
		3.0, 2.0, 3.0, 2.0, 2.0, 0.0, 1.0, 3.0, 2.0, 1.0, 2.0, 0.0, 3.0, 3.0, 0.0, 3.0, 0.0, 2.0, 0.0, 3.0, 3.0, 3.0, 1.0, 2.0, 0.0,
		0.0, 0.0, 3.0, 2.0, 0.0, 3.0, 1.0, 2.0, 3.0, 0.0, 3.0, 0.0, 2.0, 1.0, 1.0, 1.0, 3.0, 2.0, 1.0, 2.0, 3.0, 0.0, 2.0, 2.0, 0.0,
		3.0, 2.0, 1.0, 0.0, 3.0, 3.0, 2.0, 0.0, 2.0, 1.0, 0.0, 0.0, 3.0, 2.0, 3.0, 3.0, 2.0, 2.0, 0.0, 2.0, 2.0, 2.0, 0.0,
		2.0, 0.0, 1.0, 2.0, 0.0, 2.0, 2.0, 0.0, 3.0, 1.0, 1.0, 0.0, 0.0, 3.0, 0.0, 3.0, 0.0, 3.0, 1.0, 1.0, 0.0, 2.0, 2.0, 3.0, 1.0,
		2.0, 1.0, 0.0, 0.0, 0.0, 0.0, 3.0, 0.0, 3.0, 1.0, 1.0, 0.0, 2.0, 3.0, 0.0, 2.0, 2.0, 2.0, 1.0, 2.0, 3.0, 1.0, 2.0, 0.0, 3.0,
		3.0, 2.0, 1.0, 1.0, 2.0, 2.0, 0.0, 3.0, 0.0, 1.0, 2.0, 0.0, 3.0, 1.0, 1.0, 1.0, 0.0, 3.0, 1.0, 3.0, 2.0, 0.0, 3.0, 1.0, 3.0,
		2.0, 3.0, 3.0, 3.0, 3.0, 0.0, 2.0, 0.0, 1.0, 1.0, 1.0, 0.0, 0.0, 3.0, 3.0, 3.0, 2.0, 1.0, 3.0, 1.0, 3.0, 1.0, 2.0, 2.0, 2.0,
		2.0, 0.0, 0.0, 1.0, 1.0, 2.0, 0.0, 2.0, 3.0, 1.0, 0.0, 2.0, 1.0, 0.0, 2.0, 2.0, 1.0, 0.0, 2.0, 2.0, 1.0, 1.0, 2.0, 1.0, 2.0,
		0.0, 2.0, 0.0, 3.0,2.0, 0.0, 3.0, 2.0, 1.0, 0.0, 3.0, 3.0, 2.0, 0.0, 2.0, 1.0, 0.0, 0.0, 3.0, 2.0, 3.0, 3.0, 2.0, 2.0, 
    	0.0, 2.0, 2.0, 2.0, 0.0, 2.0, 0.0, 1.0, 2.0, 0.0, 2.0, 2.0, 0.0, 3.0, 1.0, 1.0, 0.0, 0.0, 3.0, 0.0, 3.0, 0.0, 3.0, 1.0, 1.0, 0.0, 
    	2.0, 2.0, 3.0, 1.0, 2.0, 1.0, 0.0, 0.0, 0.0, 0.0, 3.0, 0.0, 3.0, 1.0, 1.0, 0.0, 2.0, 3.0, 0.0, 2.0, 2.0, 2.0, 1.0, 2.0, 3.0, 1.0, 2.0, 0.0, 3.0,
		3.0, 2.0, 1.0, 1.0, 2.0, 2.0, 0.0, 3.0, 0.0, 1.0, 2.0, 0.0, 3.0, 1.0, 1.0, 1.0, 0.0, 3.0, 1.0, 3.0, 2.0, 0.0, 3.0, 1.0, 3.0,
		2.0, 3.0, 3.0, 3.0, 3.0, 0.0, 2.0, 0.0, 1.0, 1.0, 1.0, 0.0, 0.0, 3.0, 3.0, 3.0, 2.0, 1.0, 3.0, 1.0, 3.0, 1.0, 2.0, 2.0, 2.0,
		2.0, 0.0, 0.0, 1.0, 1.0, 2.0, 0.0, 2.0, 3.0, 1.0, 0.0, 2.0, 1.0, 0.0, 2.0, 2.0, 1.0, 0.0, 2.0, 2.0, 1.0, 1.0, 2.0, 1.0, 2.0,
		0.0, 2.0, 0.0, 3.0, 2.0, 0.0, 2.0, 1.0, 1.0, 0.0, 0.0, 1.0, 3.0, 2.0, 1.0, 2.0, 2.0, 1.0, 0.0, 1.0, 0.0, 3.0, 3.0, 2.0, 3.0,
		2.0, 0.0, 3.0, 3.0, 1.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 2.0, 2.0, 0.0, 2.0, 2.0, 0.0, 2.0, 0.0, 2.0, 0.0, 2.0, 2.0,
		2.0, 2.0, 2.0, 2.0, 0.0, 1.0, 1.0, 1.0, 2.0, 0.0, 3.0, 2.0, 0.0, 1.0, 3.0, 2.0, 2.0, 1.0, 2.0, 3.0, 3.0, 0.0, 2.0, 1.0, 1.0,
		3.0, 2.0, 3.0, 2.0, 2.0, 0.0, 1.0, 3.0, 2.0, 1.0, 2.0, 0.0, 3.0, 3.0, 0.0, 3.0, 0.0, 2.0, 0.0, 3.0, 3.0, 3.0, 1.0, 2.0, 0.0,
		0.0, 0.0, 3.0, 2.0, 0.0, 3.0, 1.0, 2.0, 3.0, 0.0, 3.0, 0.0, 2.0, 1.0, 1.0, 1.0, 3.0, 2.0, 1.0, 2.0, 3.0, 0.0, 2.0, 2.0, 0.0,
		3.0, 2.0, 1.0, 0.0, 3.0, 3.0, 2.0, 0.0, 2.0, 1.0, 0.0, 0.0, 3.0, 2.0, 3.0, 3.0, 2.0, 2.0, 0.0, 2.0, 2.0, 2.0, 0.0,
		2.0, 0.0, 1.0, 2.0, 0.0, 2.0, 2.0, 0.0, 3.0, 1.0, 1.0, 0.0, 0.0, 3.0, 0.0, 3.0, 0.0, 3.0, 1.0, 1.0, 0.0, 2.0, 2.0, 3.0, 1.0,
		2.0, 1.0, 0.0, 0.0, 0.0, 0.0, 3.0, 0.0, 3.0, 1.0, 1.0, 0.0, 2.0, 3.0, 0.0, 2.0, 2.0, 2.0, 1.0, 2.0, 3.0, 1.0, 2.0, 0.0, 3.0,
		3.0, 2.0, 1.0, 1.0, 2.0, 2.0, 0.0, 3.0, 0.0, 1.0, 2.0, 0.0, 3.0, 1.0, 1.0, 1.0, 0.0, 3.0, 1.0, 3.0, 2.0, 0.0, 3.0, 1.0, 3.0,
		2.0, 3.0, 3.0, 3.0, 3.0, 0.0, 2.0, 0.0, 1.0, 1.0, 1.0, 0.0, 0.0, 3.0, 3.0, 3.0, 2.0, 1.0, 3.0, 1.0, 3.0, 1.0, 2.0, 2.0, 2.0,
		2.0, 0.0, 0.0, 1.0, 1.0, 2.0, 0.0, 2.0, 3.0, 1.0, 0.0, 2.0, 1.0, 0.0, 2.0, 2.0, 1.0, 0.0, 2.0, 2.0, 1.0, 1.0, 2.0, 1.0, 2.0,
		0.0, 2.0, 0.0, 3.0,2.0, 0.0, 3.0, 2.0, 1.0, 0.0, 3.0, 3.0, 2.0, 0.0, 2.0, 1.0, 0.0, 0.0, 3.0, 2.0, 3.0, 3.0, 2.0, 2.0, 
    	0.0, 2.0, 2.0, 2.0, 0.0, 2.0, 0.0, 1.0, 2.0, 0.0, 2.0, 2.0, 0.0, 3.0, 1.0, 1.0, 0.0, 0.0, 3.0, 0.0, 3.0, 0.0, 3.0, 1.0, 1.0, 0.0, 
    	2.0, 2.0, 3.0, 1.0, 2.0, 1.0, 0.0, 0.0, 0.0, 0.0, 3.0, 0.0, 3.0, 1.0, 1.0, 0.0, 2.0, 3.0, 0.0, 2.0, 2.0, 2.0, 1.0, 2.0, 3.0, 1.0, 2.0, 0.0, 3.0,
		3.0, 2.0, 1.0, 1.0, 2.0, 2.0, 0.0, 3.0, 0.0, 1.0, 2.0, 0.0, 3.0, 1.0, 1.0, 1.0, 0.0, 3.0, 1.0, 3.0, 2.0, 0.0, 3.0, 1.0, 3.0,
		2.0, 3.0, 3.0, 3.0, 3.0, 0.0, 2.0, 0.0, 1.0, 1.0, 1.0, 0.0, 0.0, 3.0, 3.0, 3.0, 2.0, 1.0, 3.0, 1.0, 3.0, 1.0, 2.0, 2.0, 2.0,
		2.0, 0.0, 0.0, 1.0, 1.0, 2.0, 0.0, 2.0, 3.0, 1.0, 0.0, 2.0, 1.0, 0.0, 2.0, 2.0, 1.0, 0.0, 2.0, 2.0, 1.0, 1.0, 2.0, 1.0, 2.0,
		0.0, 2.0, 0.0, 3.0, 2.0, 0.0, 2.0, 1.0, 1.0, 0.0, 0.0, 1.0, 3.0, 2.0, 1.0, 2.0, 2.0, 1.0, 0.0, 1.0, 0.0, 3.0, 3.0, 2.0, 3.0,
		2.0, 0.0, 3.0, 3.0, 1.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 2.0, 2.0, 0.0, 2.0, 2.0, 0.0, 2.0, 0.0, 2.0, 0.0, 2.0, 2.0,
		2.0, 2.0, 2.0, 2.0, 0.0, 1.0, 1.0, 1.0, 2.0, 0.0, 3.0, 2.0, 0.0, 1.0, 3.0, 2.0, 2.0, 1.0, 2.0, 3.0, 3.0, 0.0, 2.0, 1.0, 1.0,
		3.0, 2.0, 3.0, 2.0, 2.0, 0.0, 1.0, 3.0, 2.0, 1.0, 2.0, 0.0, 3.0, 3.0, 0.0, 3.0, 0.0, 2.0, 0.0, 3.0, 3.0, 3.0, 1.0, 2.0, 0.0,
		0.0, 0.0, 3.0, 2.0, 0.0, 3.0, 1.0, 2.0, 3.0, 0.0, 3.0, 0.0, 2.0, 1.0, 1.0, 1.0, 3.0, 2.0, 1.0, 2.0, 3.0, 0.0, 2.0, 2.0, 0.0,
		3.0, 2.0, 1.0, 0.0, 3.0, 3.0, 2.0, 0.0, 2.0, 1.0, 0.0, 0.0, 3.0, 2.0, 3.0, 3.0, 2.0, 2.0, 0.0, 2.0, 2.0, 2.0, 0.0,
		2.0, 0.0, 1.0, 2.0, 0.0,};

	public static int i = 0;
	
    

	static int p = 18;
	static int q = 1;
	public static int count = 0;
	public static int rivalCount = 0;
	public static boolean[][] vis = {
		{ true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true},
		{ true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, false,true },
		{ true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true },
		{ true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true},
		{ true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true},
		{ true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true},
		{ true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true},
		{ true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true},
		{ true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true},
		{ true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true},
		{ true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true},
		{ true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true},
		{ true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true},
		{ true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true},
		{ true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true},
		{ true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true},
		{ true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true},
		{ true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true},
		{ true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true},
		{ true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true},
		{ true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true},
		{ true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true} };

	public MazeC1() {

	}

	public MazeC1(int N, Socket s, String playerName) {
		this.N = N;
		this.socket = s;
		this.playerName = playerName;
		StdDraw1.setXscale(0, N + 2);
		StdDraw1.setYscale(0, N + 2);
		try {
			out = new PrintWriter(socket.getOutputStream(), true);
			out.println("Connected");
			input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
	    	boardPanel = new JPanel();
	    	boardPanel.setBackground(Color.LIGHT_GRAY);
	    	msgPanel = new JPanel(new BorderLayout());
	    	msgPanel.setBackground(Color.LIGHT_GRAY);
	    	
	    	msg1.setText("Your COUNT is 0");
	    	msg2.setText("Rival COUNT is 0");
	    	msg3.setText("");
	    	
	    	msgPanel.add(msg1, BorderLayout.NORTH);
	    	msgPanel.add(msg2, BorderLayout.SOUTH);
	    	msgPanel.add(msg3, BorderLayout.CENTER);
	    	
	    	boardPanel.add(msgPanel);
	        frame.add(boardPanel);
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setSize(520,150);
			frame.setVisible(true);
			frame.setResizable(false);
			

		} catch (Exception e) {
		}
		init();
		generate();
	}

	private void init() {
		// initialize border cells as already visited
		visited = new boolean[N + 2][N + 2];
		for (int x = 0; x < N + 2; x++) {
			visited[x][0] = true;
			visited[x][N + 1] = true;
		}
		for (int y = 0; y < N + 2; y++) {
			visited[0][y] = true;
			visited[N + 1][y] = true;
		}

		// initialze all walls as present
		north = new boolean[N + 2][N + 2];
		east = new boolean[N + 2][N + 2];
		south = new boolean[N + 2][N + 2];
		west = new boolean[N + 2][N + 2];
		for (int x = 0; x < N + 2; x++) {
			for (int y = 0; y < N + 2; y++) {
				north[x][y] = true;
				east[x][y] = true;
				south[x][y] = true;
				west[x][y] = true;

			}
		}
		for (int x = 1; x <= N; x++) {
			for (int y = 1; y <= N; y++) {
				StdDraw1.filledCircle(x + 0.5, y + 0.5, 0.25);
			}
		}
	}

	// generate the maze
	private void generate(int x, int y) {
		visited[x][y] = true;
		while (!visited[x][y + 1] || !visited[x + 1][y] || !visited[x][y - 1]
				|| !visited[x - 1][y]) {
			while (true) {
				double r = er[i];
				i++;
				if (r == 0 && !visited[x][y + 1]) {
					north[x][y] = false;
					south[x][y + 1] = false;
					generate(x, y + 1);
					break;
				} else if (r == 1 && !visited[x + 1][y]) {
					east[x][y] = false;
					west[x + 1][y] = false;
					generate(x + 1, y);
					break;
				} else if (r == 2 && !visited[x][y - 1]) {
					south[x][y] = false;
					north[x][y - 1] = false;
					generate(x, y - 1);
					break;
				} else if (r == 3 && !visited[x - 1][y]) {
					west[x][y] = false;
					east[x - 1][y] = false;
					generate(x - 1, y);
					break;
				}
			}
		}
	}

	private void generate() {
		generate(1, 1);
	}
	
	public void draw() {
		StdDraw1.picture(18 + 0.5, 1 + 0.5, filename);
		StdDraw1.setPenColor(StdDraw1.BLACK);
		for (int x = 1; x <= N; x++) {
			for (int y = 1; y <= N; y++) {
				if (south[x][y])
					StdDraw1.line(x, y, x + 1, y);
				if (north[x][y])
					StdDraw1.line(x, y + 1, x + 1, y + 1);
				if (west[x][y])
					StdDraw1.line(x, y, x, y + 1);
				if (east[x][y])
					StdDraw1.line(x + 1, y, x + 1, y + 1);
			}
		}
		StdDraw1.show(1000);
	}

	public static boolean[][] getsouth() {
		return south;
	}

	public static boolean[][] getnorth() {
		return north;
	}

	public static boolean[][] getwest() {
		return west;
	}

	public static boolean[][] geteast() {
		return east;
	}

	public static int giveN() {
		return N;
	}

	public static void mazeprogramDown(){
		boolean [][]south= MazeC1.getsouth();
		if(!end){
			if((q>0) && (q<19)){
				if((south[p][q])){
					StdDraw1.picture(p + 0.5, q + 0.5, filename);
					StdDraw1.show(10);
				} else {
					if ((vis[p][q - 1] != false)) {
						count++;
					}
					StdDraw1.setPenColor(StdDraw1.LIGHT_GRAY);
	    			StdDraw1.filledCircle(p + 0.5, q + 0.5, 0.5);
					out.println("Down"+" "+getPlayerName()+" "+p+" "+q+" "+getCount());
	    
					StdDraw1.picture(p + 0.5, q + 0.5-1, filename);
					StdDraw1.show(10);
					MazeC1.msg1.setText("Your COUNT is " + getCount());
					q--;
					vis[p][q]=false;
				}
			} else {
				System.out.println("Wrong Move : You have hit the Border of maze");
			}
		}
	}

	public static void mazeprogramRight() {
		boolean[][] east = MazeC1.geteast();
		if(!end){
			if ((p < 19) || (p > 0)) {
				if ((east[p][q])) {
					StdDraw1.picture(p + 0.5, q + 0.5, filename);
					StdDraw1.show(10);

				} else {
					if ((vis[p + 1][q] != false)) {
					count++;
					}
				    StdDraw1.setPenColor(StdDraw1.LIGHT_GRAY);
				    StdDraw1.filledCircle(p + 0.5, q + 0.5, 0.5);
					out.println("Right"+" "+getPlayerName()+" "+p+" "+q+" "+getCount());
					StdDraw1.picture(p + 0.5 + 1, q + 0.5, filename);
					StdDraw1.show(10);
					MazeC1.msg1.setText("Your COUNT is " + getCount());
					p++;
					vis[p][q] = false;
				} 
			} else {
				System.out.println("Wrong Move : You have hit the Border of maze");
			}
		}
	}

	public static void mazeprogramLeft() {
		boolean[][] west = MazeC1.getwest();
		if(!end){
			if ((p < 19) || (p > 0)) {
				if ((west[p][q])) {
					StdDraw1.picture(p + 0.5, q + 0.5, filename);
					StdDraw1.show(10);

				} else { 
					if ((vis[p - 1][q] != false)){
						count++; 
					}
				    StdDraw1.setPenColor(StdDraw1.LIGHT_GRAY);
				    StdDraw1.filledCircle(p + 0.5, q + 0.5, 0.5);
				    out.println("Left"+" "+getPlayerName()+" "+p+" "+q+" "+getCount());
				    StdDraw1.picture(p + 0.5 - 1, q + 0.5, filename);
					StdDraw1.show(10);
					MazeC1.msg1.setText("Your COUNT is " + getCount());
					p--;
					vis[p][q] = false;	
				}
			} else {
				System.out.println("Wrong Move : You have hit the Border of maze");
			}
		}
	}

	public static void mazeprogramUp() {
		boolean[][] north = MazeC1.getnorth();
		if(!end){
			if ((q < 19) || (q > 0)) {
				if ((north[p][q])) {
					StdDraw1.picture(p + 0.5, q + 0.5, filename);
					StdDraw1.show(10);
				} else {
					if ((vis[p][q + 1] != false)){
						count++;
					}
					StdDraw1.setPenColor(StdDraw1.LIGHT_GRAY);
					StdDraw1.filledCircle(p + 0.5, q + 0.5, 0.5);
					out.println("Up"+" "+getPlayerName()+" "+p+" "+q+" "+getCount());
					StdDraw1.picture(p + 0.5, q + 0.5 + 1, filename);
					StdDraw1.show(10);
					MazeC1.msg1.setText("Your COUNT is " + getCount());
					q++;
					vis[p][q] = false;
				} 
			} else {
				System.out.println("Wrong Move : You have hit the Border of maze");
			}
		}
	}
	
	public static int getCount() {
		return count;
	}
	
	public static int getRivalCount() {
		return rivalCount;
	}
	public static void setRivalCount(int rivalCount) {
		rivalCount = rivalCount;
	}
	public static int positionp(){
		return p;
	}
	
	public static int positionq(){
		return q;
	}
	
	public static void finishGame(String message) {
		end = true;
		MazeC1.msg1.setText(message);
		repaint();
	}
	
	public static void repaint(){
		//repaint the maze
		/*init();
		generate();*/
	}
	
	public static String getPlayerName() {
		return playerName;
	}

	public static void setPlayerName(String playerName) {
		MazeC1.playerName = playerName;
	}

	public static void main(String[] args) throws IOException {
	}
}