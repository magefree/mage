
package mage.cards.o;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LoneFox
 */
public final class Onslaught extends CardImpl {

    public Onslaught(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{R}");

        // Whenever you cast a creature spell, tap target creature.
        Ability ability = new SpellCastControllerTriggeredAbility(new TapTargetEffect(), StaticFilters.FILTER_SPELL_A_CREATURE, false);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private Onslaught(final Onslaught card) {
        super(card);
    }

    @Override
    public Onslaught copy() {
        return new Onslaught(this);
    }
}
