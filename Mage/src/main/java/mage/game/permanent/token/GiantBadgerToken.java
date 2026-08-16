package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.common.BlocksSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

/**
 * @author muz
 */
public final class GiantBadgerToken extends TokenImpl {

    public GiantBadgerToken() {
        super("Giant Badger", "Giant Badger token");
        manaCost = new ManaCostsImpl<>("{1}{G}{G}");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.BADGER);
        power = new MageInt(2);
        toughness = new MageInt(2);

        this.addAbility(new BlocksSourceTriggeredAbility(new BoostSourceEffect(2, 2, Duration.EndOfTurn, "it")));
    }

    private GiantBadgerToken(final GiantBadgerToken token) {
        super(token);
    }

    @Override
    public GiantBadgerToken copy() {
        return new GiantBadgerToken(this);
    }
}
