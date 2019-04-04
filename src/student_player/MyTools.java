package student_player;

import boardgame.Move;
import java.util.ArrayList;
import java.util.List;
import pentago_swap.PentagoPlayer;
import pentago_swap.PentagoBoardState;
import pentago_swap.PentagoMove;

public class MyTools {
    public static double getSomething() {
        return Math.random();
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
    	private double yeet;
    	private int numWins;
    	private int numGames;
    	private PentagoBoardState data = null;
    	private List<TreeNode> children = new ArrayList<>();
    	private TreeNode parent = null;
    	
    	public TreeNode(double yeet, int numWins, int numGames, PentagoBoardState data, List<TreeNode> children, TreeNode parent) {
    		this.yeet = yeet;
    		this.numWins = numWins;
    		this.numGames = numGames;
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

		private double getYeet() {
			return yeet;
		}

		private void setYeet(double yeet) {
			this.yeet = yeet;
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
    }
}