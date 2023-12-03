package raf.dsw.classycraft.app.gui.swing.view.MainSpace;

import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.core.Observer.ISubscriber;
import raf.dsw.classycraft.app.core.Observer.notifications.SubscriberNotification;
import raf.dsw.classycraft.app.core.Observer.notifications.Type;
import raf.dsw.classycraft.app.core.ProjectTreeAbstraction.ClassyNode;
import raf.dsw.classycraft.app.core.ProjectTreeAbstraction.DiagramAbstraction.Access;
import raf.dsw.classycraft.app.core.ProjectTreeImplementation.Diagram;
import raf.dsw.classycraft.app.core.ProjectTreeImplementation.DiagramImplementation.InterClass.Class;
import raf.dsw.classycraft.app.core.ProjectTreeImplementation.DiagramImplementation.InterClass.ClassContent;
import raf.dsw.classycraft.app.gui.swing.view.MainSpace.DiagramPainters.DiagramElementPainter;
import raf.dsw.classycraft.app.gui.swing.view.MainSpace.DiagramPainters.InterClassPainter;
import raf.dsw.classycraft.app.gui.swing.view.MainSpace.DiagramPainters.TemporaryConnectionPainter;
import raf.dsw.classycraft.app.gui.swing.view.MainSpace.DiagramPainters.TemporarySelectionPainter;
import raf.dsw.classycraft.app.gui.swing.view.MainSpace.listeners.ClassyMouseListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import static com.sun.java.accessibility.util.AWTEventMonitor.addMouseMotionListener;

public class DiagramView extends JPanel implements ISubscriber {
    private final TabbedPane tabbedPane;
    private final Diagram diagram;
    private String name;
    private ArrayList<DiagramElementPainter> diagramElementPainters = new ArrayList<DiagramElementPainter>();

    public DiagramView(Diagram diagram, TabbedPane tabbedPane) {
        this.diagram = diagram;
        this.name = diagram.getName();
        this.tabbedPane = tabbedPane;
        diagram.addSubscriber(this);

        addMouseListener(new ClassyMouseListener(this));

        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                tabbedPane.getClassyPackage().getPackageView().getCurrentState()
                        .classyMouseDragged(e.getPoint(), DiagramView.this);
                repaint();
            }

            @Override
            public void mouseMoved(MouseEvent e) {

            }
        });
        //TEST/////////////////////////


        //addMouseListener(new MouseAdapter() {
        //    @Override
        //    public void mousePressed(MouseEvent e) {
        //        //diagramElementPainters.add(new InterClassPainter(new Class("viktor", Access.DEFAULT,
        //        //        new Point2D.Double(e.getPoint().getX(),e.getPoint().getY()), new Dimension(100,100),
        //        //        new ArrayList<ClassContent>(), false)));
//
        //        (tabbedPane.getClassyPackage()).getPackageView().getCurrentState()
        //                .classyMousePressed(e.getPoint().getX(), e.getPoint().getY(), this);
        //        repaint();
        //    }
        //});


        //TEST/////////////////////////

    }

    public Diagram getDiagram() {
        return diagram;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<DiagramElementPainter> getDiagramElementPainters() {
        return diagramElementPainters;
    }

    public void addDiagramElementPainter(DiagramElementPainter diagramElementPainter) {
        diagramElementPainters.add(diagramElementPainter);
        repaint();
    }

    public void popTemporaryConnectionPainter() {
        if (diagramElementPainters.get(diagramElementPainters.size() - 1) instanceof TemporaryConnectionPainter)
            diagramElementPainters.remove(diagramElementPainters.size() - 1);
    }

    // SELECTION STATE - DRAG MODE
    public void popTemporaryInterClassPainters(List<DiagramElementPainter> toRemove) {
        for (DiagramElementPainter dep : toRemove)
            diagramElementPainters.remove(dep);
    }

    // SELECTION STATE - SELECTION MODE
    public void popTemporarySelectionPainter() {
        if (!diagramElementPainters.isEmpty() && diagramElementPainters.get(diagramElementPainters.size() - 1) instanceof TemporarySelectionPainter)
            diagramElementPainters.remove(diagramElementPainters.size() - 1);
    }

    public void removeAllSelectionPainters() {
        diagramElementPainters.removeIf(dep -> dep instanceof TemporarySelectionPainter);
        repaint();
    }

    public TabbedPane getTabbedPane() {
        return tabbedPane;
    }

    @Override
    public void update(Object notification) {
        SubscriberNotification n = (SubscriberNotification) notification;
        // NOTIFICATION FOR RENAME = [TYPE.RENAME, DIAGRAM, NEW NAME]
        if (n.getType().equals(Type.RENAME)) {
            this.setName(n.getMsg());
            this.tabbedPane.renameDiagram(n.getClassyNode(), n.getMsg());

        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for(DiagramElementPainter diagramElementPainter : diagramElementPainters) {
            diagramElementPainter.paint((Graphics2D) g);
        }
    }
}
