package raf.dsw.classycraft.app.state;

import raf.dsw.classycraft.app.gui.swing.view.MainSpace.DiagramView;

import java.awt.geom.Point2D;

public interface State {
    void classyMousePressed(Point2D position, DiagramView diagramView);
    void classyMouseDragged(Point2D startingPosition, DiagramView diagramView);
    void classyMouseReleased(Point2D endingPosition, DiagramView diagramView);
}
