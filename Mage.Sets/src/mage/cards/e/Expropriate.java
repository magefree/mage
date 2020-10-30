package mage.cards.e;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.CouncilsDilemmaVoteEffect;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.predicate.other.OwnerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.turn.TurnMod;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;

/**
 * @author JRHerlehy
 */
public final class Expropriate extends CardImpl {

    public Expropriate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{7}{U}{U}");

        // <i>Council's dilemma</i> &mdash; Starting with you, each player votes for time or money. For each time vote,
        // take an extra turn after this one. For each money vote, choose a permanent owned by the voter and gain control of it. Exile Expropriate
        this.getSpellAbility().addEffect(new ExpropriateDilemmaEffect());
        this.getSpellAbility().addEffect(ExileSpellEffect.getInstance());
    }

    public Expropriate(final Expropriate card) {
        super(card);
    }

    @Override
    public Expropriate copy() {
        return new Expropriate(this);
    }
}

class ExpropriateDilemmaEffect extends CouncilsDilemmaVoteEffect {

    private ArrayList<UUID> choiceTwoVoters = new ArrayList<>();

    public ExpropriateDilemmaEffect() {
        super(Outcome.Benefit);
        this.staticText = "<i>Council's dilemma</i> &mdash; Starting with you, each player votes for time or money. For each time vote, take an extra turn after this one. For each money vote, choose a permanent owned by the voter and gain control of it";
    }

    public ExpropriateDilemmaEffect(final ExpropriateDilemmaEffect effect) {
        super(effect);
        this.choiceTwoVoters.addAll(effect.choiceTwoVoters);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());

        //If not controller, exit out here and do not vote.
        if (controller == null) {
            return false;
        }

        this.vote("time", "money", controller, game, source);

        //Time Votes
        if (voteOneCount > 0) {
            this.turnsForTimeVote(voteOneCount, controller, game, source);
        }

        //Money Votes
        if (voteTwoCount > 0) {
            this.controlForMoneyVote(controller, game, source);
        }

        return true;
    }

    private void turnsForTimeVote(int timeCount, Player controller, Game game, Ability source) {
        if (timeCount == 1) {
            game.informPlayers(controller.getName() + " will take an extra turn");
        } else {
            game.informPlayers(controller.getName() + " will take " + timeCount + " extra turns");
        }
        do {
            game.getState().getTurnMods().add(new TurnMod(source.getControllerId(), false));
            timeCount--;
        } while (timeCount > 0);
    }

    private void controlForMoneyVote(Player controller, Game game, Ability source) {
        List<Permanent> chosenCards = new ArrayList<>();

        for (UUID playerId : choiceTwoVoters) {
            FilterPermanent filter = new FilterPermanent("permanent owned by " + game.getPlayer(playerId).getName());
            filter.add(new OwnerIdPredicate(playerId));

            Target target = new TargetPermanent(filter);
            target.setNotTarget(true);

            if (controller != null
                    && controller.chooseTarget(Outcome.GainControl, target, source, game)) {
                Permanent targetPermanent = game.getPermanent(target.getFirstTarget());

                if (targetPermanent != null) {
                    chosenCards.add(targetPermanent);
                }
            }
        }
        if (controller != null) {
            for (Permanent permanent : chosenCards) {
                ContinuousEffect effect = new ExpropriateControlEffect(controller.getId());
                effect.setTargetPointer(new FixedTarget(permanent, game));
                game.addEffect(effect, source);
                game.informPlayers(controller.getName() + " gained control of " + permanent.getIdName() + " owned by " + game.getPlayer(permanent.getOwnerId()).getName());
            }
        }
    }

    @Override
    protected void vote(String choiceOne, String choiceTwo, Player controller, Game game, Ability source) {
        for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                if (player.chooseUse(Outcome.Vote,
                        "Choose " + choiceOne + " or " + choiceTwo + "?",
                        source.getRule(), choiceOne, choiceTwo, source, game)) {
                    voteOneCount++;
                    game.informPlayers(player.getName() + " has voted for " + choiceOne);
                } else {
                    voteTwoCount++;
                    game.informPlayers(player.getName() + " has voted for " + choiceTwo);
                    choiceTwoVoters.add(player.getId());
                }
            }
        }
    }

    @Override
    public ExpropriateDilemmaEffect copy() {
        return new ExpropriateDilemmaEffect(this);
    }

}

class ExpropriateControlEffect extends ContinuousEffectImpl {

    private final UUID controllerId;

    public ExpropriateControlEffect(UUID controllerId) {
        super(Duration.EndOfGame, Layer.ControlChangingEffects_2, SubLayer.NA, Outcome.GainControl);
        this.controllerId = controllerId;
    }

    public ExpropriateControlEffect(final ExpropriateControlEffect effect) {
        super(effect);
        this.controllerId = effect.controllerId;
    }

    @Override
    public ExpropriateControlEffect copy() {
        return new ExpropriateControlEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(targetPointer.getFirst(game, source));
        if (permanent == null || controllerId == null) {
            this.discard();
        } else {
            permanent.changeControllerId(controllerId, game);
        }
        return true;
    }
}
