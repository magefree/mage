package mage.cards.i;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.game.Game;
import mage.game.permanent.token.MinionToken2;
import mage.game.permanent.token.Token;
import mage.players.Player;

import java.util.Objects;
import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class InfernalGenesis extends CardImpl {

    public InfernalGenesis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{B}{B}");

        // At the beginning of each player's upkeep, that player puts the top card of their library into their graveyard.
        // Then they create X 1/1 black Minion creature tokens, where X is that card's converted mana cost.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new InfernalGenesisEffect(), TargetController.ANY, false));
    }

    private InfernalGenesis(final InfernalGenesis card) {
        super(card);
    }

    @Override
    public InfernalGenesis copy() {
        return new InfernalGenesis(this);
    }
}

class InfernalGenesisEffect extends OneShotEffect {

    private static final Token token = new MinionToken2();

    InfernalGenesisEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "that player mills a card. Then they create X 1/1 black Minion creature tokens, " +
                "where X is the milled card's mana value.";
    }

    private InfernalGenesisEffect(final InfernalGenesisEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        int totalCMC = player
                .millCards(1, source, game)
                .getCards(game)
                .stream()
                .filter(Objects::nonNull)
                .mapToInt(MageObject::getManaValue)
                .sum();
        if (totalCMC > 0) {
            token.putOntoBattlefield(totalCMC, game, source, player.getId());
        }
        return true;
    }

    @Override
    public InfernalGenesisEffect copy() {
        return new InfernalGenesisEffect(this);
    }
}
