package mage.cards.v;

import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.dynamicvalue.common.OpponentsCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.VoteHandler;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.HumanSoldierToken;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class Vault11VotersDilemma extends CardImpl {

    public Vault11VotersDilemma(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}{B}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I -- For each opponent, you create a 1/1 white Human Soldier creature token.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_I,
                new CreateTokenEffect(new HumanSoldierToken(), OpponentsCount.instance)
                        .setText("for each opponent, you create a 1/1 white Human Soldier creature token")
        );

        // II, III -- Each player secretly votes for up to one creature, then those votes are revealed. If no creature got votes, each player draws a card. Otherwise, destroy each creature with the most votes or tied for most votes.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_II, SagaChapter.CHAPTER_III, new Vault11VotersDilemmaEffect()
        );
        this.addAbility(sagaAbility);
    }

    private Vault11VotersDilemma(final Vault11VotersDilemma card) {
        super(card);
    }

    @Override
    public Vault11VotersDilemma copy() {
        return new Vault11VotersDilemma(this);
    }
}

class Vault11VotersDilemmaEffect extends OneShotEffect {

    Vault11VotersDilemmaEffect() {
        super(Outcome.Benefit);
        staticText = "each player secretly votes for up to one creature, then those votes are revealed. " +
                "If no creature got votes, each player draws a card. Otherwise, " +
                "destroy each creature with the most votes or tied for most votes";
    }

    private Vault11VotersDilemmaEffect(final Vault11VotersDilemmaEffect effect) {
        super(effect);
    }

    @Override
    public Vault11VotersDilemmaEffect copy() {
        return new Vault11VotersDilemmaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Vault11VotersDilemmaVote vote = new Vault11VotersDilemmaVote();
        vote.doVotes(source, game);
        Set<Permanent> mostVoted = vote.getMostVoted();
        mostVoted.removeIf(Objects::isNull);
        if (!mostVoted.isEmpty()) {
            for (Permanent permanent : mostVoted) {
                permanent.destroy(source, game);
            }
            return true;
        }
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Optional.ofNullable(game.getPlayer(playerId))
                    .map(player -> player.drawCards(1, source, game));
        }
        return true;
    }
}

class Vault11VotersDilemmaVote extends VoteHandler<Permanent> {

    Vault11VotersDilemmaVote() {
        super();
        this.secret = true;
    }

    @Override
    protected Set<Permanent> getPossibleVotes(Ability source, Game game) {
        return game
                .getBattlefield()
                .getActivePermanents(
                        StaticFilters.FILTER_PERMANENT_CREATURE,
                        source.getControllerId(), source, game
                )
                .stream()
                .collect(Collectors.toSet());
    }

    @Override
    protected Permanent playerChoose(String voteInfo, Player player, Player decidingPlayer, Ability source, Game game) {
        TargetPermanent target = new TargetCreaturePermanent(0, 1);
        target.withChooseHint(voteInfo + " (to exile)");
        target.withNotTarget(true);
        decidingPlayer.choose(Outcome.Exile, target, source, game);
        return game.getPermanent(target.getFirstTarget());
    }

    @Override
    protected String voteName(Permanent vote) {
        return vote.getIdName();
    }
}
