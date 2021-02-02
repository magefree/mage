
package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.AddCardSubtypeAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.mana.BlackManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterLandPermanent;

import java.util.UUID;

/**
 *
 * @author Galatolol
 */
public final class BlanketOfNight extends CardImpl {

    public BlanketOfNight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}{B}");
        

        // Each land is a Swamp in addition to its other land types.
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAllEffect(new BlackManaAbility(), Duration.WhileOnBattlefield, new FilterLandPermanent(),
                "Each land is a Swamp in addition to its other land types"));
        ability.addEffect(new AddCardSubtypeAllEffect(StaticFilters.FILTER_LAND, SubType.SWAMP, DependencyType.BecomeSwamp));
        this.addAbility(ability);
    }

    private BlanketOfNight(final BlanketOfNight card) {
        super(card);
    }

    @Override
    public BlanketOfNight copy() {
        return new BlanketOfNight(this);
    }
}
