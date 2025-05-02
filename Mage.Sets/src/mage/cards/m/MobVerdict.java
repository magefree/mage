package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.VoteHandler;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.FilterPlayer;
import mage.filter.StaticFilters;
import mage.game.Controllable;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class MobVerdict extends CardImpl {

    public MobVerdict(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}{R}");

        // Secret council -- Each player secretly votes for another player, then those votes are revealed. For each vote an opponent received, Mob Verdict deals 2 damage to that player and each creature that player controls. For each vote you received, draw a card.
        this.getSpellAbility().addEffect(new MobVerdictEffect());
        this.getSpellAbility().setAbilityWord(AbilityWord.SECRET_COUNCIL);
    }

    private MobVerdict(final MobVerdict card) {
        super(card);
    }

    @Override
    public MobVerdict copy() {
        return new MobVerdict(this);
    }
}

class MobVerdictEffect extends OneShotEffect {

    MobVerdictEffect() {
        super(Outcome.Benefit);
        staticText = "each player secretly votes for another player, then those votes are revealed. " +
                "For each vote an opponent received, {this} deals 2 damage to that " +
                "player and each creature that player controls. For each vote you received, draw a card";
    }

    private MobVerdictEffect(final MobVerdictEffect effect) {
        super(effect);
    }

    @Override
    public MobVerdictEffect copy() {
        return new MobVerdictEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MobVerdictVote vote = new MobVerdictVote();
        vote.doVotes(source, game);
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            Player opponent = game.getPlayer(opponentId);
            if (opponent == null) {
                continue;
            }
            int count = vote.getVoteCount(opponent);
            if (count < 1) {
                continue;
            }
            opponent.damage(2 * count, source, game);
            for (Permanent permanent : game.getBattlefield().getActivePermanents(
                    StaticFilters.FILTER_CONTROLLED_CREATURE, opponentId, source, game
            )) {
                permanent.damage(2 * count, source, game);
            }
        }
        Optional.ofNullable(source)
                .map(Controllable::getControllerId)
                .map(game::getPlayer)
                .ifPresent(player -> player.drawCards(vote.getVoteCount(player), source, game));
        return true;
    }
}

class MobVerdictVote extends VoteHandler<Player> {

    private static final FilterPlayer filter = new FilterPlayer("another player");

    static {
        filter.add(TargetController.NOT_YOU.getPlayerPredicate());
    }

    MobVerdictVote() {
        super();
        this.secret = true;
    }

    @Override
    protected Set<Player> getPossibleVotes(Ability source, Game game) {
        return game
                .getState()
                .getPlayersInRange(source.getControllerId(), game)
                .stream()
                .map(game::getPlayer)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    @Override
    protected Player playerChoose(String voteInfo, Player player, Player decidingPlayer, Ability source, Game game) {
        TargetPlayer target = new TargetPlayer(filter);
        target.withNotTarget(true);
        target.withChooseHint("to vote for");
        decidingPlayer.choose(Outcome.Benefit, target, source, game);
        return game.getPlayer(target.getFirstTarget());
    }

    @Override
    protected String voteName(Player vote) {
        return vote.getName();
    }
}
