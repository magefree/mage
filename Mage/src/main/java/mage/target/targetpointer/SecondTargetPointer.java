package mage.target.targetpointer;

public class SecondTargetPointer extends NthTargetPointer {

    public SecondTargetPointer() {
        super(2);
    }

    public SecondTargetPointer(final SecondTargetPointer secondTargetPointer) {
        super(secondTargetPointer);
    }

    @Override
    public SecondTargetPointer copy() {
        return new SecondTargetPointer(this);
    }
}
