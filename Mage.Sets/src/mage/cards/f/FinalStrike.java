
package mage.cards.f;

import java.util.UUID;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.dynamicvalue.common.SacrificeCostCreaturesPower;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetOpponentOrPlaneswalker;

/**
 *
 * @author fireshoes
 */
public final class FinalStrike extends CardImpl {

    public FinalStrike(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}{B}");

        // As an additional cost to cast Final Strike, sacrifice a creature.
        this.getSpellAbility().addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT));

        // Final Strike deals damage to target opponent equal to the sacrificed creature's power.
        Effect effect = new DamageTargetEffect(SacrificeCostCreaturesPower.instance);
        effect.setText("{this} deals damage to target opponent or planeswalker equal to the sacrificed creature's power");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetOpponentOrPlaneswalker());
    }

    private FinalStrike(final FinalStrike card) {
        super(card);
    }

    @Override
    public FinalStrike copy() {
        return new FinalStrike(this);
    }
}
