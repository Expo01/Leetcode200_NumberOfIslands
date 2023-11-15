import java.util.LinkedList;
import java.util.Queue;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
    }
}



// ideas. BFS? cam easily see which spaces are 1's but how to record and compare to others?

// can recurisvelyu DFS calling L,R,U,D cells, but not so simple as stating true for L,R,U,D neighbors
// being water. imagine a snaking shape which would have many occurences of all 4 directions having
// adjacent water cells. could say that as long as L R U D are true then a island exists, but how to
// tell when one island ends? will also keep track of visited cells. Encountering a water caell with all 4 sides water may work? but could
// have a 'lake' in an island. not sure if this is allowed. could also just have a large inlet where
// open water present, so this actuallyu won't work. I think the solution is a DFS, but how to determine
// when one island ends?


// DFS WITH RECURSION
class Solution {
    void dfs(char[][] grid, int r, int c) {
        int nr = grid.length;
        int nc = grid[0].length;

        if (r < 0 || c < 0 || r >= nr || c >= nc || grid[r][c] == '0') {
            return;
        }

        grid[r][c] = '0'; // turn current cell from land to water cell then DFS neighbors. no running arraylist of visited
        dfs(grid, r - 1, c); // nodes needed since excluded via changing value 1 -> 0
        dfs(grid, r + 1, c);
        dfs(grid, r, c - 1);
        dfs(grid, r, c + 1);
    }

    public int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }

        int nr = grid.length; // num row = nr. these variables are removable and could just include directly in for loops
        // but involves re-computation with each loop, so it actually slows it down
        int nc = grid[0].length; // num colmn = nc
        int num_islands = 0;
        for (int r = 0; r < nr; ++r) { // search grid  left to right, top to bottom . r = row, c = column
            for (int c = 0; c < nc; ++c) {
                if (grid[r][c] == '1') { // begin DFS when a '1' is found
                    ++num_islands; // increment island number. this works because DFS method flips all connected 1's to
                    // 0's so this won't just be adding a 1 for every cell but will add 1 every time DFS called and
                    // subsequently terminated
                    dfs(grid, r, c); // call the DFS using coordiantes of '1' value cell
                }
            }
        }

        return num_islands;
    }
}


// BFS WITH QUEUE, significantly less efficient
class Solution {
    private int nr;
    private int nc;
    public int[][]dirs = {{0,1}, {0,-1}, {-1,0}, {1,0}};

    void bfs(char[][] grid, int r, int c) { // the difference happens in the helper method in the order we search
        Queue<int[]> queue = new LinkedList<>(); // here we have a que so all neighbors of passed node will be
        queue.offer(new int[]{r,c}); // handled before any further distance nodes, until DFS which will go to furthest
        // nodes before actually completing all for directions for the start node

        while(!queue.isEmpty()){ // que the 'root' cell
            int[] cur = queue.poll();

            for(int[] dir: dirs){ // derive adjacent cell in all 4 directions
                r = cur[0] + dir[0];
                c = cur[1] + dir[1];

                if (r >= 0 && c >= 0 && r < nr && c < nc && grid[r][c] == '1') { // change adjacent cell value to 0
                    grid[r][c] = '0'; // if it was a 1 so it won't be revisited except the start node which was never changed it seems...
                    queue.offer(new int[] {r,c}); // add the adjacent cell to queue to then handle its neighbors
                }
            }
        }

    }

    public int numIslands(char[][] grid) { // all pretty much the same as the DFS here.
        if (grid == null || grid.length == 0) {
            return 0;
        }

        nr = grid.length;
        nc = grid[0].length;
        int num_islands = 0;
        for (int r = 0; r < nr; ++r) {
            for (int c = 0; c < nc; ++c) {
                if (grid[r][c] == '1') {
                    ++num_islands;
                    bfs(grid, r, c);
                }
            }
        }

        return num_islands;
    }
}