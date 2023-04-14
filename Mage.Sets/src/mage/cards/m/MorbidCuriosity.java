
package mage.cards.m;

import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.dynamicvalue.common.SacrificeCostConvertedMana;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class MorbidCuriosity extends CardImpl {

    public MorbidCuriosity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}{B}");

        // As an additional cost to cast Morbid Curiosity, sacrifice an artifact or creature.
        this.getSpellAbility().addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_ARTIFACT_OR_CREATURE_SHORT_TEXT));

        // Draw cards equal to the converted mana cost of the sacrificed permanent.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(
                new SacrificeCostConvertedMana("permanent")
        ).setText("draw cards equal to the mana value of the sacrificed permanent"));
    }

    private MorbidCuriosity(final MorbidCuriosity card) {
        super(card);
    }

    @Override
    public MorbidCuriosity copy() {
        return new MorbidCuriosity(this);
    }
}
