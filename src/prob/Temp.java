package prob;

import java.util.Arrays;

public class Temp {
	
	static class Edge implements Comparable<Edge> {
		
		int from, to, weight;
		
		public Edge(int from, int to, int weight) {
			super();
			this.from = from;
			this.to = to;
			this.weight = weight;
		}
		
		@Override
		public int compareTo(Edge o) {
			return this.weight - o.weight;
		}
	}
	
	static int[] parents; // 부모 리스트
	static int V, E; // 정점의 갯수와 간선의 수
	static Edge[] edgeList; // 간선 리스트
	
	static void make() { // 크기가 1인 서로소 집합 생성
		
		parents = new int[V];
		// 모든 노드가 자신을 부모로 하는 (대표자) 집합으로 만든다
		for (int i = 0; i < V; i++) 
			parents[i] = i;
	}
	
	static int find(int a) { // 1의 대표자 찾기
		if (parents[a] == a) return a;
		
		return parents[a] = find(parents[a]); // 우리의 대표자를 나의 부모로
	}
	
	static boolean union(int a, int b) { // 리턴값 : true => union 성공
		int aRoot = find(a);
		int bRoot = find(b);
		
		if (aRoot == bRoot) return false;
		
		parents[bRoot] = aRoot;
		return true;
	}
	
	public static void main(String[] args) {
		make();
		Arrays.sort(edgeList);
		
		int result = 0;
		int cnt = 0;
		for (Edge edge: edgeList) {
			if (union(edge.from, edge.to)) {
				result += edge.weight;
				if (++cnt == V-1) break;
			}
		}
		System.out.println();
	}
	
	
	
}