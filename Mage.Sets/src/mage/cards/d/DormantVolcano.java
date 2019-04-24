
package mage.cards.d;

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
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author LevelX2
 */
public final class DormantVolcano extends CardImpl {

    private static final FilterControlledLandPermanent filter = new FilterControlledLandPermanent("an untapped Mountain");

    static {
        filter.add(new SubtypePredicate(SubType.MOUNTAIN));
        filter.add(Predicates.not(new TappedPredicate()));
    }

    public DormantVolcano(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // Dormant Volcano enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // When Dormant Volcano enters the battlefield, sacrifice it unless you return an untapped Mountain you control to its owner's hand.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SacrificeSourceUnlessPaysEffect(new ReturnToHandChosenControlledPermanentCost(new TargetControlledPermanent(filter)))));

        // {tap}: Add {C}{R}.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, new Mana(1, 0, 0, 0, 0, 0, 0, 1), new TapSourceCost()));

    }

    public DormantVolcano(final DormantVolcano card) {
        super(card);
    }

    @Override
    public DormantVolcano copy() {
        return new DormantVolcano(this);
    }
}
