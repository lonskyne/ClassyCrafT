package raf.dsw.classycraft.app.core.Observer.notifications;

import raf.dsw.classycraft.app.core.ProjectTreeAbstraction.ClassyNode;

public class SubscriberNotification {
    private final Type type;
    private final ClassyNode classyNode;
    private String msg;

    public SubscriberNotification(Type type, ClassyNode classyNode) {
        this.type = type;
        this.classyNode = classyNode;
    }

    public SubscriberNotification(Type type, ClassyNode classyNode, String message) {
        this.type = type;
        this.classyNode = classyNode;
        this.msg = message;
    }

    public Type getType() {
        return type;
    }

    public ClassyNode getClassyNode() {
        return classyNode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
