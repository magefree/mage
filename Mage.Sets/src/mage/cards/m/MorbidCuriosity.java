
package mage.cards.m;

import java.util.UUID;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.dynamicvalue.common.SacrificeCostConvertedMana;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author fireshoes
 */
public final class MorbidCuriosity extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("an artifact or creature");

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate()));
    }

    public MorbidCuriosity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{B}{B}");

        // As an additional cost to cast Morbid Curiosity, sacrifice an artifact or creature.
        this.getSpellAbility().addCost(new SacrificeTargetCost(new TargetControlledPermanent(filter)));

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
