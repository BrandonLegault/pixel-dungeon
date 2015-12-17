/*
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.watabou.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class Graph {

	public static <T extends INode> void setPrice( List<T> nodes, int value ) {
		for (T node : nodes) {
			node.price( value );
		}
	}
	
	public static <T extends INode> void buildDistanceMap( Collection<T> nodes, INode focus ) {
		
		for (T node : nodes) {
			node.distance( Integer.MAX_VALUE );
		}
		
		LinkedList<INode> queue = new LinkedList<INode>();
		
		focus.distance( 0 );
		queue.add( focus );
		
		while (!queue.isEmpty()) {
			
			INode node = queue.poll();
			int distance = node.distance();
			int price = node.price();
			
			for (INode edge : node.edges()) {
				if (edge.distance() > distance + price) {
					queue.add( edge );
					edge.distance( distance + price );
				}
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends INode> List<T> buildPath( Collection<T> nodes, T from, T to ) {
		
		List<T> path = new ArrayList<T>();
		
		T room = from;
		while (room != to) {
			
			int min = room.distance();
			T next = null;
			
			Collection<? extends INode> edges = room.edges();
			
			for (INode edge : edges) {
				
				int distance = edge.distance();
				if (distance < min) {
					min = distance;
					next = (T)edge;
				}
			}
			
			if (next == null) {
				return null;
			}
			
			path.add( next );
			room = next;
		}
		
		return path;
	}
	
	public interface INode {
		
		int distance();
		void distance( int value );
		
		int price();
		void price( int value );
		
		Collection<? extends INode> edges();
		
	}
}
