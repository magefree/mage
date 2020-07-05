package mage.choices;

import mage.constants.CardType;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author emerald000
 */
public class ChoiceCardType extends ChoiceImpl {

    public ChoiceCardType() {
        this(true, Arrays.stream(CardType.values()).collect(Collectors.toList()));
    }

    public ChoiceCardType(boolean required, List<CardType> cardTypes) {
        super(required);
        this.choices.addAll(cardTypes.stream().map(CardType::toString).collect(Collectors.toList()));
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
