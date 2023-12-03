package raf.dsw.classycraft.app.state.substates;

import raf.dsw.classycraft.app.core.ProjectTreeAbstraction.DiagramAbstraction.Connection;
import raf.dsw.classycraft.app.core.ProjectTreeAbstraction.DiagramAbstraction.InterClass;
import raf.dsw.classycraft.app.core.ProjectTreeAbstraction.DiagramAbstraction.TemporaryConnection;
import raf.dsw.classycraft.app.gui.swing.view.MainSpace.DiagramPainters.ConnectionPainter;
import raf.dsw.classycraft.app.gui.swing.view.MainSpace.DiagramPainters.DiagramElementPainter;
import raf.dsw.classycraft.app.gui.swing.view.MainSpace.DiagramPainters.InterClassPainter;
import raf.dsw.classycraft.app.gui.swing.view.MainSpace.DiagramPainters.TemporaryConnectionPainter;
import raf.dsw.classycraft.app.gui.swing.view.MainSpace.DiagramView;
import raf.dsw.classycraft.app.state.State;

import java.awt.*;
import java.awt.geom.Point2D;

public class AddConnectionState implements State {
    private InterClass from;
    private InterClass to;

    @Override
    public void classyMousePressed(Point2D position, DiagramView diagramView) {
        System.out.println("starting connection");
        for (DiagramElementPainter dep : diagramView.getDiagramElementPainters()) {
            if (dep.elementAt(position)) {
                from = ((InterClassPainter) dep).getInterClass();
                return;
            }
        }
    }

    @Override
    public void classyMouseDragged(Point2D startingPosition, DiagramView diagramView) {
        int index = diagramView.getDiagramElementPainters().size() - 1;
        if (diagramView.getDiagramElementPainters().get(index) instanceof TemporaryConnectionPainter)
            diagramView.getDiagramElementPainters().remove(index);
        TemporaryConnection temporaryConnection = new TemporaryConnection("Placeholder", from, startingPosition) {
            @Override
            public InterClass getFrom() {
                return super.getFrom();
            }

            @Override
            public Point2D getTo() {
                return super.getTo();
            }
        };
        TemporaryConnectionPainter connectionPainter = new TemporaryConnectionPainter(temporaryConnection);
        diagramView.addDiagramElementPainter(connectionPainter);
    }

    @Override
    public void classyMouseReleased(Point2D endingPosition, DiagramView diagramView) {
        System.out.println("finished");
        for (DiagramElementPainter dep : diagramView.getDiagramElementPainters()) {
            if (dep.elementAt(endingPosition)) {
                if (from != ((InterClassPainter) dep).getInterClass())
                    to = ((InterClassPainter) dep).getInterClass();
                break;
            }
        }
        Connection connection = new Connection("Placeholder", from, to) {
            @Override
            public InterClass getFrom() {
                return super.getFrom();
            }

            @Override
            public InterClass getTo() {
                return super.getTo();
            }
        };
        ConnectionPainter connectionPainter = new ConnectionPainter(connection);
        diagramView.addDiagramElementPainter(connectionPainter);

        from = null;
        to = null;
    }

}
