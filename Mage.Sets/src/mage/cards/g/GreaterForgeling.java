
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author Loki
 */
public final class GreaterForgeling extends CardImpl {

    public GreaterForgeling(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}{R}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // {1}{R}: Greater Forgeling gets +3/-3 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(3, -3, Duration.EndOfTurn), new ManaCostsImpl<>("{1}{R}")));
    }

    private GreaterForgeling(final GreaterForgeling card) {
        super(card);
    }

    @Override
    public GreaterForgeling copy() {
        return new GreaterForgeling(this);
    }
}
