
package mage.cards.w;

import java.util.UUID;
import mage.MageObject;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.util.GameLog;

/**
 *
 * @author BetaSteward_at_googlemail.com and jeff
 */
public final class WildEvocation extends CardImpl {

    public WildEvocation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{5}{R}");

        //At the beginning of each player's upkeep, that player reveals a card at random from their hand. If it's a land card, the player puts it onto the battlefield. Otherwise, the player casts it without paying its mana cost if able.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new WildEvocationEffect(), TargetController.ANY, false));
    }

    public WildEvocation(final WildEvocation card) {
        super(card);
    }

    @Override
    public WildEvocation copy() {
        return new WildEvocation(this);
    }

}

class WildEvocationEffect extends OneShotEffect {

    public WildEvocationEffect() {
        super(Outcome.PutCardInPlay);
        staticText = "that player reveals a card at random from their hand. "
                + "If it's a land card, that player puts it onto the battlefield. "
                + "Otherwise, the player casts it without paying its mana cost if able";
    }

    public WildEvocationEffect(final WildEvocationEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        MageObject sourceObject = source.getSourceObject(game);
        if (player != null && sourceObject != null) {
            Card card = player.getHand().getRandom(game);
            if (card != null) {
                Cards cards = new CardsImpl();
                cards.add(card);
                player.revealCards(sourceObject.getIdName() + " Turn: " + game.getTurnNum(), cards, game);
                if (card.isLand()) {
                    player.moveCards(card, Zone.BATTLEFIELD, source, game);
                } else if (card.getSpellAbility() != null
                        && card.getSpellAbility().getTargets().canChoose(player.getId(), game)) {
                    player.cast(card.getSpellAbility(), game, true, new MageObjectReference(source.getSourceObject(game), game));
                } else {
                    game.informPlayers(GameLog.getColoredObjectName(card) + " can't be cast now by " + player.getLogName());
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public WildEvocationEffect copy() {
        return new WildEvocationEffect(this);
    }
}
