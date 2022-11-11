package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class RemorselessPunishment extends CardImpl {

    public RemorselessPunishment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}{B}");

        // Target opponent loses 5 life unless that player discards two cards or sacrifices a creature or planeswalker. Repeat this process once.
        getSpellAbility().addEffect(new RemorselessPunishmentEffect());
        getSpellAbility().addTarget(new TargetOpponent());
    }

    private RemorselessPunishment(final RemorselessPunishment card) {
        super(card);
    }

    @Override
    public RemorselessPunishment copy() {
        return new RemorselessPunishment(this);
    }
}

class RemorselessPunishmentEffect extends OneShotEffect {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("creature or planeswalker");

    static {
        filter.add(Predicates.or(CardType.CREATURE.getPredicate(), CardType.PLANESWALKER.getPredicate()));
    }

    public RemorselessPunishmentEffect() {
        super(Outcome.LoseLife);
        this.staticText = "Target opponent loses 5 life unless that player discards two cards or sacrifices a creature or planeswalker. Repeat this process once";
    }

    public RemorselessPunishmentEffect(final RemorselessPunishmentEffect effect) {
        super(effect);
    }

    @Override
    public RemorselessPunishmentEffect copy() {
        return new RemorselessPunishmentEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player opponent = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (opponent != null) {
            handleBaseEffect(game, source, opponent, "1st");
            handleBaseEffect(game, source, opponent, "2nd");
            return true;
        }
        return false;
    }

    private void handleBaseEffect(Game game, Ability source, Player opponent, String iteration) {
        if (opponent.getHand().size() > 1) {
            if (opponent.chooseUse(outcome, "Choose your " + iteration + " punishment.", null, "Discard two cards", "Choose another option", source, game)) {
                opponent.discard(2, false, false, source, game);
                return;
            }
        }
        if (game.getBattlefield().containsControlled(filter, opponent.getId(), source, game, 1)) {
            if (opponent.chooseUse(outcome, "Choose your " + iteration + " punishment.", null, "Sacrifice a creature or planeswalker", "Lose 5 life", source, game)) {
                TargetPermanent target = new TargetPermanent(1, 1, filter, true);
                if (target.choose(Outcome.Sacrifice, opponent.getId(), source.getId(), source, game)) {
                    for (UUID targetId : target.getTargets()) {
                        Permanent permanent = game.getPermanent(targetId);
                        if (permanent != null) {
                            permanent.sacrifice(source, game);
                        }
                    }
                    return;
                }

            }
        }
        opponent.loseLife(5, game, source, false);
    }
}
