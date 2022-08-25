package prob.solved;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.List;
import java.util.StringTokenizer;

public class Main_16236_아기상어3 {
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		N = Integer.parseInt(br.readLine());
		map = new int[N][N];
		time = new int[N][N];
		sharkSize = 2; // 최초 상어 크기

		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				if (map[i][j] == 9) {
					sharkPosR = i;
					sharkPosC = j;
					dq.offer(new int[] {sharkPosR, sharkPosC}); // 초기 상어 위치 dq add
					time[sharkPosR][sharkPosC] = 1;
				}
			}
//			System.out.println(Arrays.toString(map[i]));
//			System.out.println(Arrays.toString(time[i]));
		}
		
//		for (int[] a: dq)
//			System.out.println(Arrays.toString(a));
		
		while (!dq.isEmpty()) {
			feedCnt = 0;
			dqTemp = new ArrayDeque<>();
			fishPos = new ArrayList<>();
			bfs();
			
			if(feedCnt!=0) {
				find();
				continue;
			}
			
			for (int[] nextP: dqTemp) {
				dq.offer(nextP);
			}
			
		}
		
		System.out.println(totalTime);
		
	}
	
	private static int N, sharkSize, sharkFeed, totalTime, feedCnt;
	private static int sharkPosR, sharkPosC;
	private static int[] dr = {-1, 0, 0, 1}; // 북 서 동 남
	private static int[] dc = {0, -1, 1, 0};
	private static int[][] map, time;
	private static Deque<int[]> dq = new ArrayDeque<>();
	private static Deque<int[]> dqTemp;
	private static List<int[]> fishPos;

	private static void bfs() {
		
		while (!dq.isEmpty()) {
			int[] cur = dq.poll();
			for (int i = 0; i < 4; i++) {
				int nextR = cur[0] + dr[i];
				int nextC = cur[1] + dc[i];
				if (nextR < cur[0] && nextC != cur[1]) continue;
				if (nextR < 0 || nextR >= N || nextC < 0 || nextC >= N) continue; // map index 범위가 넘어가면 continue
				if (time[nextR][nextC] == 0 && map[nextR][nextC] <= sharkSize) { // 방문조건 && 크기가 작거나 같으면 이동 가능
					 if (map[nextR][nextC] < sharkSize && map[nextR][nextC] > 0) { // 상어보다 크기가 작은 물고기를 만나면
						 feedCnt++;
						 fishPos.add(new int[] {nextR, nextC});
					 }
					 time[nextR][nextC] = time[cur[0]][cur[1]] + 1; // 시간 넣기
					 dqTemp.offer(new int[] {nextR, nextC});
				}
			}
		}
		
	}
	
	private static void find() {
		
		Collections.sort(fishPos, new Comparator<int[]>() {
			public int compare(int[] o1, int[] o2) {
				if (o1[0] == o2[0]) return o1[1] - o2[1];
				return o1[0] - o2[0];
			}
		});
		
		int posR = fishPos.get(0)[0];
		int posC = fishPos.get(0)[1];
		
		 ++sharkFeed;
		 if(sharkFeed == sharkSize) {
			 ++sharkSize; // 상어의 크기만큼 먹이를 먹으면 상어의 크기가 1 증가
			 sharkFeed = 0; // 다시 먹은 양을 0으로 초기화
		 }
		 totalTime += time[posR][posC] - 1;
		 map[sharkPosR][sharkPosC] = 0; // 기존 상어 위치 0으로 변경
		 sharkPosR = posR;
		 sharkPosC = posC;
		 map[sharkPosR][sharkPosC] = 9; // 새로운 상어 위치 9로 변경
		 dq.clear();
		 dq.offer(new int[] {sharkPosR, sharkPosC}); // 새로운 상어의 위치를 que를 초기화 하고 넣어주기
		 
		 // TODO : 출력
//		 System.out.println("totalTime : " + totalTime + ", time : " + (time[posR][posC] - 1) + ", sharkSize : " + sharkSize);
		 time = new int[N][N];
		 time[sharkPosR][sharkPosC] = 1;
		 
		 // TODO : 출력
//		 for (int m = 0; m < N; m++) {
//			 for (int n = 0; n < N; n++)
//				 System.out.print(map[m][n] + " ");
//			 System.out.println();
//		 }
//		 System.out.println("---------------------------");
	}
	
}
