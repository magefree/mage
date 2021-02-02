
package mage.cards.f;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetPlayerOrPlaneswalker;

/**
 *
 * @author Galatolol
 */
public final class FuriousAssault extends CardImpl {

    public FuriousAssault(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");

        // Whenever you cast a creature spell, Furious Assault deals 1 damage to target player.
        Ability ability = new SpellCastControllerTriggeredAbility(new DamageTargetEffect(1), StaticFilters.FILTER_SPELL_A_CREATURE, false);
        ability.addTarget(new TargetPlayerOrPlaneswalker());
        this.addAbility(ability);
    }

    private FuriousAssault(final FuriousAssault card) {
        super(card);
    }

    @Override
    public FuriousAssault copy() {
        return new FuriousAssault(this);
    }
}
