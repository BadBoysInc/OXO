import java.util.ArrayList;


class Board {

	
	Player player;
	
	final int cols = 3;
	final int rows = 3;
	
	private char[][] boardStorage;
	
	Board(){
		player = Player.X;
		makeEmptyBoardSpace();
	}
	
	
	private void makeEmptyBoardSpace() {
		boardStorage = new char[this.cols][this.rows];
		for(int x = 0; x < 3; x++){
			for(int i = 0; i < 3; i++){
				boardStorage[x][i] = '.';
			}
		}
		
		
	}


	public static void main(String[] args) {
		Board b = new Board();
		test(b);
	}

	Position position(String pos) {
		if(pos.length()!=2){
			return null;
		}
		try{
			return new Position(pos.charAt(0)-'a', pos.charAt(1)-'0');
		}catch(Error e){
			System.out.println(e.getMessage());
			return null;
		}
		
	}

	private static void test(Board b) {
		System.out.println(b.toString());
		for(int x = 0; x < 3; x++){
			for(int i = 0; i < 3; i++){
				b.move(new Position(x, i));
				System.out.println("--------------");
				System.out.println(b.toString());
				System.out.println("Winner: "+b.winner().toString());
				System.out.println("Still blank:");
				for(Position p: b.blanks()){
					System.out.println(String.format("(%d, %d)", p.row(), p.col()));
				}
			}
		}
	}

	void move(Position p) {
		boardStorage[p.row()][p.col()] = (player == Player.O ? 'O':'X');
		player = player.other();
	}

	Position[] blanks() {
		ArrayList<Position> ps = new ArrayList<Position>();
		for(int r = 0; r < 3; r++){
			for(int c = 0; c < 3; c++){
				if(boardStorage[r][c]=='.'){ps.add(new Position(r, c));}
			}
		}
		Position[] psArray = new Position[ps.size()];
		return (Position[]) ps.toArray(psArray);
	}

	Player winner() {
		if(didWin(Player.O)){
			return Player.O;
		}else if(didWin(Player.X)){
			return Player.X;
		}else if(checkIfOver()){
			return Player.Both;
		}
		return Player.None;
	}


	private boolean checkIfOver() {
		for(int x = 0; x < 3; x++){
			for(int i = 0; i < 3; i++){
				if(boardStorage[x][i]=='.'){return false;}
			}
		}
		return true;
	}


	private boolean didWin(Player o) {
		
		char c = o == Player.O? 'O' : 'X';
		
		//check rows
		for(int x = 0; x < 3; x++){
			int count = 0;
			for(int i = 0; i < 3; i++){
				if(boardStorage[x][i]==c){count++;}
			}
			if(count==3){return true;}
		}
		
		//check cols
		for(int x = 0; x < 3; x++){
			int count = 0;
			for(int i = 0; i < 3; i++){
				if(boardStorage[i][x]==c){count++;}
			}
			if(count==3){return true;}
		}
		
		//check one diagonal
		if(boardStorage[0][0]==c&&boardStorage[1][1]==c&&boardStorage[2][2]==c){
			return true;
		}
		
		//check the other diagonal
		if(boardStorage[0][2]==c&&boardStorage[1][1]==c&&boardStorage[2][0]==c){
			return true;
		}
		
		return false;
	}


	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(char[] cs: boardStorage){
			for(char c: cs){
				sb.append(c);
			}
			sb.append('\n');
		}
		return sb.toString();
	}

	
	
}
