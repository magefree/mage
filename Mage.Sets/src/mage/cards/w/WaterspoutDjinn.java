
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.costs.common.ReturnToHandChosenControlledPermanentCost;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author fireshoes
 */
public final class WaterspoutDjinn extends CardImpl {
    
    private static final FilterControlledLandPermanent filter = new FilterControlledLandPermanent("an untapped Island");

    static{
        filter.add(SubType.ISLAND.getPredicate());
        filter.add(TappedPredicate.UNTAPPED);
    }

    public WaterspoutDjinn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}{U}");
        this.subtype.add(SubType.DJINN);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // At the beginning of your upkeep, sacrifice Waterspout Djinn unless you return an untapped Island you control to its owner's hand.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new SacrificeSourceUnlessPaysEffect(new ReturnToHandChosenControlledPermanentCost(new TargetControlledPermanent(filter))),
                TargetController.YOU, false));
    }

    private WaterspoutDjinn(final WaterspoutDjinn card) {
        super(card);
    }

    @Override
    public WaterspoutDjinn copy() {
        return new WaterspoutDjinn(this);
    }
}
