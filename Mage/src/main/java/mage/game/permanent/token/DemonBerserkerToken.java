package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.MenaceAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class DemonBerserkerToken extends TokenImpl {

    public DemonBerserkerToken() {
        super("Demon Berserker Token", "2/3 red Demon Berserker creature token with menace");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        subtype.add(SubType.DEMON);
        subtype.add(SubType.BERSERKER);
        power = new MageInt(2);
        toughness = new MageInt(3);

        addAbility(new MenaceAbility());
    }

    private DemonBerserkerToken(final DemonBerserkerToken token) {
        super(token);
    }

    @Override
    public DemonBerserkerToken copy() {
        return new DemonBerserkerToken(this);
    }
}
