package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.game.Game;
import mage.game.permanent.token.TreasureToken;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TemptingContract extends CardImpl {

    public TemptingContract(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // At the beginning of your upkeep, each opponent may create a Treasure token. For each opponent who does, you create a Treasure token.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new TemptingContractEffect(), TargetController.YOU, false
        ));
    }

    private TemptingContract(final TemptingContract card) {
        super(card);
    }

    @Override
    public TemptingContract copy() {
        return new TemptingContract(this);
    }
}

class TemptingContractEffect extends OneShotEffect {

    TemptingContractEffect() {
        super(Outcome.Benefit);
        staticText = "each opponent may create a Treasure token. " +
                "For each opponent who does, you create a Treasure token";
    }

    private TemptingContractEffect(final TemptingContractEffect effect) {
        super(effect);
    }

    @Override
    public TemptingContractEffect copy() {
        return new TemptingContractEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        int counter = 0;
        for (UUID playerId : game.getOpponents(source.getControllerId())) {
            Player opponent = game.getPlayer(playerId);
            if (opponent != null && opponent.chooseUse(
                    outcome, "Create a Treasure token?",
                    "If you do, " + controller.getName() + " will create one as well",
                    "Yes", "No", source, game
            ) && new TreasureToken().putOntoBattlefield(1, game, source, opponent.getId())) {
                counter++;
            }
        }
        if (counter > 0) {
            new TreasureToken().putOntoBattlefield(counter, game, source, source.getControllerId());
            return true;
        }
        return false;
    }
}
