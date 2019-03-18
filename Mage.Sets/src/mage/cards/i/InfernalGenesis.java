
package mage.cards.i;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.token.MinionToken2;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public final class InfernalGenesis extends CardImpl {

    public InfernalGenesis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{B}{B}");

        // At the beginning of each player's upkeep, that player puts the top card of their library into their graveyard.
        // Then he or she creates X 1/1 black Minion creature tokens, where X is that card's converted mana cost.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new InfernalGenesisEffect(), TargetController.ANY, false));
    }

    public InfernalGenesis(final InfernalGenesis card) {
        super(card);
    }

    @Override
    public InfernalGenesis copy() {
        return new InfernalGenesis(this);
    }
}

class InfernalGenesisEffect extends OneShotEffect {

    InfernalGenesisEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "that player puts the top card of their library into their graveyard. " +
                "Then he or she creates X 1/1 black Minion creature tokens, where X is that card's converted mana cost";
    }

    InfernalGenesisEffect(final InfernalGenesisEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (player != null) {
            Card card = player.getLibrary().getFromTop(game);
            if (card != null) {
                if (player.moveCards(card, Zone.GRAVEYARD, source, game)) {
                    int cmc = card.getConvertedManaCost();
                    MinionToken2 token = new MinionToken2();
                    token.putOntoBattlefield(cmc, game, source.getSourceId(), player.getId());
                }
            }
        }
        return true;
    }

    @Override
    public InfernalGenesisEffect copy() {
        return new InfernalGenesisEffect(this);
    }
}
