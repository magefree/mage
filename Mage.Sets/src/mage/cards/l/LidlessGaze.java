package mage.cards.l;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class LidlessGaze extends CardImpl {

    public LidlessGaze(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}{R}");

        // Exile the top card of each player's library. Until the end of your next turn, you may play those cards, and mana of any type can be spent to cast them.
        this.getSpellAbility().addEffect(new LidlessGazeEffect());

        // Flashback {2}{B}{R}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{2}{B}{R}")));
    }

    private LidlessGaze(final LidlessGaze card) {
        super(card);
    }

    @Override
    public LidlessGaze copy() {
        return new LidlessGaze(this);
    }
}

class LidlessGazeEffect extends OneShotEffect {

    LidlessGazeEffect() {
        super(Outcome.Benefit);
        staticText = "Exile the top card of each player's library. Until the end of your next turn, you "
                + "may play those cards, and mana of any type can be spent to cast them.";
    }

    private LidlessGazeEffect(final LidlessGazeEffect effect) {
        super(effect);
    }

    @Override
    public LidlessGazeEffect copy() {
        return new LidlessGazeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        UUID exileId = CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter());
        MageObject sourceObject = source.getSourceObject(game);
        String exileName = sourceObject == null ? null : sourceObject.getIdName();

        Cards cards = new CardsImpl();
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                Card card = player.getLibrary().removeFromTop(game);
                if (card != null) {
                    controller.moveCardsToExile(card, source, game, true, exileId, exileName);
                    cards.add(card);
                }
            }
        }

        cards.retainZone(Zone.EXILED, game);
        for (Card card : cards.getCards(game)) {
            CardUtil.makeCardPlayable(game, source, card, Duration.EndOfTurn,
                    true, controller.getId(), null);
        }

        return true;
    }
}