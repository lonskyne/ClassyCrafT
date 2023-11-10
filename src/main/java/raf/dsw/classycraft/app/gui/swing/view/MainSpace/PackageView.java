package raf.dsw.classycraft.app.gui.swing.view.MainSpace;

import raf.dsw.classycraft.app.core.Observer.ISubscriber;
import raf.dsw.classycraft.app.core.Observer.notifications.SubscriberNotification;
import raf.dsw.classycraft.app.core.Observer.notifications.Type;
import raf.dsw.classycraft.app.core.ProjectTreeImplementation.Diagram;
import raf.dsw.classycraft.app.core.ProjectTreeImplementation.Package;

import javax.swing.*;

public class PackageView extends JPanel implements ISubscriber {
    private final HeadlineSpace headlineSpace;
    private final TabbedPane tabbedPane;

    private Package focusedPackage;

    public PackageView(HeadlineSpace headlineSpace, TabbedPane tabbedPane) {
        this.headlineSpace = headlineSpace;
        this.tabbedPane = tabbedPane;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(headlineSpace);
        add(tabbedPane);
    }

    public void setupView(Package openedPackage) {
        this.tabbedPane.loadPackage(openedPackage);
        this.headlineSpace.setup(tabbedPane.getClassyProject().getName(), tabbedPane.getClassyProject().getAuthor());
        if (!tabbedPane.getClassyPackage().checkSubscriber(this))
            this.tabbedPane.getClassyPackage().addSubscriber(this);
        repaint();
    }

    public void totalClear() {
        this.headlineSpace.clear();
        this.tabbedPane.clear();
        this.tabbedPane.revalidate();
    }

    @Override
    public void update(Object notification) {
        SubscriberNotification n = (SubscriberNotification) notification;

            // NOTIFICATION FOR RENAME = [TYPE.RENAME, PROJECT, NEW NAME]
        if (n.getType().equals(Type.RENAME)) {
            if (n.getClassyNode() == this.tabbedPane.getClassyPackage().findProject())
                this.headlineSpace.setupProjectName(n.getMsg());

            // NOTIFICATION FOR AUTHOR CHANGE = [TYPE.CHANGE_AUTHOR, PROJECT, NEW AUTHOR]
        } else if (n.getType().equals(Type.CHANGE_AUTHOR)) {
            if (n.getClassyNode() == this.tabbedPane.getClassyPackage().findProject())
                this.headlineSpace.setupAuthor(n.getMsg());

            // NOTIFICATION FOR OPENING PACKAGE IN VIEW = [TYPE.ADD, PACKAGE, NONE]
        } else if (n.getType().equals(Type.OPEN)) {
            this.totalClear();
            this.setupView((Package) n.getClassyNode());

            // NOTIFICATION FOR ADDING DIAGRAM = [TYPE.ADD, NEW DIAGRAM, NONE]
        } else if (n.getType().equals(Type.ADD)) {
            if (n.getClassyNode().getParent() == tabbedPane.getClassyPackage() &&
                    tabbedPane.getClassyPackage().getChildren().contains(n.getClassyNode())) {
                this.tabbedPane.addNewDiagram((Diagram) n.getClassyNode());
                this.revalidate();
            }
            // NOTIFICATION FOR REMOVING NODES = [TYPE.ADD, NODE, NONE]
        } else if (n.getType().equals(Type.REMOVE)) {
            if (n.getClassyNode() instanceof Diagram) {
                this.tabbedPane.removeDiagram(n.getClassyNode());
            } else {
                if (tabbedPane.checkParent(n.getClassyNode()))
                    totalClear();
            }
        }
    }
}