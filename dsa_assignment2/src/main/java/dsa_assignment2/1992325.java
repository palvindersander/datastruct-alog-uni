package dsa_assignment2;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.log4j.Logger;

/**
 * A Drone class to simulate the decisions and information collected by a drone
 * on exploring an underground maze.
 * 
 */
public class Drone implements DroneInterface
{
	private static final Logger logger     = Logger.getLogger(Drone.class);
	
	public String getStudentID()
	{
		//change this return value to return your student id number
		return "1992325";
	}

	public String getStudentName()
	{
		//change this return value to return your name
		return "Palvinder Sander";
	}

	/**
	 * The Maze that the Drone is in
	 */
	private Maze                maze;

	/**
	 * The stack containing the portals to backtrack through when all other
	 * doorways of the current chamber have been explored (see assignment
	 * handout). Note that in Java, the standard collection class for both
	 * Stacks and Queues are Deques
	 */
	private Deque<Portal>       visitStack = new ArrayDeque<>();

	/**
	 * The set of portals that have been explored so far.
	 */
	private Set<Portal>         visited    = new HashSet<>();

	/**
	 * The Queue that contains the sequence of portals that the Drone has
	 * followed from the start
	 */
	private Deque<Portal>       visitQueue = new ArrayDeque<>();

	/**
	 * This constructor should never be used. It is private to make it
	 * uncallable by any other class and has the assert(false) to ensure that if
	 * it is ever called it will throw an exception.
	 */
	@SuppressWarnings("unused")
	private Drone()
	{
		assert (false);
	}

	/**
	 * Create a new Drone object and place it in chamber 0 of the given Maze
	 * 
	 * @param maze
	 *            the maze to put the Drone in.
	 */
	public Drone(Maze maze)
	{
		this.maze = maze;
	}

	/* 
	 * @see dsa_assignment2.DroneInterface#searchStep()
	 */
	@Override
	public Portal searchStep()
	{
		/* WRITE YOUR CODE HERE */
		if (visited.size() != 0 && visitStack.size() == 0 && maze.getCurrentChamber() == 0) {
			return null;
		}
		int doorsnum = maze.getNumDoors();
		int chambernum = maze.getCurrentChamber();
		int[] doors = new int[doorsnum];
		for (Portal p : visited) {
			if (p.getChamber() == chambernum) {
				doors[p.getDoor()] = 1;
			}
		}
		for (int i = 0; i < doors.length; i++) {
			if (doors[i] == 0) {
				Portal pOut = new Portal(chambernum, i);
				Portal pIn = maze.traverse(i);
				visitStack.push(pIn);
				visited.add(pOut);
				visited.add(pIn);
				visitQueue.add(pOut);
				visitQueue.add(pIn);
				return pIn;
			}
		}
		Portal popped = visitStack.pop();
		Portal pOut = popped;
		Portal pIn = maze.traverse(popped.getDoor());
		visited.add(pOut);
		visited.add(pIn);
		visitQueue.add(pOut);
		visitQueue.add(pIn);
		return pIn;
	}

	/* 
	 * @see dsa_assignment2.DroneInterface#getVisitOrder()
	 */
	@Override
	public Portal[] getVisitOrder()
	{
		/* WRITE YOUR CODE HERE */
		Object[] vQueue = visitQueue.toArray();
		Portal[] order = new Portal[visitQueue.size()];
		for (int i = 0; i < order.length; i++) {
			Portal p = (Portal) vQueue[i];
			order[i] = p;
		}
		return order;
	}

	/*
	 * @see dsa_assignment2.DroneInterface#findPathBack()
	 */
	@Override
	public Portal[] findPathBack()
	{
		/* WRITE YOUR CODE HERE */
		Object[] vQueue = visitQueue.toArray();
		Portal[] order = new Portal[visitQueue.size()];
		for (int i = 0; i < order.length; i++) {
			Portal p = (Portal) vQueue[i];
			order[i] = p;
		}
		ArrayList<Portal> pathBack = new ArrayList<>();
		for (int i = order.length-1; i >= 1; i--) {
			if (i%2 == 0) {
				continue;
			}
			Portal pThis = order[i];
			pathBack.add(pThis);	
		}
		if (pathBack.get(0).getChamber() == 0) {
			return new Portal[0];
		}
		for (int i = 0; i < pathBack.size()-1; i++) {
			if (pathBack.get(i).getChamber() == 0) {
				pathBack = new ArrayList<Portal>(pathBack.subList(0, i));
			}
		}
		boolean swaps = true;
		ArrayList<Portal> path = new ArrayList<>(pathBack);
		while (swaps) {
			swaps = false;
			int startGap = 0;
			int endGap = 0;
			for (int i = 0; i < path.size()-1; i++) {
				 for (int j = i+1; j < path.size(); j++) {
					 if (path.get(j).getChamber() == path.get(i).getChamber()) {
							startGap = i;
							endGap = j;
							swaps = true;
							break;
					 }
				 }
				 if (swaps) {
					 break;
				 }
			}
			ArrayList<Portal> before = new ArrayList<Portal>(path.subList(0,startGap));
			ArrayList<Portal> after = new ArrayList<Portal>(path.subList(endGap,path.size()));
			before.addAll(after);
			path = new ArrayList<>(before);
		}
		/*
		int startGap = 0;
		int endGap = 0;
		for (int i = 0; i < pathBack.size()-1; i++) {
			 for (int j = i+1; j < pathBack.size(); j++) {
				 if (pathBack.get(j).getChamber() == pathBack.get(i).getChamber()) {
					 if (endGap < j) {
						 startGap = i;
						 endGap = j;
					 }
				 }
			 }
		}
		ArrayList<Portal> before = new ArrayList<Portal>(pathBack.subList(0,startGap));
		ArrayList<Portal> after = new ArrayList<Portal>(pathBack.subList(endGap,pathBack.size()));
		before.addAll(after);
		//return pathBack.toArray(new Portal[pathBack.size()]);
		return before.toArray(new Portal[before.size()]);*/
		return path.toArray(new Portal[path.size()]);
	}

}
