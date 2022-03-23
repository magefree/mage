package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.choices.VoteHandler;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author emerald000, TheElk801
 */
public final class CouncilsJudgment extends CardImpl {

    public CouncilsJudgment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{W}{W}");

        // Will of the council - Starting with you, each player votes for a nonland permanent you don't control. Exile each permanent with the most votes or tied for most votes.
        this.getSpellAbility().addEffect(new CouncilsJudgmentEffect());
    }

    private CouncilsJudgment(final CouncilsJudgment card) {
        super(card);
    }

    @Override
    public CouncilsJudgment copy() {
        return new CouncilsJudgment(this);
    }
}

class CouncilsJudgmentEffect extends OneShotEffect {

    CouncilsJudgmentEffect() {
        super(Outcome.Exile);
        this.staticText = "<i>Will of the council</i> &mdash; Starting with you, each player votes for a " +
                "nonland permanent you don't control. Exile each permanent with the most votes or tied for most votes";
    }

    private CouncilsJudgmentEffect(final CouncilsJudgmentEffect effect) {
        super(effect);
    }

    @Override
    public CouncilsJudgmentEffect copy() {
        return new CouncilsJudgmentEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        CouncilsJudgmentVote vote = new CouncilsJudgmentVote(player);
        vote.doVotes(source, game);

        Cards cards = new CardsImpl();
        vote.getMostVoted().stream().forEach(cards::add);
        return player.moveCards(cards, Zone.EXILED, source, game);
    }
}

class CouncilsJudgmentVote extends VoteHandler<Permanent> {

    private final FilterPermanent filter;

    CouncilsJudgmentVote(Player controller) {
        this.filter = new FilterNonlandPermanent("nonland permanent not controlled by " + controller.getName());
        this.filter.add(Predicates.not(new ControllerIdPredicate(controller.getId())));
    }

    @Override
    protected Set<Permanent> getPossibleVotes(Ability source, Game game) {
        // too much permanentns on battlefield, so no need to show full list here
        return new LinkedHashSet<>();
    }

    @Override
    public Permanent playerChoose(String voteInfo, Player player, Player decidingPlayer, Ability source, Game game) {
        if (game.getBattlefield().count(filter, source.getControllerId(), source, game) < 1) {
            return null;
        }
        TargetPermanent target = new TargetPermanent(1, filter);
        target.withChooseHint(voteInfo + " (to exile)");
        target.setNotTarget(true);
        decidingPlayer.choose(Outcome.Exile, target, source, game);
        return game.getPermanent(target.getFirstTarget());
    }

    @Override
    protected String voteName(Permanent vote) {
        return vote.getIdName();
    }
}
