
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandChosenControlledPermanentEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class SpeciesGorger extends CardImpl {

    public SpeciesGorger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}{U}");
        this.subtype.add(SubType.FROG);
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // At the beginning of your upkeep, return a creature you control to its owner's hand.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new ReturnToHandChosenControlledPermanentEffect(new FilterControlledCreaturePermanent()), TargetController.YOU, false));
        
    }

    private SpeciesGorger(final SpeciesGorger card) {
        super(card);
    }

    @Override
    public SpeciesGorger copy() {
        return new SpeciesGorger(this);
    }
}

