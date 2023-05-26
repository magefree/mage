package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.HasteAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author spjspj
 */
public final class HellionToken extends TokenImpl {

    public HellionToken(boolean withHaste) {
        this();

        if (withHaste) {
            addAbility(HasteAbility.getInstance());
            this.description = "4/4 red Hellion creature token with haste";
        }
    }

    public HellionToken() {
        super("Hellion Token", "4/4 red Hellion creature token");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        subtype.add(SubType.HELLION);
        power = new MageInt(4);
        toughness = new MageInt(4);
    }

    public HellionToken(final HellionToken token) {
        super(token);
    }

    public HellionToken copy() {
        return new HellionToken(this);
    }
}
