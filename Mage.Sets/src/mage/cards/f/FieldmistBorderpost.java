

package mage.cards.f;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.common.ReturnToHandChosenControlledPermanentCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.WhiteManaAbility;
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
public final class FieldmistBorderpost extends CardImpl {
    
    private static final FilterControlledPermanent filter = new FilterControlledLandPermanent("a basic land");

    static {
        filter.add(SuperType.BASIC.getPredicate());
    }

    public FieldmistBorderpost (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{1}{W}{U}");



        // You may pay {1} and return a basic land you control to its owner's hand rather than pay Fieldmist Borderpost's mana cost.
        Ability ability = new AlternativeCostSourceAbility(new GenericManaCost(1));
        ability.addCost(new ReturnToHandChosenControlledPermanentCost(new TargetControlledPermanent(filter)));
        this.addAbility(ability);

        // Fieldmist Borderpost enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // Tap: Add {W} or {U}.
        this.addAbility(new WhiteManaAbility());
        this.addAbility(new BlueManaAbility());
    }

    private FieldmistBorderpost(final FieldmistBorderpost card) {
        super(card);
    }

    @Override
    public FieldmistBorderpost copy() {
        return new FieldmistBorderpost(this);
    }
}
