
package mage.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author BetaSteward_at_googlemail.com
 * @param <T>
 */
public class TreeNode<T> {

    protected T data;
    protected List<TreeNode<T>> children;

    public TreeNode() {
        super();
        children = new ArrayList<>();
    }

    public TreeNode(T data) {
        this();
        setData(data);
    }

    public List<TreeNode<T>> getChildren() {
        return this.children;
    }

    public int getNumberOfChildren() {
        return getChildren().size();
    }

    public boolean hasChildren() {
        return (getNumberOfChildren() > 0);
    }

    public void setChildren(List<TreeNode<T>> children) {
        this.children = children;
    }

    public void addChild(TreeNode<T> child) {
        children.add(child);
    }

    public void addChild(T child) {
        children.add(new TreeNode<>(child));
    }

    public void addChildAt(int index, TreeNode<T> child) throws IndexOutOfBoundsException {
        children.add(index, child);
    }

    public void removeChildren() {
        this.children.clear();
    }

    public void removeChildAt(int index) throws IndexOutOfBoundsException {
        children.remove(index);
    }

    public TreeNode<T> getChildAt(int index) throws IndexOutOfBoundsException {
        return children.get(index);
    }

    public T getData() {
        return this.data;
    }

    private void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return getData().toString();
    }

    @Override
    public int hashCode() {
        return 0; // TODO
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TreeNode<T> that = (TreeNode<T>) o;

        if (!Objects.equals(this.data, that.data)) {
            return false;
        }

        return Objects.equals(this.children, that.children);
    }
}
