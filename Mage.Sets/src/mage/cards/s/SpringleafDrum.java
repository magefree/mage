package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author Loki
 */
public final class SpringleafDrum extends CardImpl {

    public SpringleafDrum(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        // {T}, Tap an untapped creature you control: Add one mana of any color.
        Ability ability = new AnyColorManaAbility();
        ability.addCost(new TapTargetCost(StaticFilters.FILTER_CONTROLLED_UNTAPPED_CREATURE));
        this.addAbility(ability);
    }

    private SpringleafDrum(final SpringleafDrum card) {
        super(card);
    }

    @Override
    public SpringleafDrum copy() {
        return new SpringleafDrum(this);
    }
}
