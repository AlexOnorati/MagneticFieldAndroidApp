package net.greenriver.ontopgames.magneticfieldtest;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.PointsGraphSeries;
import com.jjoe64.graphview.series.Series;

import java.util.ArrayList;
import java.util.List;

public class display extends AppCompatActivity {
    public Grid grid;
    PointsGraphSeries<DataPoint> series1;
    int progress1 = 0;
    int progress2 = 0;
    PointsGraphSeries<DataPoint> series2;
    LineGraphSeries<DataPoint> seriesLine1;
    LineGraphSeries<DataPoint> seriesLine2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display);

        grid = Grid.instance;
        com.jjoe64.graphview.GraphView graph1 = (com.jjoe64.graphview.GraphView) findViewById(R.id.graph1);

        graph1.getViewport().setXAxisBoundsManual(true);
        graph1.getViewport().setMinX(0);
        graph1.getViewport().setMaxX(30);
        graph1.getViewport().setYAxisBoundsManual(true);
        graph1.getViewport().setMinY(0);
        graph1.getViewport().setMaxY(30);


        com.jjoe64.graphview.GraphView graph2 = (com.jjoe64.graphview.GraphView) findViewById(R.id.graph2);

        graph2.getViewport().setXAxisBoundsManual(true);
        graph2.getViewport().setMinX(0);
        graph2.getViewport().setMaxX(30);
        graph2.getViewport().setYAxisBoundsManual(true);
        graph2.getViewport().setMinY(0);
        graph2.getViewport().setMaxY(30);

        updateGraphs(0, 0);

        SeekBar seekBar1 = (SeekBar)findViewById(R.id.seekBar1);
        final TextView seekBarValue1 = (TextView)findViewById(R.id.textView1);

        seekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                // TODO Auto-generated method stub
                seekBarValue1.setText(String.valueOf(progress));
                progress1 = progress;
                updateGraph(1, progress1);
                updateGraph(2, progress2);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub

            }
        });

        SeekBar seekBar2 = (SeekBar)findViewById(R.id.seekBar2);
        final TextView seekBarValue2 = (TextView)findViewById(R.id.textView2);

        seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                // TODO Auto-generated method stub
                seekBarValue2.setText(String.valueOf(progress));
                progress2 = progress;
                updateGraph(2,progress2);
                updateGraph(1,progress1);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub

            }
        });





}

    public void updateGraphs(int planeX, int planeY){
        updateGraph(1,planeX);
        updateGraph(2,planeY);
    }

    public void updateGraph(int idNumber, int plane){
        float[][] gridSet;
        com.jjoe64.graphview.GraphView graph;
        if(idNumber < 2) {
        graph = (com.jjoe64.graphview.GraphView) findViewById(R.id.graph1);
            if(series1 != null) {
                graph.removeSeries(series1);

            }
            if(seriesLine2 != null){
                graph.removeSeries(seriesLine2);
            }
            series1 = new PointsGraphSeries<>();
            gridSet = grid.getPlaneAtY(plane);
        }else{
            graph = (com.jjoe64.graphview.GraphView) findViewById(R.id.graph2);
            if(series2 != null) {
                graph.removeSeries(series2);

            }
            if(seriesLine1 != null){
                graph.removeSeries(seriesLine1);
            }
            gridSet = grid.getPlaneAtX(plane);
            series2 = new PointsGraphSeries<>();
        }

        List<DataPoint> dataPoints = new ArrayList<>();
        PointsGraphSeries<DataPoint> series = new PointsGraphSeries<DataPoint>();
        for(int i = 0; i < gridSet.length; i++){
            for(int j = 0; j < gridSet[0].length; j++){
                if(gridSet[i][j] < -50) {
                    if(idNumber < 2) {
                        series1.appendData(new DataPoint(1+i, 1+j), true, gridSet.length * gridSet.length);
                    }else{
                        series2.appendData(new DataPoint(1+i, 1+j), true, gridSet.length * gridSet.length);
                    }

                }
            }
        }


        if(idNumber < 2) {
            series1.setSize(5f);
            seriesLine2 = new LineGraphSeries<DataPoint>();
           series1.setShape(PointsGraphSeries.Shape.RECTANGLE);
            seriesLine2.appendData(new DataPoint(progress2 +1, 0), true, gridSet.length);
            seriesLine2.appendData(new DataPoint(progress2 +1, 30), true, gridSet.length);
            graph.addSeries(seriesLine2);
            graph.addSeries(series1);
        }else{
            series2.setSize(5f);
            seriesLine1 = new LineGraphSeries<DataPoint>();
            series2.setShape(PointsGraphSeries.Shape.RECTANGLE);
            seriesLine1.appendData(new DataPoint(0, progress1 +1), true, gridSet.length);
            seriesLine1.appendData(new DataPoint(30, progress1 +1), true, gridSet.length);
            graph.addSeries(seriesLine1);
            graph.addSeries(series2);
        }
    }




}
