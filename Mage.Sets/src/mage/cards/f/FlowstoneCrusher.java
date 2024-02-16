
package mage.cards.f;

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
 * @author LoneFox
 */
public final class FlowstoneCrusher extends CardImpl {

    public FlowstoneCrusher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}{R}");
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // {R}: Flowstone Crusher gets +1/-1 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD,
            new BoostSourceEffect(1, -1, Duration.EndOfTurn), new ManaCostsImpl<>("{R}")));
    }

    private FlowstoneCrusher(final FlowstoneCrusher card) {
        super(card);
    }

    @Override
    public FlowstoneCrusher copy() {
        return new FlowstoneCrusher(this);
    }
}
