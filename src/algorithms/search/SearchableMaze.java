package algorithms.search;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * Object adapter for maze problems. Adapts the maze problem so generic searching algorithms can be used to solve it
 */
public class SearchableMaze implements ISearchable{
    private Maze maze;

    /**
     * Constructor
     * @param m Given maze
     */
    public SearchableMaze(Maze m) {
        maze = m;
    }

    /**
     * Getter for start/goal position
     * @return MazeState
     */
    @Override
    public AState getStartState() { return new MazeState(maze.getStartPosition());
    }

    @Override
    public AState getGoalState() {
        return new MazeState(maze.getGoalPosition());
    }


    /**
     * Finds all the Possible moves from Astate s in a clockwise order (starts at 12:00)
     * @param s Current state to check
     * @return ArrayList of all the "passage" AState that near s
     */
    @Override
    public ArrayList<AState> getAllSuccessors(AState s) {
        //Return list
        ArrayList<AState> states_list = new ArrayList<>();

        //Given state properties
        MazeState curr_state = (MazeState) s;
        Position curr_position = curr_state.getPos();
        Position temp;
        int curr_row = curr_position.getRowIndex();
        int curr_col = curr_position.getColumnIndex();

        //Limit of the matrix
        int row = maze.getMaze_matrix().length;
        int col = maze.getMaze_matrix()[0].length;

        //Boolean helpers
        boolean up_right=false;
        boolean right_down=false;
        boolean down_left=false;
        boolean left_up= false;
        boolean up = false;

        int[][] MyMaze = maze.getMaze_matrix();

        //Checks up
        if (curr_row-1>=0 && MyMaze[curr_row-1][curr_col] == 0){
            //MazeState new_s = new MazeState( 10,m,new Position(curr_row-1,curr_col));
            temp = new Position(curr_row-1,curr_col);
            states_list.add(new MazeState( 10 + curr_state.getCost(),curr_state,temp));
            up = true;

            //Checks up-right
            if (curr_col+1<col && MyMaze[curr_row-1][curr_col+1] == 0){
                temp = new Position(curr_row-1, curr_col+1 );
                states_list.add(new MazeState(15 + curr_state.getCost(), curr_state, temp));
                up_right = true;
            }
        }

        //Checks right
        if (curr_col + 1 < col && MyMaze[curr_row][curr_col + 1] == 0) {
            
            //Checks right-up
                if (!up_right && curr_row-1>=0 && MyMaze[curr_row-1][curr_col+1] == 0){
                    //Adds right-up
                    temp = new Position(curr_row-1, curr_col+1 );
                    states_list.add(new MazeState( 15 + curr_state.getCost() ,curr_state,temp));

            }
            //Adds right
            temp =  new Position(curr_row, curr_col+1);
            states_list.add(new MazeState(10 + curr_state.getCost() , curr_state,temp));
            
            //Checks right-down
            if (curr_row + 1 < row && MyMaze[curr_row+1][curr_col+1]==0){
                right_down = true;
                //Adds right-down
                temp = new Position(curr_row+1, curr_col+1);
                states_list.add(new MazeState(15+ curr_state.getCost() , curr_state, temp));
            }
        }


        //Down
        if (curr_row + 1 < row && MyMaze[curr_row + 1][curr_col] == 0){

            //Checks down-right
            if (!right_down && curr_col +1 < col && MyMaze[curr_row+1][curr_col+1] == 0){
                //Adds right-down
                temp = new Position(curr_row+1, curr_col+1);
                states_list.add(new MazeState(15 + curr_state.getCost(), curr_state, temp));
            }

            //Adds down
            temp = new Position(curr_row+1, curr_col);
            states_list.add(new MazeState(10 + curr_state.getCost(), curr_state, temp));


            //Checks down-left
            if (curr_col-1 >= 0 && MyMaze[curr_row+1][curr_col-1]==0){
                down_left = true;
                //Adds down-left
                temp = new Position(curr_row+1, curr_col-1);
                states_list.add(new MazeState(15 + curr_state.getCost(), curr_state, temp));
            }
        }


        //Left
        if (curr_col - 1 >= 0 && MyMaze[curr_row][curr_col-1] == 0){

            //Checks left-down
            if (!down_left && curr_row + 1 < row && MyMaze[curr_row + 1][curr_col - 1] == 0){
                //Adds down-left
                temp = new Position(curr_row+1, curr_col-1);
                states_list.add(new MazeState(15 + curr_state.getCost(), curr_state, temp));
            }

            //Adds left
            temp = new Position(curr_row, curr_col - 1);
            states_list.add(new MazeState(10 + curr_state.getCost(), curr_state, temp));

            //Checks left-up
            if (curr_row - 1 >= 0 && MyMaze[curr_row - 1][curr_col - 1] == 0){
                left_up = true;
                //Adds left-up
                temp = new Position(curr_row-1, curr_col-1);
                states_list.add(new MazeState(15 + curr_state.getCost(), curr_state, temp));
            }
        }


        //Checks up-left
        if (!left_up && up && curr_col-1 >= 0 && MyMaze[curr_row-1][curr_col-1] == 0){
            temp = new Position(curr_row-1, curr_col-1);
            states_list.add(new MazeState(15 + curr_state.getCost(), curr_state, temp));
        }

        return states_list;
    }


    @Override
    public List<AState> getAllPossibleStates(AState state) {
        if(!(state instanceof MazeState))
            return null;
        MazeState mazeState = (MazeState)state;
        Position tempPos;
        Position position = mazeState.getPos();
        List<AState> possiblePositions = new ArrayList<>();
        int row = maze.getMaze_matrix().length;
        int col = maze.getMaze_matrix()[0].length;
        int[][] m = maze.getMaze_matrix();
        int pos_row = position.getRowIndex();
        int pos_col = position.getColumnIndex();
        //Check down
        if (position.getRowIndex() < (row - 1) && pos_col >0 && m[pos_row + 1][pos_col] == 0) {
            tempPos = new Position(pos_row + 1, pos_col);
            possiblePositions.add(new MazeState(10+ mazeState.getCost(),mazeState,tempPos));
        }
        //Check right
        if (pos_col < col - 1 && m[pos_row][pos_col + 1] == 0) {
            tempPos = new Position(pos_row, pos_col + 1);
            possiblePositions.add(new MazeState(10+ mazeState.getCost(),mazeState,tempPos));
        }
        //Check up
        if (pos_row > 0 && pos_col>0 && m[pos_row - 1][pos_col] == 0) {
            tempPos = new Position(pos_row - 1, pos_col);
            possiblePositions.add(new MazeState(10+ mazeState.getCost(),mazeState,tempPos));
        }
        //Check left
        if (pos_col > 0 && m[pos_row][pos_col - 1] == 0) {
            tempPos = new Position(pos_row, pos_col - 1);
            possiblePositions.add(new MazeState(10+ mazeState.getCost(),mazeState,tempPos));
        }

        //Check diagonals

        //Check down - left
        if (pos_row < row - 1 && pos_row>0 && pos_col > 0 && m[pos_row + 1][pos_col - 1] == 0 && /*check if have a path -> */ m[pos_row][pos_col - 1] == 0 || pos_col>0 && m[pos_row + 1][pos_col] == 0) {
            tempPos = new Position(pos_row + 1, pos_col - 1);
            possiblePositions.add(new MazeState(15 + mazeState.getCost(),mazeState,tempPos));
        }
        //Check down - right
        if (pos_col < col - 1 && pos_row < row - 1 && m[pos_row + 1][pos_col + 1] == 0 && /*check if have a path -> */ (m[pos_row + 1][pos_col] == 0 || m[pos_row][pos_col + 1]== 0)) {
            tempPos = new Position(pos_row + 1, pos_col + 1);
            possiblePositions.add(new MazeState(15 + mazeState.getCost(),mazeState,tempPos));
        }
        //Check up - left
        if (pos_row > 0 && pos_col > 0 && m[pos_row - 1][pos_col - 1] == 0 && /*check if have a path -> */ (m[pos_row][pos_col - 1] == 0 || m[pos_row - 1][pos_col] == 0)) {
            tempPos = new Position(pos_row - 1, pos_col - 1);
            possiblePositions.add(new MazeState(15 + mazeState.getCost(),mazeState,tempPos));
        }
        //Check up - right
        if (pos_row > 0 && pos_col < col - 1 && m[pos_row - 1][pos_col + 1] == 0 && /*check if have a path -> */ (m[pos_row][pos_col + 1] == 0 || m[pos_row - 1][pos_col] == 0)) {
            tempPos = new Position(pos_row - 1, pos_col + 1);
            possiblePositions.add(new MazeState(15 + mazeState.getCost(),mazeState,tempPos));
        }

        return possiblePositions;
    }
}






