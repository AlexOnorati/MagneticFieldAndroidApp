package net.greenriver.ontopgames.magneticfieldtest;

/**
 * Created by alexo on 4/19/2016.
 */
public class Grid {
    public static Grid instance;
    float[][][] grid;
    int xSize;
    int ySize;
    int zSize;

    public Grid(int x, int y, int z){
        grid = new float[x][y][z];
        this.xSize = x;
        this.ySize = y;
        this.zSize = z;
    }

    public void setCoridinate(int x, int y, int z, float value){
        grid[x][y][z] = value;
    }

    public float[][] getPlaneAtX(int x){
        float[][] tempArray = new float[zSize][ySize];
        for(int z = 0; z < zSize; z++){
            for(int y = 0; y < ySize; y++){
                tempArray[z][y] = grid[x][y][z];
            }
        }
        return tempArray;
    }

    public float[][] getPlaneAtY(int y){
        float[][] tempArray = new float[xSize][zSize];
        for(int x = 0; x < xSize; x++){
            for(int z = 0; z < zSize; z++){
                tempArray[x][z] = grid[x][y][z];
            }
        }
        return tempArray;
    }

    public float[][] getPlaneAtZ(int z){
        float[][] tempArray = new float[xSize][zSize];
        for(int x = 0; x < xSize; x++){
            for(int y = 0; y < ySize; y++){
                tempArray[x][y] = grid[x][y][z];
            }
        }
        return tempArray;
    }
}
