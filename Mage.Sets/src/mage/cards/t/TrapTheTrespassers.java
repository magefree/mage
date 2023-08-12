package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.VoteHandler;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class TrapTheTrespassers extends CardImpl {

    public TrapTheTrespassers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}");

        // Secret council -- Each player secretly votes for a creature you don't control, then those votes are revealed. For each creature with one or more votes, put that many stun counters on it, then tap it.
        this.getSpellAbility().addEffect(new TrapTheTrespassersEffect());
        this.getSpellAbility().setAbilityWord(AbilityWord.SECRET_COUNCIL);
    }

    private TrapTheTrespassers(final TrapTheTrespassers card) {
        super(card);
    }

    @Override
    public TrapTheTrespassers copy() {
        return new TrapTheTrespassers(this);
    }
}

class TrapTheTrespassersEffect extends OneShotEffect {

    TrapTheTrespassersEffect() {
        super(Outcome.Benefit);
        staticText = "each player secretly votes for a creature you don't control, then those votes are revealed. " +
                "For each creature with one or more votes, put that many stun counters on it, then tap it";
    }

    private TrapTheTrespassersEffect(final TrapTheTrespassersEffect effect) {
        super(effect);
    }

    @Override
    public TrapTheTrespassersEffect copy() {
        return new TrapTheTrespassersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (!game.getBattlefield().contains(StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL, source, game, 1)) {
            return false;
        }
        TrapTheTrespassersVote vote = new TrapTheTrespassersVote(source, game);
        vote.doVotes(source, game);
        for (Map.Entry<Permanent, Integer> entry : vote.getVotesPerCreature(game).entrySet()) {
            entry.getKey().addCounters(CounterType.STUN.createInstance(entry.getValue()), source, game);
            entry.getKey().tap(source, game);
        }
        return true;
    }
}

class TrapTheTrespassersVote extends VoteHandler<Permanent> {

    private final FilterPermanent filter;

    TrapTheTrespassersVote(Ability source, Game game) {
        this.filter = new FilterCreaturePermanent(
                "creature not controlled by " + game.getPlayer(source.getControllerId()).getName()
        );
        this.filter.add(Predicates.not(new ControllerIdPredicate(source.getControllerId())));
        this.secret = true;
    }

    @Override
    protected Set<Permanent> getPossibleVotes(Ability source, Game game) {
        return new HashSet<>();
    }

    @Override
    protected Permanent playerChoose(String voteInfo, Player player, Player decidingPlayer, Ability source, Game game) {
        TargetPermanent target = new TargetPermanent(filter);
        target.setNotTarget(true);
        decidingPlayer.choose(Outcome.UnboostCreature, target, source, game);
        return game.getPermanent(target.getFirstTarget());
    }

    @Override
    protected String voteName(Permanent vote) {
        return vote.getIdName();
    }

    Map<Permanent, Integer> getVotesPerCreature(Game game) {
        return playerMap
                .values()
                .stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toMap(Function.identity(), x -> 1, Integer::sum));
    }
}