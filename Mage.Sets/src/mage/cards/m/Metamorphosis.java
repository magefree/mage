
package mage.cards.m;

import java.util.UUID;
import mage.ConditionalMana;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.mana.AddConditionalManaOfAnyColorEffect;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.abilities.mana.conditional.CreatureCastConditionalMana;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author MarcoMarin
 */
public final class Metamorphosis extends CardImpl {

    public Metamorphosis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{G}");

        // As an additional cost to cast Metamorphosis, sacrifice a creature.
        this.getSpellAbility().addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT));
        this.getSpellAbility().addEffect(new MetamorphosisEffect());
        // Add X mana of any one color, where X is one plus the sacrificed creature's converted mana cost. Spend this mana only to cast creature spells.
    }

    private Metamorphosis(final Metamorphosis card) {
        super(card);
    }

    @Override
    public Metamorphosis copy() {
        return new Metamorphosis(this);
    }
}

class MetamorphosisEffect extends OneShotEffect {

    public MetamorphosisEffect() {
        super(Outcome.PutManaInPool);
        staticText = "Add X mana of any one color, where X is 1 plus the sacrificed creature's mana value. Spend this mana only to cast creature spells.";
    }

    public MetamorphosisEffect(final MetamorphosisEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int amount = 0;
        for (Cost cost : source.getCosts()) {
            if (cost instanceof SacrificeTargetCost && !((SacrificeTargetCost) cost).getPermanents().isEmpty()) {
                amount = ((SacrificeTargetCost) cost).getPermanents().get(0).getManaValue() + 1;
                break;
            }
        }
        if (amount > 0) {
            new AddConditionalManaOfAnyColorEffect(amount, new MetamorphosisManaBuilder()).apply(game, source);
        }
        return false;
    }

    @Override
    public MetamorphosisEffect copy() {
        return new MetamorphosisEffect(this);
    }

}

class MetamorphosisManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options) {
        return new CreatureCastConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to cast creature spells";
    }
}
