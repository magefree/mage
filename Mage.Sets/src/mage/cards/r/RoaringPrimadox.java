
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.OnEventTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandChosenControlledPermanentEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.events.GameEvent.EventType;

/**
 *
 * @author North
 */
public final class RoaringPrimadox extends CardImpl {

    public RoaringPrimadox(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // At the beginning of your upkeep, return a creature you control to its owner's hand.
        this.addAbility(new OnEventTriggeredAbility(EventType.UPKEEP_STEP_PRE, "beginning of your upkeep", new ReturnToHandChosenControlledPermanentEffect(new FilterControlledCreaturePermanent())));
    }

    private RoaringPrimadox(final RoaringPrimadox card) {
        super(card);
    }

    @Override
    public RoaringPrimadox copy() {
        return new RoaringPrimadox(this);
    }
}
