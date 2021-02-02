
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BecomesBlockedByCreatureTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;

/**
 *
 * @author fireshoes
 */
public final class RabidWolverines extends CardImpl {

    public RabidWolverines(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}{G}");
        this.subtype.add(SubType.WOLVERINE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever Rabid Wolverines becomes blocked by a creature, Rabid Wolverines gets +1/+1 until end of turn.
        this.addAbility(new BecomesBlockedByCreatureTriggeredAbility(new BoostSourceEffect(1, 1, Duration.EndOfTurn), false));
    }

    private RabidWolverines(final RabidWolverines card) {
        super(card);
    }

    @Override
    public RabidWolverines copy() {
        return new RabidWolverines(this);
    }
}
