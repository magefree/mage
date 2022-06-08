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
        super("Elephant Token", "3/3 green Elephant creature token");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.ELEPHANT);
        power = new MageInt(3);
        toughness = new MageInt(3);

        availableImageSetCodes = Arrays.asList("C14", "C15", "CMA", "CNS", "GVL", "DDD",
                "EMA", "INV", "JUD", "MM2", "ODY", "TSP", "VMA", "WWK", "MH1", "CMR", "C21", "MIC", "NEC", "2XM", "NCC", "MM3", "DDS");
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