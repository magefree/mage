package mage.cards.g;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Ogre44Token;
import mage.players.Player;
import mage.players.PlayerList;
import mage.target.Target;
import mage.target.TargetPermanent;

/**
 *
 * @author androosss
 */
public final class GrenzosRebuttal extends CardImpl {

    public GrenzosRebuttal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{R}{R}");

        // Put a 4/4 red Ogre creature token onto the battlefield. Starting with you, each player chooses an artifact, a creature, and a land from among the permanents controlled by the player to their left. Destroy each permanent chosen this way.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new Ogre44Token()));
        this.getSpellAbility().addEffect(new GrenzosRebuttalEffect());
    }

    private GrenzosRebuttal(final GrenzosRebuttal card) {
        super(card);
    }

    @Override
    public GrenzosRebuttal copy() {
        return new GrenzosRebuttal(this);
    }
}

class GrenzosRebuttalEffect extends OneShotEffect {

    GrenzosRebuttalEffect() {
        super(Outcome.Benefit);
        this.staticText = "Starting with you, each player chooses an artifact, a creature, and a land from among the permanents controlled by the player to their left." + "Destroy each permanent chosen this way.";
    }

    private GrenzosRebuttalEffect(final GrenzosRebuttalEffect effect) {
        super(effect);
    }

    @Override
    public GrenzosRebuttalEffect copy() {
        return new GrenzosRebuttalEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            List<Card> chosenPermanents = new ArrayList<>();

            PlayerList playerList = game.getState().getPlayerList().copy();
            // set playerlist to controller
            while (!playerList.get().equals(source.getControllerId())) {
                playerList.getNext();
            }
            Player currentPlayer = game.getPlayer(playerList.get());
            Player nextPlayer;
            UUID firstNextPlayer = null;
            while (!playerList.getNext().equals(firstNextPlayer)) {
                nextPlayer = game.getPlayer(playerList.get());
                if (nextPlayer == null) {
                    return false;
                }
                // save first next player to check for iteration stop
                if (firstNextPlayer == null) {
                    firstNextPlayer = nextPlayer.getId();
                }
                if (!nextPlayer.canRespond()) {
                    continue;
                }
                // if player is in range they choose a creature to control
                if (currentPlayer != null && game.getState().getPlayersInRange(controller.getId(), game).contains(currentPlayer.getId())) {
                    FilterArtifactPermanent filterArtifact = new FilterArtifactPermanent(
                            "artifact controlled by " + nextPlayer.getLogName());
                    filterArtifact.add(new ControllerIdPredicate(nextPlayer.getId()));
                    FilterCreaturePermanent filterCreature = new FilterCreaturePermanent(
                            "creature controlled by " + nextPlayer.getLogName());
                    filterCreature.add(new ControllerIdPredicate(nextPlayer.getId()));
                    FilterLandPermanent filterLand = new FilterLandPermanent(
                            "land controlled by " + nextPlayer.getLogName());
                    filterLand.add(new ControllerIdPredicate(nextPlayer.getId()));
                    Target targetArtifact = new TargetPermanent(1, 1, filterArtifact, true);
                    Target targetCreature = new TargetPermanent(1, 1, filterCreature, true);
                    Target targetLand = new TargetPermanent(1, 1, filterLand, true);

                    if (targetArtifact.canChoose(currentPlayer.getId(), source, game)) {
                        while (currentPlayer.canRespond() && !targetArtifact.isChosen(game) && targetArtifact.canChoose(currentPlayer.getId(), source, game)) {
                            currentPlayer.chooseTarget(Outcome.Benefit, targetArtifact, source, game);
                        }

                        Permanent artifact = game.getPermanent(targetArtifact.getFirstTarget());
                        if (artifact != null) {
                            chosenPermanents.add(artifact);
                        }
                        targetArtifact.clearChosen();
                    }

                    if (targetCreature.canChoose(currentPlayer.getId(), source, game)) {
                        while (currentPlayer.canRespond() && !targetCreature.isChosen(game) && targetCreature.canChoose(currentPlayer.getId(), source, game)) {
                            currentPlayer.chooseTarget(Outcome.Benefit, targetCreature, source, game);
                        }

                        Permanent creature = game.getPermanent(targetCreature.getFirstTarget());
                        if (creature != null) {
                            chosenPermanents.add(creature);
                        }
                        targetCreature.clearChosen();
                    }

                    if (targetLand.canChoose(currentPlayer.getId(), source, game)) {
                        while (currentPlayer.canRespond() && !targetLand.isChosen(game) && targetLand.canChoose(currentPlayer.getId(), source, game)) {
                            currentPlayer.chooseTarget(Outcome.Benefit, targetLand, source, game);
                        }

                        Permanent land = game.getPermanent(targetLand.getFirstTarget());
                        if (land != null) {
                            chosenPermanents.add(land);
                        }
                        targetLand.clearChosen();
                    }
                }
                currentPlayer = nextPlayer;
            }

            for (Permanent permanent : game.getBattlefield().getAllActivePermanents()) {
                if (!chosenPermanents.contains(permanent)) {
                    permanent.destroy(source, game);
                }
            }

            return true;
        }
        return false;
    }
}