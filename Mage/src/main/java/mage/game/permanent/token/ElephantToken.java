package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author BetaSteward_at_googlemail.com
 */
public final class ElephantToken extends TokenImpl {

    public ElephantToken() {
        super("Elephant", "3/3 green Elephant creature token");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.ELEPHANT);
        power = new MageInt(3);
        toughness = new MageInt(3);

        availableImageSetCodes = Arrays.asList("C13", "C14", "C15", "CMA", "CMD", "CNS", "GVL", "DDD",
                "EMA", "INV", "JUD", "MM2", "ODY", "ROE", "TSP", "VMA", "WWK", "MH1", "CMR", "C21");
    }

    public ElephantToken(final ElephantToken token) {
        super(token);
    }

    public ElephantToken copy() {
        return new ElephantToken(this);
    }

    @Override
    public void setExpansionSetCodeForImage(String code) {
        super.setExpansionSetCodeForImage(code);
    }
}