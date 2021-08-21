package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.ReachAbility;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author TheElk801
 */
public final class LolthSpiderToken extends TokenImpl {

    public LolthSpiderToken() {
        super("Spider", "2/1 black Spider creature token with menace and reach");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.SPIDER);
        power = new MageInt(2);
        toughness = new MageInt(1);

        // Menace
        this.addAbility(new MenaceAbility());

        // Reach
        this.addAbility(ReachAbility.getInstance());

        availableImageSetCodes = Arrays.asList("AFR");
    }

    public LolthSpiderToken(final LolthSpiderToken token) {
        super(token);
    }

    public LolthSpiderToken copy() {
        return new LolthSpiderToken(this);
    }
}
