package mage.target.targetpointer;

public class ThirdTargetPointer extends NthTargetPointer {

    public ThirdTargetPointer() {
        super(3);
    }

    protected ThirdTargetPointer(final ThirdTargetPointer thirdTargetPointer) {
        super(thirdTargetPointer);
    }

    @Override
    public ThirdTargetPointer copy() {
        return new ThirdTargetPointer(this);
    }
}
