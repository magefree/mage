package mage.choices;

import mage.MageObject;
import mage.constants.SubType;

import java.util.LinkedHashSet;
import java.util.stream.Collectors;

public class ChoicePlaneswalkerType extends ChoiceImpl {

    private static final String DEFAULT_MESSAGE = "Choose a planeswalker type";

    public ChoicePlaneswalkerType() {
        this(true, DEFAULT_MESSAGE, null);
    }

    public ChoicePlaneswalkerType(MageObject source) {
        this(true, DEFAULT_MESSAGE, source);
    }

    public ChoicePlaneswalkerType(String chooseMessage, MageObject source) {
        this(true, chooseMessage, source);
    }

    public ChoicePlaneswalkerType(boolean required, String chooseMessage, MageObject source) {
        super(required);
        this.setChoices(SubType.getPlaneswalkerTypes().stream().map(SubType::toString).collect(Collectors.toCollection(LinkedHashSet::new)));
        this.setMessage(chooseMessage);
        if (source != null) {
            this.setSubMessage(source.getIdName());
        }
        this.setSearchEnabled(true);
    }

    public ChoicePlaneswalkerType(final ChoicePlaneswalkerType choice) {
        super(choice);
    }

    @Override
    public ChoicePlaneswalkerType copy() {
        return new ChoicePlaneswalkerType(this);
    }
}
