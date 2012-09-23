package ru.romanchuk.sqlmaster.parser;

import ru.romanchuk.sqlmaster.parser.tree.ParameterNode;
import ru.romanchuk.sqlmaster.parser.tree.RootNode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexey Romanchuk
 */
class TemplateTreeImpl implements TemplateTree {
    private RootNode tree;

    public TemplateTreeImpl(RootNode tree) {
        this.tree = tree;
    }

    @Override
    public RootNode getRootNode() {
        return tree;
    }

    @Override
    public ParameterNode getParameterNode(final String name) {
        final ParameterNode[] result = {null};
        visit(getRootNode(), new TreeVisitor() {
            @Override
            public void visit(Node node) {
                if (node instanceof ParameterNode) {
                    if (((ParameterNode) node).getName().equals(name)) {
                        result[0] = (ParameterNode) node;
                    }
                }
            }
        });
        return result[0];
    }

    @Override
    public List<ParameterNode> getParameters() {
        final List<ParameterNode> result = new ArrayList<ParameterNode>();
        visit(getRootNode(), new TreeVisitor() {
            @Override
            public void visit(Node node) {
                if (node instanceof ParameterNode) {
                    result.add((ParameterNode) node);
                }
            }
        });
        return result;
    }

    public void visit(Node node, TreeVisitor v) {
        v.visit(node);
        if (node instanceof NodeWithChildes) {
            for (Node walker : ((NodeWithChildes) node).getChildes()) {
                visit(walker, v);
            }
        }
    }

    public static interface TreeVisitor {
        void visit(Node node);
    }
}
