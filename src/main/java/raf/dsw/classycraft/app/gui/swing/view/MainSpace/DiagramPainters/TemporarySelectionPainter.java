package raf.dsw.classycraft.app.gui.swing.view.MainSpace.DiagramPainters;

import raf.dsw.classycraft.app.core.ProjectTreeAbstraction.DiagramAbstraction.InterClass;

import java.awt.*;
import java.awt.geom.Dimension2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;

public class TemporarySelectionPainter extends DiagramElementPainter {
    protected Shape shape;
    private Point2D startPoint;
    private Dimension2D dimension;

    public TemporarySelectionPainter(Point2D startPoint, Point2D endPoint) {
        if (endPoint.getY() < startPoint.getY() && endPoint.getX() < startPoint.getX()) {
            this.startPoint = new Point2D.Double(endPoint.getX(), endPoint.getY());
            endPoint.setLocation(startPoint.getX(), startPoint.getY());
        } else if (endPoint.getY() < startPoint.getY()) {
            this.startPoint = new Point2D.Double(startPoint.getX(), endPoint.getY());
            endPoint.setLocation(endPoint.getX(), startPoint.getY());
        } else if (endPoint.getX() < startPoint.getX()) {
            this.startPoint = new Point2D.Double(endPoint.getX(), startPoint.getY());
            endPoint.setLocation(startPoint.getX(), endPoint.getY());
        } else {
            this.startPoint = startPoint;
        }
        shape = new GeneralPath();

        Dimension2D interClassSize = new Dimension((int) Math.abs(this.startPoint.getX() - endPoint.getX()),
                (int) Math.abs(this.startPoint.getY() - endPoint.getY()));
        dimension = interClassSize;
        ((GeneralPath)shape).moveTo(this.startPoint.getX(), this.startPoint.getY());

        ((GeneralPath)shape).lineTo(this.startPoint.getX() + interClassSize.getWidth(), this.startPoint.getY());

        ((GeneralPath)shape).lineTo(this.startPoint.getX() + interClassSize.getWidth(), this.startPoint.getY() + interClassSize.getHeight());

        ((GeneralPath)shape).lineTo(this.startPoint.getX(), this.startPoint.getY() + interClassSize.getHeight());

        ((GeneralPath)shape).lineTo(this.startPoint.getX(), this.startPoint.getY());

        ((GeneralPath)shape).closePath();
    }

    @Override
    public void paint(Graphics2D g) {
        float[] dashPattern = {5, 5};
        g.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND,
                1.0f, dashPattern, 0));

        g.draw(shape);
    }

    @Override
    public boolean elementAt(Point2D point) {
        if (point.getX() >= this.startPoint.getX()
                && point.getY() >= this.startPoint.getY()
                && point.getX() <= this.startPoint.getX() + this.dimension.getWidth()
                && point.getY() <= this.startPoint.getY() + this.dimension.getHeight())
            return true;
        return false;
    }
}
