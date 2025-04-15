package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Ogre44Token;
import mage.players.Player;
import mage.players.PlayerList;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.util.CardUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

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

    private static final List<CardType> cardTypes = Arrays.asList(
            CardType.ARTIFACT, CardType.CREATURE, CardType.LAND
    );

    GrenzosRebuttalEffect() {
        super(Outcome.Benefit);
        this.staticText = "Starting with you, each player chooses an artifact, a creature, and a land from among the permanents controlled by the player to their left. " + 
        "Destroy each permanent chosen this way.";
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
        UUID controllerId = source.getControllerId();
        Player controller = game.getPlayer(controllerId);
        if (controller == null || !controller.canRespond()) {
            return false;
        }
        List<Card> chosenPermanents = new ArrayList<>();

        PlayerList playerList = game.getState().getPlayersInRange(controllerId, game, true);

        Player currentPlayer = game.getPlayer(playerList.get());
        Player nextPlayer = null;

        while (!playerList.get().equals(controllerId) || nextPlayer == null) {
            nextPlayer = game.getPlayer(playerList.getNext());
            if (nextPlayer == null) {
                return false;
            }

            if (currentPlayer != null) {
                for (CardType cardType : cardTypes) {
                    String cardTypeText = CardUtil.addArticle(cardType.toString());
                    FilterPermanent filter = new FilterPermanent(
                            cardTypeText + " controlled by " + nextPlayer.getLogName());
                    filter.add(cardType.getPredicate());
                    filter.add(new ControllerIdPredicate(nextPlayer.getId()));

                    Target target = new TargetPermanent(1, 1, filter, true);

                    if (target.canChoose(currentPlayer.getId(), source, game)) {
                        while (currentPlayer.canRespond() && !target.isChosen(game) && target.canChoose(currentPlayer.getId(), source, game)) {
                            currentPlayer.chooseTarget(Outcome.Benefit, target, source, game);
                        }

                        Permanent artifact = game.getPermanent(target.getFirstTarget());
                        if (artifact != null) {
                            chosenPermanents.add(artifact);
                        }
                        target.clearChosen();
                    }
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
}