package student_player;

import boardgame.Move;
import java.util.ArrayList;
import java.util.List;
import pentago_swap.PentagoPlayer;
import pentago_swap.PentagoBoardState;
import pentago_swap.PentagoMove;

public class MyTools {
    public static PentagoMove getMove(PentagoBoardState s, int playeriD) {
        PentagoMove bestMove = (PentagoMove) s.getRandomMove();
        ArrayList<PentagoMove> moves = s.getAllLegalMoves();
        for(int i = 0; i<6; i++) {
        	for(int j = 0; j<6; j++) {
        		if((s.getPieceAt(i, j)==PentagoBoardState.Piece.BLACK || s.getPieceAt(i, j)==PentagoBoardState.Piece.WHITE) && i<4 && j<4) {
        			bestMove = new PentagoMove(i+1, j+1, PentagoBoardState.Quadrant.BL, PentagoBoardState.Quadrant.TL, playeriD);
        			if(s.isLegal(bestMove)==false) {
        				bestMove = new PentagoMove(i-1, j-1, PentagoBoardState.Quadrant.BL, PentagoBoardState.Quadrant.TL, playeriD);
        			}if(s.isLegal(bestMove)==false) {
        				bestMove = (PentagoMove) s.getRandomMove();
        			}
        		}
        	}
        }
    	return bestMove;
    }
    class MonteCarloTree {
    	private TreeNode root = null;
    	public MonteCarloTree(TreeNode root){
    		this.root = root;
    	}
    	public void descent() {
    		
    	}
    	public void rollout() {
    		
    	}
    	public void update() {
    		
    	}
    	public void growth() {
    		
    	}
    }
    class TreeNode {
    	private double value;
    	private int numWins;
    	private int numGames;
    	private int nodeVisit;
    	private PentagoBoardState data = null;
    	private List<TreeNode> children = new ArrayList<>();
    	private TreeNode parent = null;
    	
    	public TreeNode(double value, int numWins, int numGames, int nodeVisit, PentagoBoardState data, List<TreeNode> children, TreeNode parent) {
    		this.value = value;
    		this.numWins = numWins;
    		this.numGames = numGames;
    		this.setNodeVisit(nodeVisit);
    		this.children = children;
    		this.data = data;
    		this.parent = parent;
    	 }
    	 
    	 public TreeNode addChild(TreeNode child) {
    		 child.setParent(this);
    		 this.children.add(child);
    		 return child;
    	 }
    	 
    	 public void addChildren(List<TreeNode> children) {
    		 children.forEach(each -> each.setParent(this));
    		 this.children.addAll(children);
    	 }
    	 
    	 public List<TreeNode> getChildren() {
    		 return children;
    	 }
    	 
    	 public PentagoBoardState getBoard() {
    		 return data;
    	 }
    	 
    	 public void setBoard(PentagoBoardState data) {
    		 this.data = data;
    	 }
    	 
    	 private void setParent(TreeNode parent) {
    		 this.parent = parent;
    	 }
    	 
    	 public TreeNode getParent() {
    		 return parent;
    	 }

		private double getValue() {
			return value;
		}

		private void setValue(double value) {
			this.value = value;
		}

		private int getNumWins() {
			return numWins;
		}

		private void setNumWins(int numWins) {
			this.numWins = numWins;
		}

		private int getNumGames() {
			return numGames;
		}

		private void setNumGames(int numGames) {
			this.numGames = numGames;
		}

		private PentagoBoardState getData() {
			return data;
		}

		private void setData(PentagoBoardState data) {
			this.data = data;
		}

		private int getNodeVisit() {
			return nodeVisit;
		}

		private void setNodeVisit(int nodeVisit) {
			this.nodeVisit = nodeVisit;
		}
    }
}