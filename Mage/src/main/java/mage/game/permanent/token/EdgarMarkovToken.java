package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author spjspj
 */
public final class EdgarMarkovToken extends TokenImpl {

    static final private List<String> tokenImageSets = new ArrayList<>();

    static {
        tokenImageSets.addAll(Arrays.asList("C17"));
    }

    public EdgarMarkovToken() {
        super("Vampire Token", "1/1 black Vampire creature token");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.VAMPIRE);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    public EdgarMarkovToken(final EdgarMarkovToken token) {
        super(token);
    }

    public EdgarMarkovToken copy() {
        return new EdgarMarkovToken(this);
    }
}
