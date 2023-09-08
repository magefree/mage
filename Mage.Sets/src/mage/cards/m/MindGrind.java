
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.VariableCost;
import mage.abilities.costs.mana.VariableManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class MindGrind extends CardImpl {

    public MindGrind(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{U}{B}");

        // Each opponent reveals cards from the top of their library until they reveal X land cards, then puts all cards revealed this way into their graveyard. X can't be 0.
        this.getSpellAbility().addEffect(new MindGrindEffect());
        for (VariableCost cost : this.getSpellAbility().getManaCosts().getVariableCosts()) {
            if (cost instanceof VariableManaCost) {
                ((VariableManaCost) cost).setMinX(1);
                break;
            }
        }
    }

    private MindGrind(final MindGrind card) {
        super(card);
    }

    @Override
    public MindGrind copy() {
        return new MindGrind(this);
    }
}

class MindGrindEffect extends OneShotEffect {

    public MindGrindEffect() {
        super(Outcome.Discard);
        this.staticText = "Each opponent reveals cards from the top of their library until they reveal X land cards, then puts all cards revealed this way into their graveyard. X can't be 0";
    }

    private MindGrindEffect(final MindGrindEffect effect) {
        super(effect);
    }

    @Override
    public MindGrindEffect copy() {
        return new MindGrindEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int xValue = source.getManaCostsToPay().getX();
        if (xValue < 1) {
            return false;
        }
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            Player player = game.getPlayer(opponentId);
            if (player == null) {
                continue;
            }
            int landsToReveal = xValue;
            Cards cards = new CardsImpl();
            for (Card card : player.getLibrary().getCards(game)) {
                cards.add(card);
                if (card.isLand(game) && --landsToReveal == 0) {
                    break;
                }
            }
            player.revealCards(source, "from " + player.getName(), cards, game);
            player.moveCards(cards, Zone.GRAVEYARD, source, game);
        }
        return true;
    }
}
