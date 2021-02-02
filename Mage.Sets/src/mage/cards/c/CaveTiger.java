
package mage.cards.c;

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
 * @author Backfir3
 */
public final class CaveTiger extends CardImpl {

    public CaveTiger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.CAT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Cave Tiger becomes blocked by a creature, Cave Tiger gets +1/+1 until end of turn.
        this.addAbility(new BecomesBlockedByCreatureTriggeredAbility(new BoostSourceEffect(1, 1, Duration.EndOfTurn), false));
    }

    private CaveTiger(final CaveTiger card) {
        super(card);
    }

    @Override
    public CaveTiger copy() {
        return new CaveTiger(this);
    }
}
