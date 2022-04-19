package mage.cards.f;

import java.util.HashSet;
import java.util.UUID;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author weirddan455
 */
public final class FatalGrudge extends CardImpl {

    public FatalGrudge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{B}{R}");

        // As an additional cost to cast this spell, sacrifice a nonland permanent.
        this.getSpellAbility().addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_PERMANENT_NON_LAND));

        // Each opponent chooses a permanent they control that shares a type with the sacrificed permanent and sacrifices it.
        this.getSpellAbility().addEffect(new FatalGrudgeEffect());

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1));
    }

    private FatalGrudge(final FatalGrudge card) {
        super(card);
    }

    @Override
    public FatalGrudge copy() {
        return new FatalGrudge(this);
    }
}

class FatalGrudgeEffect extends OneShotEffect {

    public FatalGrudgeEffect() {
        super(Outcome.Sacrifice);
        this.staticText = "each opponent chooses a permanent they control that shares a type with the sacrificed permanent and sacrifices it";
    }

    private FatalGrudgeEffect(final FatalGrudgeEffect effect) {
        super(effect);
    }

    @Override
    public FatalGrudgeEffect copy() {
        return new FatalGrudgeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        HashSet<CardType> types = new HashSet<>();
        for (Cost cost : source.getCosts()) {
            if (cost instanceof SacrificeTargetCost) {
                for (Permanent sacrificed : ((SacrificeTargetCost) cost).getPermanents()) {
                    types.addAll(sacrificed.getCardType(game));
                }
            }
        }
        FilterControlledPermanent filter = new FilterControlledPermanent("permanent you control that shares a type with the sacrificed permanent");
        filter.add(new FatalGrudgePredicate(types));
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            Player opponent = game.getPlayer(opponentId);
            if (opponent != null) {
                TargetControlledPermanent target = new TargetControlledPermanent(1, 1, filter, true);
                opponent.chooseTarget(Outcome.Sacrifice, target, source, game);
                Permanent permanent = game.getPermanent(target.getFirstTarget());
                if (permanent != null) {
                    permanent.sacrifice(source, game);
                }
            }
        }
        return true;
    }
}

class FatalGrudgePredicate implements Predicate<MageObject> {

    private final HashSet<CardType> types;

    public FatalGrudgePredicate(HashSet<CardType> types) {
        this.types = types;
    }

    @Override
    public boolean apply(MageObject input, Game game) {
        for (CardType type : input.getCardType(game)) {
            if (types.contains(type)) {
                return true;
            }
        }
        return false;
    }
}
