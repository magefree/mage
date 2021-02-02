
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author LevelX2
 */
public final class Pyroconvergence extends CardImpl {

    public Pyroconvergence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{R}");

        // Whenever you cast a multicolored spell, Pyroconvergence deals 2 damage to any target.
        Ability ability = new SpellCastControllerTriggeredAbility(
                new DamageTargetEffect(2), StaticFilters.FILTER_SPELL_A_MULTICOLORED, false);
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private Pyroconvergence(final Pyroconvergence card) {
        super(card);
    }

    @Override
    public Pyroconvergence copy() {
        return new Pyroconvergence(this);
    }
}
