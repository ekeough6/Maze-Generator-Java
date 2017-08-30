import java.util.ArrayList;

public class AsciiMaze {
	private String[][] m;
	private final int NORTH = 0;
	private final int EAST = 1;
	private final int SOUTH = 2;
	private final int WEST = 3;
	private int size;
	
	public AsciiMaze(int size)
	{
		this.size = size;
		randomize();
		
	}
	
	private boolean canMove(int row, int col) {
		return row > 0 && col > 0 && row < size-1 && col < size-1 && m[row][col].equals("#");
	}
	
	private void destroy(int row, int col, int dir) {
		if(dir == NORTH)
		{
			m[row-1][col] = " ";
			m[row-2][col] = " ";
		}
		
		if(dir == EAST)
		{
			m[row][col+1] = " ";
			m[row][col+2] = " ";
		}
		
		if(dir == SOUTH)
		{
			m[row+1][col] = " ";
			m[row+2][col] = " ";
		}
		
		if(dir == WEST)
		{
			m[row][col-1] = " ";
			m[row][col-2] = " ";
		}
	}
	
	private int randomDirection(int row, int col)
	{
		ArrayList<Integer> directions = new ArrayList<Integer>();
		if(canMove(row-2, col))
			directions.add(NORTH);
		if(canMove(row, col+2))
			directions.add(EAST);
		if(canMove(row+2, col))
			directions.add(SOUTH);
		if(canMove(row, col-2))
			directions.add(WEST);
		if(directions.size() == 0)
			return -1;
		return directions.get((int)(Math.random()*directions.size()));
	}
	
	private int[] getLocation(int row, int col, int dir)
	{
		int[] loc = new int[2];
		if(dir == NORTH)
			loc = new int[] {row-2, col};
		else if(dir == EAST)
			loc = new int[] {row, col+2};
		else if(dir == SOUTH)
			loc = new int[] {row+2, col};
		else if(dir == WEST)
			loc = new int[] {row, col-2};
		return loc;
	}
	
	private void init() {
		m = new String[size][size];
		for(int r=0; r<size; r++)
			for(int c=0; c<size; c++)
				m[r][c] = "#";
		m[size-1][1] = "X";
	}
	
	private void generate(int row, int col) {
		boolean con = true;
		while(con)
		{
			int dir = randomDirection(row, col);
			if(dir != -1)
			{
				destroy(row, col, dir);
				int[] loc = getLocation(row, col, dir);
				generate(loc[0], loc[1]);
			}
			else
			{
				con = false;
				break;
			}
		}
	}
	
	private void mazeEnd() {
		int count = 0;
		ArrayList<Integer[]> locs = new ArrayList<Integer[]>();
		for(int r=1; r<size-1; r++)
			for(int c=1; c<size-1; c++)
				if(m[r][c].equals(" "))
				{
					if(m[r-1][c].equals("#"))
						count++;
					if(m[r+1][c].equals("#"))
						count++;
					if(m[r][c+1].equals("#"))
						count++;
					if(m[r][c-1].equals("#"))
						count++;
					if(count == 3)
						locs.add(new Integer[] {r, c});
					count = 0;
				}
		Integer[] loc = locs.get((int)(Math.random()*locs.size()));
		m[loc[0]][loc[1]] = "@";
	}
	
	public void randomize() {
		init();
		generate(size-2, 1);
		mazeEnd();
	}
	
	public String[][] getMaze()
	{
		return m;
	}
	
	public static void main(String[] args) {
		AsciiMaze m = new AsciiMaze(29);
		String[][] maze = m.getMaze();
		for(String[] maz:maze)
		{
			for(String ma:maz)
				System.out.print(ma);
			System.out.println();
		}	
	}

}
