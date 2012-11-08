package com.gesture.uiutils;

import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.Vector;

import javax.swing.JPanel;


public class MyJPanel extends JPanel implements MouseMotionListener{
	private static final long serialVersionUID = -6389847943928335404L;
	private final int RADIUS = 5;
	private boolean drawingStarted = false;
	private Vector<Double> rf_curTime;
	private Vector<Point> rf_drawPoint;

	/**
	 * Constructor
	 */

	
	public MyJPanel() {
		addMouseMotionListener(this);
		rf_curTime = new Vector<Double>();
		rf_drawPoint = new Vector<Point>();
	}

	public boolean isDrawingStarted() {
		return drawingStarted;
	}

	public void setDrawingStarted(boolean drawingStarted) {
		this.drawingStarted = drawingStarted;
	}

	/**
	 * Draw
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (int i = 0; i < rf_curTime.size(); i++) {
			((Graphics2D) g).drawOval((int) rf_drawPoint.get(i).getX(), (int) rf_drawPoint.get(i).getY(), RADIUS, RADIUS);
		}

	}


	/**
	 * Add points to list and repaint
	 * 
	 * @param x
	 * @param y
	 */
	private void add(int x, int y, double curTime) {
		rf_curTime.add(curTime);
		rf_drawPoint.add(new Point(x, y));
		repaint();
	}


	@Override
	public void mouseDragged(MouseEvent e) {
		if (drawingStarted) {
			add(e.getX(), e.getY(), System.currentTimeMillis());
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
	}

	public void clearVectorPoints()
	{
		rf_curTime.clear();
		rf_drawPoint.clear();
	}
	
}
