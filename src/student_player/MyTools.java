package student_player;

import boardgame.BoardState;
import boardgame.Move;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import pentago_swap.PentagoPlayer;
import pentago_swap.PentagoBoardState.Piece;
import pentago_swap.PentagoBoardState.Quadrant;
import pentago_swap.PentagoBoardState;
import pentago_swap.PentagoBoardState;
import pentago_swap.PentagoCoord;
import pentago_swap.PentagoMove;




public class MyTools {
    public static PentagoMove getMove(PentagoBoardState s, int PlayerID) {
    	//OPENING MOVES: Player always starts in available center spots of each quadrant for first two moves
    	PentagoMove myMove = (PentagoMove) s.getRandomMove();
    	
    	if(PlayerID == 0 && s.getTurnNumber() == 0) {	
    		PentagoMove move = new PentagoMove(1,1,Quadrant.TL,Quadrant.BL,PlayerID);
    		myMove = move;
       } 
    	else if(PlayerID == 1 && s.getTurnNumber() == 0) {
    		PentagoMove move = new PentagoMove(1,1,Quadrant.TL,Quadrant.BL,PlayerID);
   			if(s.isLegal(move) == true) myMove = move;
   			else{
   				move = new PentagoMove(1,4,Quadrant.TL,Quadrant.BL,PlayerID);
   				myMove = move;
   			}
       }
    	//check for available spaces
    	else if(PlayerID == 0 && s.getTurnNumber() == 1) {
    		PentagoMove move = new PentagoMove(1,1,Quadrant.TR,Quadrant.BR,PlayerID);
   			if(s.isLegal(move) == true) myMove = move;
   			move = new PentagoMove(1,4,Quadrant.TR,Quadrant.BR,PlayerID);
   			if(s.isLegal(move) == true) myMove = move;
   			move = new PentagoMove(4,4,Quadrant.TR,Quadrant.BR,PlayerID);
   			if(s.isLegal(move) == true) myMove = move;
   			move = new PentagoMove(4,1,Quadrant.TR,Quadrant.BR,PlayerID);
   			if(s.isLegal(move) == true) myMove = move;
    	}
    	else if(PlayerID == 1 && s.getTurnNumber() == 1){
    		PentagoMove move = new PentagoMove(1,1,Quadrant.TR,Quadrant.BR,PlayerID);
   			if(s.isLegal(move) == true) myMove = move;
   			move = new PentagoMove(1,4,Quadrant.TR,Quadrant.BR,PlayerID);
   			if(s.isLegal(move) == true) myMove = move;
   			move = new PentagoMove(4,4,Quadrant.TR,Quadrant.BR,PlayerID);
   			if(s.isLegal(move) == true) myMove = move;
   			move = new PentagoMove(4,1,Quadrant.TR,Quadrant.BR,PlayerID);
   			if(s.isLegal(move) == true) myMove = move;
    	}
    	else {
    		//for subsequent moves, act according to heuristic
    		//create a root node that contains current board state
    		TreeNode root = new TreeNode(s, new ArrayList<>(), null);
    		//old MonteCarlo method
    		//MonteCarlo makeTree = new MonteCarlo(root, PlayerID);
    		Heuristic makeTree = new Heuristic(root, PlayerID);
    		//call getBestMove with current board state
    		myMove = (PentagoMove) makeTree.getBestMove(s);
    		
    	}
    	return myMove;
    }
 
  }

	//old MonteCarlo class
//class MonteCarlo {
//	private TreeNode root;
//	private int PlayerID;
//	
//	public MonteCarlo(TreeNode root, int PlayerID) {
//		this.root = root;
//		this.PlayerID = PlayerID;
//	}
//	
//	public Move getBestMove(PentagoBoardState state, int PlayerID) {
//		Move bestMove = state.getRandomMove();
//		
//		ArrayList<PentagoMove> legalMoves = state.getAllLegalMoves();
//		
//		for(PentagoMove m: legalMoves) {
//			PentagoBoardState clone = (PentagoBoardState)state.clone();
//			clone.processMove(m);
//			if(clone.getWinner() == PlayerID) {
//				return m;
//			}			
//		}
//		long totalTime = System.nanoTime();
//		while(System.nanoTime()-totalTime < 1900000000) {	
//			descent();
//		}
//		TreeNode best = getBestChildren(root);
//		return best.getMove();
//	}
//	
//	public void descent() {
//		//rollout(root);
//		//PentagoMove goodMove;
//		
//		//PentagoBoardState stateClone = (PentagoBoardState)state.clone();
//		TreeNode temp = root;
//		Random randObj = new Random();
//		double p = 0.3;
//		while(!(temp.getChildren().isEmpty())) {
//			if(randObj.nextFloat() <= p) {
//				//temp = (nextNewNode(temp));
//					break;
//	    		}
//			temp = getBestChildren(temp);
//		}
//		TreeNode child = nextNewNode(temp);
//		temp.addChild(child);
//		//stateClone.processMove(m);
//		//TreeNode child = new TreeNode(stateClone, null, temp);
//		//child.setMove(m);
//		boolean wins = rollout(child.getBoardState());
//		update(child, wins);
//		
//	}
//	
//	public TreeNode nextNewNode(TreeNode node) {
//		PentagoMove goodMove = (PentagoMove)node.getBoardState().getRandomMove();
//		PentagoBoardState stateClone = (PentagoBoardState)node.getBoardState().clone();
//		stateClone.processMove(goodMove);
//		TreeNode newTree = new TreeNode(stateClone, new ArrayList<TreeNode>(), null);
//		//node.addChild(newTree);
//		newTree.setMove(goodMove);
//		if(node.contains(newTree)) {
//			while(!(node.contains(newTree))){
//				goodMove = (PentagoMove)node.getBoardState().getRandomMove();
//				stateClone = (PentagoBoardState)node.getBoardState().clone();
//				stateClone.processMove(goodMove);
//    			newTree = new TreeNode(stateClone, new ArrayList<TreeNode>(), null);
//    			newTree.setMove(goodMove);
//			//node.addChild(c);
//			}
//		}
//		node.addChild(newTree);
//		return newTree;
//	}
//	
//	public boolean rollout(PentagoBoardState state) {
//		PentagoBoardState stateClone = (PentagoBoardState)state.clone();
//		boolean won = false;
//		while(stateClone.gameOver() == false) {
//			Move next = stateClone.getRandomMove();
//			stateClone.processMove((PentagoMove)next);
//		}
//		if(stateClone.getWinner() == this.getPlayerID()) {
//			won = true;
//		}
//		return won;
//	}
//	
//	public void update(TreeNode child, boolean win) {
//		while(child!=null) {
//			child.setNodeVisit(child.getNodeVisit()+1);
//			if(win) {
//				child.setNumWins(child.getNumWins()+1);
//			}
//			child.setValue();
//			child = child.getParent();
//		}
//	}
//	
//	public TreeNode getBestChildren(TreeNode parent) {
//		TreeNode bestChild = parent;
//		double bestVal = 0;
//		for(TreeNode n: parent.getChildren()) {
//			if(n.getValue() > bestVal) {
//				bestChild = n;
//				bestVal = bestChild.getValue();
//			}
//		}
//		return bestChild;
//	}
//	
//	private int getPlayerID() {
//		return PlayerID;
//	}
//
//	private void setPlayerID(int playerID) {
//		PlayerID = playerID;
//	}
//	
//	
//}

class Heuristic{
	private TreeNode root;
	private int PlayerID;
	
	//constructor
	public Heuristic(TreeNode root, int PlayerID) {
		this.root = root;
		this.PlayerID = PlayerID;
	}
	

	
	public Move getBestMove(PentagoBoardState state) {
		//clone current state and get a list of all legal moves
		PentagoBoardState stateClone = (PentagoBoardState)state.clone();
		ArrayList<PentagoMove> legal = stateClone.getAllLegalMoves();
		//initialize moveBest to legal random move
		Move moveBest = stateClone.getRandomMove();
		//initialize temporary variable to store root, and best child as new tree node
		TreeNode temp = root;
		TreeNode bestChild = new TreeNode(null, null, null);
		//bestChild.setEval(0.0);
		//set move to random move
		bestChild.setMove((PentagoMove)moveBest);
		//for each legal move, process on the state clone and get the evaluation function value of the move 
		//evaluation function is based on playerID (the current colour of the player)
		for(PentagoMove m: legal) {
			
			stateClone.processMove(m);
			
			//if the move results in an automatic win, return that move
			if(stateClone.getWinner() == PlayerID) {
				return m;
			}
			
			//evaluate new child of temp, set move to m in legal
			TreeNode node = new TreeNode(stateClone, new ArrayList<>(), null);
			node.setEvaluation(PlayerID);
			node.setMove(m);
			temp.addChild(node);
			stateClone = (PentagoBoardState)state.clone();
			
		}
		//get the best child from all moves based on evaluation function
		for(TreeNode t: temp.getChildren()) {
			
			if(t.getEval()>bestChild.getEval()) {
				bestChild = t;
			}
		}
		
		//return best child
		moveBest = bestChild.getMove();
		
		return moveBest;
	}
}

class TreeNode {
	//old variables for MonteCarlo method:
	//private double value;
	//private double numWins;
	//private double numGames;
	//private double nodeVisit;
	private double eval;
	private PentagoBoardState data;
	private List<TreeNode> children = new ArrayList<>();
	private TreeNode parent;
	private PentagoMove move;
	
	
 public TreeNode(PentagoBoardState data, List<TreeNode> children, TreeNode parent) {
//		this.value = value;
//		this.setNodeVisit(nodeVisit);
//		this.setNumWins(0.0);
//		this.setNodeVisit(0.0);
	 	//Heuristic values
	 	this.eval = 0.0;
		this.children = children;
		this.data = data;
		this.parent = parent;
	}
	
 	//evaluation function
    public void setEvaluation(int PlayerID) {
    	//set eval to 0.0, get current piece colour (line taken from checkWin method in PentagoBoardState
    	double val=0.0;
        Piece currColour = PlayerID == 0 ? Piece.WHITE : Piece.BLACK;
        Piece oppColour = Piece.BLACK;
        int oppID = 0; //id of opponent
        if(currColour == Piece.WHITE) {
        	oppColour = Piece.BLACK;
        	oppID = 1; 
        }else {
        	oppColour = Piece.WHITE;
        	oppID = 0;
        }
        PentagoCoord current = new PentagoCoord(0,0);
        int num = 0; //number of pieces on the board
        int numOpp = 0; //number of opponent pieces on the board
        double numThree = 0.0; //number of "three-in-a-rows"
        double numFour = 0.0; //number of "four-in-a-rows"
       // switch(currColour) {
        //case WHITE:
        
        //check every row:
        for(int i = 0; i<6; i++) {
        	for(int j = 0; j<6; j++) {
        		current = new PentagoCoord(i,j);
        		
        		//increase number of pieces in the row
        		if(currColour == (this.data.getPieceAt(current))) {
        			num++;
        		
        		//increase number of opponents pieces in the row only if number of pieces is greater than four
        		}if(oppColour == (this.data.getPieceAt(current)) && num>=4 ) {
        			numOpp++;
        		}
        	}
        	//if there are three or four pieces in a row, update these numbers and subtract a function based on the number of opponent pieces
        	if(num == 3) {
    			numThree++;
    			numThree -= 0.5*numOpp;
            }if(num == 4) {
    			numFour++;
            }if(numOpp>0) {
            	numFour -= numOpp;
            }
        	
        	num = 0;
        	numOpp=0;
        }
        //do the same evaluation for all columns, diagonals on the board
        //check every column
        for(int j = 0; j<6; j++) {
        	for(int i = 0; i<6; i++) {
        		current = new PentagoCoord(i,j);
        		if(currColour == (this.data.getPieceAt(current))) {
        			num++;
        			
        		}if(oppColour == (this.data.getPieceAt(current))&& num>=4 ) {
        			numOpp++;
        		}
        	}
        	if(num == 3) {
    			numThree++;
    			numThree-= 0.5*numOpp;
            }if(num == 4) {
    			numFour++;
            }if(numOpp>0) {
            	numFour -= numOpp;
            }
        	num = 0;
        	numOpp=0;
        
        }
        if(num == 3) {
			numThree++;
			numThree -= 0.5*numOpp;
		}if(num == 4) {
			numFour++;
			numFour-= 0.4*numOpp;
		}
        num = 0;
    	numOpp=0;
    	
    	//check the first diagonal where it is possible to get five in a row
        int j = 0;
        for(int i = 1; i<6; i++) {
        		
        		current = new PentagoCoord(i,j);
        		if(currColour == (this.data.getPieceAt(current))) {
        			num++;
        			 
        		}if(oppColour == (this.data.getPieceAt(current))) {
        			numOpp++;
        		}
        	j++;
        }
        if(num == 3) {
			numThree++;
			numThree-= 0.5*numOpp;
        }if(num == 4) {
			numFour++;
			numFour -= 0.4*numOpp;
        }
    	num = 0;
    	numOpp=0;
    	
    	//second diagonal
        j=0;
        for(int i = 0; i<6; i++) {
        	
        		current = new PentagoCoord(i,j);
        		if(currColour == (this.data.getPieceAt(current))) {
        			num++;
        			
        		}if(oppColour == (this.data.getPieceAt(current))) {
        			numOpp++;
        		}
        	j++;
        }
        if(num == 3) {
			numThree++;
			numThree -= 0.5*numOpp;
		}if(num == 4) {
			numFour++;
			numFour -= 0.4*numOpp;
		}
    	num = 0;
    	numOpp=0;
    	
    	//third diagonal
        j=1;
        for(int i = 0; i<5; i++) {
        		current = new PentagoCoord(i,j);
        		if(currColour == (this.data.getPieceAt(current))) {
        			num++;
        			 
        		}if(oppColour == (this.data.getPieceAt(current))) {
        			numOpp++;
        		}
        	j++;
        }
        if(num == 3) {
			numThree++;
			numThree-= 0.5*numOpp;
		}if(num == 4) {
			numFour++;
			numFour-= 0.4*numOpp;
		}
    	num = 0;
    	numOpp=0;
    	
    	//fourth diagonal
        j=0;
        for(int i = 4; i>-1; i--) {
        		current = new PentagoCoord(i,j);
        		if(currColour == (this.data.getPieceAt(current))) {
        			num++;
        			
        		}if(oppColour == (this.data.getPieceAt(current))) {
        			numOpp++;
        		}
        	j++;
        }
        if(num == 3) {
			numThree++;
			numThree-= 0.5*numOpp;
		}if(num == 4) {
			numFour++;
			numFour -= 0.4*numOpp;
		}
    	num = 0;
    	numOpp=0;
    	
    	//fifth diagonal
        j=0;
        for(int i = 5; i>-1; i--) {
        		current = new PentagoCoord(i,j);
        		if(currColour == (this.data.getPieceAt(current))) {
        			num++;
        			
        		}if(oppColour == (this.data.getPieceAt(current))) {
        			numOpp++;
        		}
        	j++;
        }
        if(num == 3) {
			numThree++;
			numThree-= 0.5*numOpp;
		}if(num == 4) {
			numFour++;
			numFour-= 0.4*numOpp;
		}
    	num = 0;
    	numOpp=0;
    	
    	//sixth diagonal
        j=1;
        for(int i = 5; i>0; i--) {
        		current = new PentagoCoord(i,j);
        		if(currColour == (this.data.getPieceAt(current))) {
        			num++;
        			
        		}if(oppColour == (this.data.getPieceAt(current))) {
        			numOpp++;
        		}
        	j++;
        }
        if(num == 3) {
			numThree++;
			numThree -= 0.5*numOpp;
		}if(num == 4) {
			numFour++;
			numFour -= 0.4*numOpp;
		}
		
		//update the value of the board state weighting four-in-a-rows more heavily
		val = 2*numThree+5*numFour;
//		//old switch case to make black play more defensively
//		case BLACK:
//			for(int i = 0; i<6; i++) {
//	        	for(int k = 0; k<6; k++) {
//	        		current = new PentagoCoord(i,k);
//	        		
//	        		if(currColour == (this.data.getPieceAt(current))) {
//	        			num++;
//	        			
//	        		}if(oppColour == (this.data.getPieceAt(current)) ) {
//	        			numOpp++;
//	        		}
//	        	}
//	        	if(numOpp == 3) {
//	    			numThree++;
//	    			numThree += 0.5*num;
//	            }if(numOpp == 4) {
//	    			numFour++;
//	    			numFour += 0.5*num;
//	            }if(num == 3) {
//	    			numThree++;
//	    			
//	            }if(num == 4) {
//	    			numFour++;
//	            }
//	        	
//	        	num = 0;
//	        	numOpp=0;
//	        }
//	        num = 0;
//	    	numOpp=0;
//	        for(int k = 0; k<6; k++) {
//	        	for(int i = 0; i<6; i++) {
//	        		current = new PentagoCoord(i,k);
//	        		if(currColour == (this.data.getPieceAt(current))) {
//	        			num++;
//	        			
//	        		}if(oppColour == (this.data.getPieceAt(current))&& num>=4 ) {
//	        			numOpp++;
//	        		}
//	        	}
//	        	if(numOpp == 3) {
//	    			numThree++;
//	    			numThree += 0.5*num;
//	            }if(numOpp == 4) {
//	    			numFour++;
//	    			numFour += 0.5*num;
//	            }if(num == 3) {
//	    			numThree++;
//	    			
//	            }if(num == 4) {
//	    			numFour++;
//	            }
//	        	num = 0;
//	        	numOpp=0;
//	        
//	        }//first diagonal
//	        if(numOpp == 3) {
//    			numThree++;
//    			numThree += 0.5*num;
//            }if(numOpp == 4) {
//    			numFour++;
//    			numFour += 0.5*num;
//            }if(num == 3) {
//    			numThree++;
//    			
//            }if(num == 4) {
//    			numFour++;
//            }
//	        num = 0;
//	    	numOpp=0;
//	        int k = 0;
//	        for(int i = 1; i<6; i++) {
//	        		
//	        		current = new PentagoCoord(i,k);
//	        		if(currColour == (this.data.getPieceAt(current))) {
//	        			num++;
//	        			 
//	        		}if(oppColour == (this.data.getPieceAt(current))) {
//	        			numOpp++;
//	        		}
//	        	k++;
//	        }//second diagonal
//	        if(numOpp == 3) {
//    			numThree++;
//    			numThree += 0.5*num;
//            }if(numOpp == 4) {
//    			numFour++;
//    			numFour += 0.5*num;
//            }if(num == 3) {
//    			numThree++;
//    			
//            }if(num == 4) {
//    			numFour++;
//            }
//	    	num = 0;
//	    	numOpp=0;
//	        k=0;
//	        for(int i = 0; i<6; i++) {
//	        	
//	        		current = new PentagoCoord(i,k);
//	        		if(currColour == (this.data.getPieceAt(current))) {
//	        			num++;
//	        			
//	        		}if(oppColour == (this.data.getPieceAt(current))) {
//	        			numOpp++;
//	        		}
//	        	k++;
//	        }//third diagonal
//	        if(numOpp == 3) {
//    			numThree++;
//    			numThree += 0.5*num;
//            }if(numOpp == 4) {
//    			numFour++;
//    			numFour += 0.5*num;
//            }if(num == 3) {
//    			numThree++;
//    			
//            }if(num == 4) {
//    			numFour++;
//            }
//	    	num = 0;
//	    	numOpp=0;
//	        k=1;
//	        for(int i = 0; i<5; i++) {
//	        		current = new PentagoCoord(i,k);
//	        		if(currColour == (this.data.getPieceAt(current))) {
//	        			num++;
//	        			 
//	        		}if(oppColour == (this.data.getPieceAt(current))) {
//	        			numOpp++;
//	        		}
//	        	k++;
//	        }//fourth diagonal
//	        if(numOpp == 3) {
//    			numThree++;
//    			numThree += 0.5*num;
//            }if(numOpp == 4) {
//    			numFour++;
//    			numFour += 0.5*num;
//            }if(num == 3) {
//    			numThree++;
//    			
//            }if(num == 4) {
//    			numFour++;
//            }
//	    	num = 0;
//	    	numOpp=0;
//	        k=0;
//	        for(int i = 4; i>-1; i--) {
//	        		current = new PentagoCoord(i,k);
//	        		if(currColour == (this.data.getPieceAt(current))) {
//	        			num++;
//	        			
//	        		}if(oppColour == (this.data.getPieceAt(current))) {
//	        			numOpp++;
//	        		}
//	        	k++;
//	        }//fifth diagonal
//	        if(numOpp == 3) {
//    			numThree++;
//    			numThree += 0.5*num;
//            }if(numOpp == 4) {
//    			numFour++;
//    			numFour += 0.5*num;
//            }if(num == 3) {
//    			numThree++;
//    			
//            }if(num == 4) {
//    			numFour++;
//            }
//	    	num = 0;
//	    	numOpp=0;
//	        k=0;
//	        for(int i = 5; i>-1; i--) {
//	        		current = new PentagoCoord(i,k);
//	        		if(currColour == (this.data.getPieceAt(current))) {
//	        			num++;
//	        			
//	        		}if(oppColour == (this.data.getPieceAt(current))) {
//	        			numOpp++;
//	        		}
//	        	k++;
//	        }//sixth diagonal
//	        if(numOpp == 3) {
//    			numThree++;
//    			numThree += 0.5*num;
//            }if(numOpp == 4) {
//    			numFour++;
//    			numFour += 0.5*num;
//            }if(num == 3) {
//    			numThree++;
//    			
//            }if(num == 4) {
//    			numFour++;
//            }
//	    	num = 0;
//	    	numOpp=0;
//	        k=1;
//	        for(int i = 5; i>0; i--) {
//	        		current = new PentagoCoord(i,k);
//	        		if(currColour == (this.data.getPieceAt(current))) {
//	        			num++;
//	        			
//	        		}if(oppColour == (this.data.getPieceAt(current))) {
//	        			numOpp++;
//	        		}
//	        	k++;
//	        }
//	        if(numOpp == 3) {
//    			numThree++;
//    			numThree += 0.5*num;
//            }if(numOpp == 4) {
//    			numFour++;
//    			numFour += 0.5*num;
//            }if(num == 3) {
//    			numThree++;
//    			
//            }if(num == 4) {
//    			numFour++;
//            }
//			val = 2*numThree+3*numFour;
//		case EMPTY:
//			break;
//        }
		
		//check for winner - if opponent wins with the board state, prefer not to select the move
    	if(data.getWinner() == PlayerID) val = Integer.MAX_VALUE;
    	if(data.getWinner() == oppID) val = Integer.MIN_VALUE;
    	this.eval = val;
    }
    
    public double getEval() {
    	return this.eval;
    }

    //method to return whether a state is the same as another state (i.e. symmetric), used in MonteCarlo for efficiency
	public boolean contains(TreeNode node) {
		for(TreeNode n: this.getChildren()) {
			if(n.getBoardState().toString().equals(node.getBoardState().toString())) {
				return true;
			}
		}
		return false;
	}
	 
	public List<TreeNode> getChildren() {
		 return children;
	}
	 
	public PentagoBoardState getBoardState() {
		 return data;
	}
	public void setBoardState(PentagoBoardState data) {
		 this.data = data;
	}
	
	public PentagoMove getMove() {
		return move;
	}

	public void setMove(PentagoMove move) {
		this.move = move;
	}
	public TreeNode addChild(TreeNode child) {
		 child.setParent(this);
		 this.getChildren().add(child);
		 return child;
	}

	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}
	
	 
	public void setParent(TreeNode parent) {
		 this.parent = parent;
	}
	 
	public TreeNode getParent() {
		 return parent;
	}
//	 //old methods for MonteCarlo
//	
//	public boolean isWorse(TreeNode node) {
//		if(node.getEval()>=this.getEval()) {
//			return true;
//		}else return false;
//	}
//	 
//	public void setValue() {
//		 double UTC = this.numWins/this.nodeVisit; //+ Math.sqrt(Math.log(this.parent.nodeVisit)/this.nodeVisit);
//		 this.value = UTC;
//	}
//	
//	public TreeNode getBestValue() {
//		TreeNode best = this.getChildren().get(0);
//		for(TreeNode t: this.getChildren()) {
//			if(best.getValue()<t.getValue()) {
//				best = t;
//			}
//		}
//		return best;
//	}
//	
//	public double getValue() {
//		return this.value;
//	}
//	
//	public double getNumWins() {
//		return numWins;
//	}
//
//	public void setNumWins(double numWins) {
//		this.numWins = numWins;
//	}
//
//	public double getNodeVisit() {
//		return nodeVisit;
//	}
//
//	public void setNodeVisit(double nodeVisit) {
//		this.nodeVisit = nodeVisit;
//	}


}


