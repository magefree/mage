package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.OrCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ModifiedPredicate;
import mage.game.Game;
import mage.target.common.TargetCreatureOrPlaneswalker;

import java.util.UUID;

/**
 * @author grimreap124
 */
public final class LethalThrowdown extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("a modified creature");

    static {
        filter.add(ModifiedPredicate.instance);
    }

    public LethalThrowdown(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{B}");

        // As an additional cost to cast this spell, sacrifice a creature or sacrifice a modified creature.
        this.getSpellAbility()
                .addCost(new OrCost("sacrifice a creature or sacrifice a modified creature",
                        new SacrificeTargetCost(StaticFilters.FILTER_PERMANENT_CREATURE)
                                .setText("sacrifice a creature"),
                        new SacrificeTargetCost(filter).setText("sacrifice a modified creature")));
        // Destroy target creature or planeswalker. If the modified creature was sacrificed, draw a card.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addEffect(new LethalThrowdownEffect());
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());
    }

    private LethalThrowdown(final LethalThrowdown card) {
        super(card);
    }

    @Override
    public LethalThrowdown copy() {
        return new LethalThrowdown(this);
    }
}

class LethalThrowdownEffect extends OneShotEffect {

    LethalThrowdownEffect() {
        super(Outcome.Benefit);
        staticText = "If the modified creature was sacrificed, draw a card";
    }

    private LethalThrowdownEffect(final LethalThrowdownEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Cost cost : source.getCosts()) {
            if (cost instanceof OrCost) {
                OrCost orCost = (OrCost) cost;

                // This is a bit of a hack to check if the modified creature was sacrificed
                // if this comes up anywhere else we should think of a more elegant solution
                if (orCost.getSelectedCost().getText().equals("sacrifice a modified creature")) {
                    return new DrawCardSourceControllerEffect(1).apply(game, source);
                }
            }
        }
        return false;
    }

    @Override
    public LethalThrowdownEffect copy() {
        return new LethalThrowdownEffect(this);
    }
}
