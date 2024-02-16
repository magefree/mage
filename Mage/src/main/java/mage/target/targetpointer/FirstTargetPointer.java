package mage.target.targetpointer;

public class FirstTargetPointer extends NthTargetPointer {

    public FirstTargetPointer() {
        super(1);
    }

    protected FirstTargetPointer(final FirstTargetPointer firstTargetPointer) {
        super(firstTargetPointer);
    }

    @Override
    public FirstTargetPointer copy() {
        return new FirstTargetPointer(this);
    }
}
