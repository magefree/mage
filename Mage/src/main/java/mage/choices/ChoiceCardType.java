package mage.choices;

import mage.constants.CardType;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author emerald000
 */
public class ChoiceCardType extends ChoiceImpl {

    public ChoiceCardType() {
        this(true);
    }

    public ChoiceCardType(boolean required) {
        super(required);
        this.choices.addAll(Arrays.stream(CardType.values()).map(CardType::toString).collect(Collectors.toList()));
        this.message = "Choose a card type";
    }

    private ChoiceCardType(final ChoiceCardType choice) {
        super(choice);
    }

    @Override
    public ChoiceCardType copy() {
        return new ChoiceCardType(this);
    }
}
