
package mage.cards.c;

import java.util.UUID;
import mage.Mana;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.ReturnToHandChosenControlledPermanentCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author LevelX2
 */
public final class CoralAtoll extends CardImpl {

    private static final FilterControlledLandPermanent filter = new FilterControlledLandPermanent("an untapped Island");

    static {
        filter.add(SubType.ISLAND.getPredicate());
        filter.add(TappedPredicate.UNTAPPED);
    }

    public CoralAtoll(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // Coral Atoll enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
        // When Coral Atoll enters the battlefield, sacrifice it unless you return an untapped Island you control to its owner's hand.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SacrificeSourceUnlessPaysEffect(new ReturnToHandChosenControlledPermanentCost(new TargetControlledPermanent(filter)))));
        // {tap}: Add {C}{U}.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, new Mana(0, 1, 0, 0, 0, 0, 0, 1), new TapSourceCost()));

    }

    private CoralAtoll(final CoralAtoll card) {
        super(card);
    }

    @Override
    public CoralAtoll copy() {
        return new CoralAtoll(this);
    }
}
