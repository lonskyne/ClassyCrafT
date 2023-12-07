package raf.dsw.classycraft.app.state.substates;

import raf.dsw.classycraft.app.gui.swing.view.MainSpace.DiagramView;
import raf.dsw.classycraft.app.state.State;

import java.awt.geom.Point2D;

public class ZoomOutState implements State {
    @Override
    public void classyMousePressed(Point2D position, DiagramView diagramView) {
        diagramView.zoomOut();
    }

    @Override
    public void classyMouseDragged(Point2D startingPosition, DiagramView diagramView) {

    }

    @Override
    public void classyMouseReleased(Point2D endingPosition, DiagramView diagramView) {

    }
}
