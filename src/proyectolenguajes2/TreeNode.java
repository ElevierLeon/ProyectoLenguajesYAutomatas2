/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectolenguajes2;

import java.util.ArrayList;

public class TreeNode {
    private String label;
    private ArrayList<TreeNode> children;

    public TreeNode(String label) {
        this.label = label;
        this.children = new ArrayList<>();
    }

    public TreeNode(String label, String... childLabels) {
        this.label = label;
        this.children = new ArrayList<>();
        for (String childLabel : childLabels) {
            this.children.add(new TreeNode(childLabel));
        }
    }

    public TreeNode(String label, TreeNode... children) {
        this.label = label;
        this.children = new ArrayList<>();
        for (TreeNode child : children) {
            this.children.add(child);
        }
    }

    public void addChild(TreeNode child) {
        children.add(child);
    }

    public String getLabel() {
        return label;
    }

    public ArrayList<TreeNode> getChildren() {
        return children;
    }

    public void printTree() {
        printNode(this, "");
    }

    private void printNode(TreeNode node, String indent) {
        System.out.println(indent + node.getLabel());
        for (TreeNode child : node.getChildren()) {
            printNode(child, indent + "  ");
        }
    }
}