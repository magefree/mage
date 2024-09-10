

package mage.choices;

/**
 * @author LevelX2
 */

public class ChoiceLeftOrRight extends ChoiceImpl {

    public ChoiceLeftOrRight() {
        super(true);
        this.choices.add("Left");
        this.choices.add("Right");
        this.message = "Choose left or right";
    }

    protected ChoiceLeftOrRight(final ChoiceLeftOrRight choice) {
        super(choice);
    }

    @Override
    public ChoiceLeftOrRight copy() {
        return new ChoiceLeftOrRight(this);
    }

}
