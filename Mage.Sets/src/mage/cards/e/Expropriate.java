package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.TwoChoiceVote;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.turn.TurnMod;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTargets;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * @author JRHerlehy, TheElk801
 */
public final class Expropriate extends CardImpl {

    public Expropriate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{7}{U}{U}");

        // <i>Council's dilemma</i> &mdash; Starting with you, each player votes for time or money. For each time vote,
        // take an extra turn after this one. For each money vote, choose a permanent owned by the voter and gain control of it. Exile Expropriate
        this.getSpellAbility().addEffect(new ExpropriateEffect());
        this.getSpellAbility().addEffect(new ExileSpellEffect());
    }

    private Expropriate(final Expropriate card) {
        super(card);
    }

    @Override
    public Expropriate copy() {
        return new Expropriate(this);
    }
}

class ExpropriateEffect extends OneShotEffect {

    ExpropriateEffect() {
        super(Outcome.Benefit);
        staticText = "<i>Council's dilemma</i> &mdash; Starting with you, each player votes for time or money. " +
                "For each time vote, take an extra turn after this one. For each money vote, " +
                "choose a permanent owned by the voter and gain control of it";
    }

    private ExpropriateEffect(final ExpropriateEffect effect) {
        super(effect);
    }

    @Override
    public ExpropriateEffect copy() {
        return new ExpropriateEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        // Outcome.Detriment - AI will gain control all the time (Money choice)
        // TODO: add AI hint logic in the choice method, see Tyrant's Choice as example
        TwoChoiceVote vote = new TwoChoiceVote("Time (extra turn)", "Money (gain control)", Outcome.Detriment);
        vote.doVotes(source, game);

        // extra turn
        int timeCount = vote.getVoteCount(true);
        for (int i = 0; i < timeCount; i++) {
            game.getState().getTurnMods().add(new TurnMod(source.getControllerId()).withExtraTurn());
        }

        // gain control
        if (vote.getVoteCount(false) < 1) {
            return true;
        }
        List<Permanent> toSteal = new ArrayList<>();
        for (UUID playerId : vote.getVotedFor(false)) {
            int moneyCount = vote.getVotes(playerId).stream().mapToInt(x -> x ? 0 : 1).sum();
            FilterPermanent filter = new FilterPermanent();
            filter.add(new ControllerIdPredicate(playerId));
            moneyCount = Math.min(game.getBattlefield().count(
                    filter, source.getControllerId(), source, game
            ), moneyCount);
            if (moneyCount == 0) {
                continue;
            }
            TargetPermanent target = new TargetPermanent(moneyCount, filter);
            target.setNotTarget(true);
            player.choose(Outcome.GainControl, target, source, game);
            target.getTargets()
                    .stream()
                    .map(game::getPermanent)
                    .filter(Objects::nonNull)
                    .forEach(toSteal::add);
        }
        game.addEffect(new GainControlTargetEffect(
                Duration.Custom, true, source.getControllerId()
        ).setTargetPointer(new FixedTargets(toSteal, game)), source);
        return true;
    }
}
