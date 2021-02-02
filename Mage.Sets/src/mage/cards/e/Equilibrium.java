
package mage.cards.e;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class Equilibrium extends CardImpl {

    public Equilibrium(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}{U}");

        // Whenever you cast a creature spell, you may pay {1}. If you do, return target creature to its owner's hand.
        Ability ability = new SpellCastControllerTriggeredAbility(new DoIfCostPaid(new ReturnToHandTargetEffect(), new GenericManaCost(1)), StaticFilters.FILTER_SPELL_A_CREATURE, false);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private Equilibrium(final Equilibrium card) {
        super(card);
    }

    @Override
    public Equilibrium copy() {
        return new Equilibrium(this);
    }
}
