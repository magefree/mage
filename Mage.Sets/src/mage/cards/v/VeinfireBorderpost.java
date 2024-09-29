

package mage.cards.v;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.common.ReturnToHandChosenControlledPermanentCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author Loki
 */
public final class VeinfireBorderpost extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledLandPermanent("a basic land");

    static {
        filter.add(SuperType.BASIC.getPredicate());
    }

    public VeinfireBorderpost (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{1}{B}{R}");
        


        // You may pay {1} and return a basic land you control to its owner's hand rather than pay Veinfire Borderpost's mana cost.
        Ability ability = new AlternativeCostSourceAbility(new GenericManaCost(1));
        ability.addCost(new ReturnToHandChosenControlledPermanentCost(new TargetControlledPermanent(filter)));
        this.addAbility(ability);

        // Veinfire Borderpost enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // Tap: Add {B} or {R}.
        this.addAbility(new BlackManaAbility());
        this.addAbility(new RedManaAbility());
    }

    private VeinfireBorderpost(final VeinfireBorderpost card) {
        super(card);
    }

    @Override
    public VeinfireBorderpost copy() {
        return new VeinfireBorderpost(this);
    }

}
