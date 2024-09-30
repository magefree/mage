package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.OrCost;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetOpponent;
import mage.target.common.TargetSacrifice;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class ForbiddenRitual extends CardImpl {

    public ForbiddenRitual(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}{B}");

        // Sacrifice a nontoken permanent. If you do, target opponent loses 2 life unless they sacrifice a permanent or discards a card. You may repeat this process any number of times.
        this.getSpellAbility().addEffect(new ForbiddenRitualEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    private ForbiddenRitual(final ForbiddenRitual card) {
        super(card);
    }

    @Override
    public ForbiddenRitual copy() {
        return new ForbiddenRitual(this);
    }
}

class ForbiddenRitualEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterPermanent("a nontoken permanent");
    static {
        filter.add(TokenPredicate.FALSE);
    }

    ForbiddenRitualEffect() {
        super(Outcome.Sacrifice);
        this.staticText = "Sacrifice a nontoken permanent. If you do, target opponent loses 2 life unless " +
                "that player sacrifices a permanent or discards a card. You may repeat this process any number of times";
    }

    private ForbiddenRitualEffect(final ForbiddenRitualEffect effect) {
        super(effect);
    }

    @Override
    public ForbiddenRitualEffect copy() {
        return new ForbiddenRitualEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (controller == null || opponent == null) {
            return false;
        }
        do {
            TargetSacrifice targetSacrifice = new TargetSacrifice(filter);
            if (targetSacrifice.choose(Outcome.Sacrifice, source.getControllerId(), source.getSourceId(), source, game)) {
                Permanent permanent = game.getPermanent(targetSacrifice.getFirstTarget());
                if (permanent != null && permanent.sacrifice(source, game)) {
                    Cost cost = new OrCost("sacrifice a permanent or discard a card",
                            new SacrificeTargetCost(StaticFilters.FILTER_PERMANENT),
                            new DiscardCardCost()
                    );
                    if (!cost.canPay(source, source, opponent.getId(), game)
                            || !opponent.chooseUse(Outcome.Sacrifice, "Sacrifice a permanent or discard a card? (Otherwise you lose 2 life.)", source, game)
                            || !cost.pay(source, game, source, opponent.getId(), true)) {
                        opponent.loseLife(2, game, source, false);
                    }
                }
            }
        } while (controller.canRespond() && controller.chooseUse(
                Outcome.AIDontUseIt, "Repeat this process? (Sacrifice another nontoken permanent?)", source, game
        ));
        return true;
    }
}
