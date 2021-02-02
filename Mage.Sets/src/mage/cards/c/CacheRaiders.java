
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandChosenControlledPermanentEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterControlledPermanent;

/**
 *
 * @author Loki
 */
public final class CacheRaiders extends CardImpl {

    public CacheRaiders(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}{U}");
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.ROGUE);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
        
        //At the beginning of your upkeep, return a permanent you control to its owner's hand.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new ReturnToHandChosenControlledPermanentEffect(new FilterControlledPermanent()), TargetController.YOU, false));
    }

    private CacheRaiders(final CacheRaiders card) {
        super(card);
    }

    @Override
    public CacheRaiders copy() {
        return new CacheRaiders(this);
    }
}
