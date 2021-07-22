
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.mana.AnyColorManaAbility;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author spjspj
 */
public final class SurvivorsEncampment extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("an untapped creature you control");

    static {
        filter.add(TappedPredicate.UNTAPPED);
    }

    public SurvivorsEncampment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.DESERT);

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {T}, Tap an untapped creature you control: Add one mana of any color.
        Ability ability = new AnyColorManaAbility();
        ability.addCost(new TapTargetCost(new TargetControlledPermanent(filter)));
        this.addAbility(ability);
    }

    private SurvivorsEncampment(final SurvivorsEncampment card) {
        super(card);
    }

    @Override
    public SurvivorsEncampment copy() {
        return new SurvivorsEncampment(this);
    }
}
