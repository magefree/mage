package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.FinishVotingTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ModelOfUnity extends CardImpl {

    public ModelOfUnity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // Whenever players finish voting, you and each opponent who voted for a choice you voted for may scry 2.
        this.addAbility(new FinishVotingTriggeredAbility(new ModelOfUnityEffect()));

        // {T}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());
    }

    private ModelOfUnity(final ModelOfUnity card) {
        super(card);
    }

    @Override
    public ModelOfUnity copy() {
        return new ModelOfUnity(this);
    }
}

class ModelOfUnityEffect extends OneShotEffect {

    ModelOfUnityEffect() {
        super(Outcome.Benefit);
        staticText = "you and each opponent who voted for a choice you voted for may scry 2";
    }

    private ModelOfUnityEffect(final ModelOfUnityEffect effect) {
        super(effect);
    }

    @Override
    public ModelOfUnityEffect copy() {
        return new ModelOfUnityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && controller.chooseUse(outcome, "Scry 2?", source, game)) {
            controller.scry(2, source, game);
        }
        Set<UUID> playerIds = (Set<UUID>) getValue("votedAgainst");
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            if (playerIds.contains(opponentId)) {
                continue;
            }
            Player player = game.getPlayer(opponentId);
            if (player != null && player.chooseUse(outcome, "Scry 2?", source, game)) {
                player.scry(2, source, game);
            }
        }
        return true;
    }
}
