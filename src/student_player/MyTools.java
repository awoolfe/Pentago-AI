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
import java.util.Scanner;
import java.util.function.UnaryOperator;
import java.io.File;



public class MyTools {
    public static PentagoMove getMove(PentagoBoardState s, int PlayerID) {
    	//OPENING MOVES
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
    		TreeNode root = new TreeNode(s, new ArrayList<>(), null);
    		//MonteCarlo makeTree = new MonteCarlo(root, PlayerID);
    		Heuristic makeTree = new Heuristic(root, PlayerID);
    		myMove = (PentagoMove) makeTree.getBestMove(s);
    		
    	}
    	return myMove;
    }
 
  }
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
	
	public Heuristic(TreeNode root, int PlayerID) {
		this.root = root;
		this.PlayerID = PlayerID;
	}
	

	
	public Move getBestMove(PentagoBoardState state) {
		PentagoBoardState stateClone = (PentagoBoardState)state.clone();
		ArrayList<PentagoMove> legal = stateClone.getAllLegalMoves();
		Move moveBest = stateClone.getRandomMove();
		TreeNode temp = root;
		TreeNode bestChild = new TreeNode(null, null, null);
		bestChild.setEval(0.0);
		bestChild.setMove((PentagoMove)moveBest);
		//bestChild.setEvaluation(PlayerID);
		for(PentagoMove m: legal) {
			stateClone = (PentagoBoardState)state.clone();
			stateClone.processMove(m);
			if(stateClone.getWinner() == PlayerID) {
				return m;
			}
			TreeNode node = new TreeNode(stateClone, new ArrayList<>(), null);
			node.setEvaluation(PlayerID);
			node.setMove(m);
			
			temp.addChild(node);
			
		}
		
		for(TreeNode t: temp.getChildren()) {
			//System.out.print(t.getEval());
			if(t.getEval()>bestChild.getEval()) {
				bestChild = t;
			}
			//System.out.print(bestChild.getBoardState().toString());
		}
		//System.out.print(bestChild.getBoardState().toString());
		moveBest = bestChild.getMove();
		
		return moveBest;
	}
}

class TreeNode {
	private double value;
	private double eval;

	private double numWins;
	//private double numGames;
	private double nodeVisit;
	private PentagoBoardState data;
	private List<TreeNode> children = new ArrayList<>();
	private TreeNode parent;
	private PentagoMove move;
	
	
 public TreeNode(PentagoBoardState data, List<TreeNode> children, TreeNode parent) {
//		this.value = value;
//		this.setNodeVisit(nodeVisit);
	 	this.eval = 0.0;
		this.setNumWins(0.0);
		this.setNodeVisit(0.0);
		this.children = children;
		this.data = data;
		this.parent = parent;
	}
	
    public void setEvaluation(int PlayerID) {
    	double val=0.0;
        Piece currColour = PlayerID == 0 ? Piece.WHITE : Piece.BLACK;
        Piece oppColour = Piece.BLACK;
        int oppID = 0;
        if(currColour == Piece.WHITE) {
        	oppColour = Piece.BLACK;
        	oppID = 1; 
        }else {
        	oppColour = Piece.WHITE;
        	oppID = 0;
        }
        PentagoCoord current = new PentagoCoord(0,0);
        int num = 0;
        int numOpp = 0;
        int numThree = 0;
        int numFour = 0;
        for(int i = 0; i<6; i++) {
        	for(int j = 0; j<6; j++) {
        		current = new PentagoCoord(i,j);
        		if(currColour == (this.data.getPieceAt(current))) {
        			num++;
        			
        		}if(oppColour == (this.data.getPieceAt(current)) && num>=4 && ((i==1 && j==1)||(i==1&&j==4)||(i==4&&j==1)||(i==4&&j==4)) ) {
        			numOpp++;
        		}	
        	}
        	if(num == 3) {
    			numThree++;
            }if(num == 4) {
    			numFour++;
            }if(numOpp>0) {
            	numFour--;
            }
        	num = 0;
        	numOpp=0;
        }
        num = 0;
    	numOpp=0;
        for(int j = 0; j<6; j++) {
        	for(int i = 0; i<6; i++) {
        		current = new PentagoCoord(i,j);
        		if(currColour == (this.data.getPieceAt(current))) {
        			num++;
        			
        		}if(oppColour == (this.data.getPieceAt(current))&& num>=4 && ((i==1 && j==1)||(i==1&&j==4)||(i==4&&j==1)||(i==4&&j==4)) ) {
        			numOpp++;
        		}
        	}
        	if(num == 3) {
    			numThree++;
            }if(num == 4) {
    			numFour++;
            }if(numOpp>0) {
            	numFour--;
            }
        	num = 0;
        	numOpp=0;
        
        }//first diagonal
        if(num == 3) {
			numThree++;
		}if(num == 4) {
			numFour++;
		}
        num = 0;
    	numOpp=0;
        int j = 0;
        for(int i = 1; i<6; i++) {
        		
        		current = new PentagoCoord(i,j);
        		if(currColour == (this.data.getPieceAt(current))) {
        			num++;
        			 
        		}if(oppColour == (this.data.getPieceAt(current))) {
        			numOpp++;
        		}
        	j++;
        }//second diagonal
        if(num == 3) {
			numThree++;
        }if(num == 4) {
			numFour++;
        }
    	num = 0;
    	numOpp=0;
        j=0;
        for(int i = 0; i<6; i++) {
        	
        		current = new PentagoCoord(i,j);
        		if(currColour == (this.data.getPieceAt(current))) {
        			num++;
        			
        		}if(oppColour == (this.data.getPieceAt(current))) {
        			numOpp++;
        		}
        	j++;
        }//third diagonal
        if(num == 3) {
			numThree++;
		}if(num == 4) {
			numFour++;
		}
    	num = 0;
    	numOpp=0;
        j=1;
        for(int i = 0; i<5; i++) {
        		current = new PentagoCoord(i,j);
        		if(currColour == (this.data.getPieceAt(current))) {
        			num++;
        			 
        		}if(oppColour == (this.data.getPieceAt(current))) {
        			numOpp++;
        		}
        	j++;
        }//fourth diagonal
        if(num == 3) {
			numThree++;
		}if(num == 4) {
			numFour++;
		}
    	num = 0;
    	numOpp=0;
        j=0;
        for(int i = 4; i>-1; i--) {
        		current = new PentagoCoord(i,j);
        		if(currColour == (this.data.getPieceAt(current))) {
        			num++;
        			
        		}if(oppColour == (this.data.getPieceAt(current))) {
        			numOpp++;
        		}
        	j++;
        }//fifth diagonal
        if(num == 3) {
			numThree++;
		}if(num == 4) {
			numFour++;
		}
    	num = 0;
    	numOpp=0;
        j=0;
        for(int i = 5; i>-1; i--) {
        		current = new PentagoCoord(i,j);
        		if(currColour == (this.data.getPieceAt(current))) {
        			num++;
        			
        		}if(oppColour == (this.data.getPieceAt(current))) {
        			numOpp++;
        		}
        	j++;
        }//sixth diagonal
        if(num == 3) {
			numThree++;
		}if(num == 4) {
			numFour++;
		}
    	num = 0;
    	numOpp=0;
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
		}if(num == 4) {
			numFour++;
		}
		val = 100*numThree+1000*numFour;
    	if(data.getWinner() == PlayerID) val = Integer.MAX_VALUE;
    	if(data.getWinner() == oppID) val = Integer.MIN_VALUE;
    	this.eval = val;
    }
    
    public void setEval(double eval) {
    	this.eval=eval;
    }
    
    public double getEval() {
    	return this.eval;
    }
//	 
//	
//	public boolean isWorse(TreeNode node) {
//		if(node.getEval()>=this.getEval()) {
//			return true;
//		}else return false;
//	}
//	 
	public void setValue() {
		 double UTC = this.numWins/this.nodeVisit; //+ Math.sqrt(Math.log(this.parent.nodeVisit)/this.nodeVisit);
		 this.value = UTC;
 	}
	
	public TreeNode getBestValue() {
		TreeNode best = this.getChildren().get(0);
		for(TreeNode t: this.getChildren()) {
			if(best.getValue()<t.getValue()) {
				best = t;
			}
		}
		return best;
	}
	
	public double getValue() {
		return this.value;
	}
	
	public boolean contains(TreeNode node) {
		for(TreeNode n: this.getChildren()) {
			if(n.getBoardState().toString().equals(node.getBoardState().toString())) {
				return true;
			}
		}
		return false;
	}
	 
	public void addChildren(List<TreeNode> children) {
		 children.forEach(each -> each.setParent(this));
		 this.getChildren().addAll(children);
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
	 
	public void setParent(TreeNode parent) {
		 this.parent = parent;
	}
	 
	public TreeNode getParent() {
		 return parent;
	}

	public double getNumWins() {
		return numWins;
	}

	public void setNumWins(double numWins) {
		this.numWins = numWins;
	}

	public double getNodeVisit() {
		return nodeVisit;
	}

	public void setNodeVisit(double nodeVisit) {
		this.nodeVisit = nodeVisit;
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
	
	}


