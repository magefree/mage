
package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 *
 * @author muz
 */
public final class HexMagic extends CardImpl {

    public HexMagic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}");
        this.subtype.add(SubType.ARCANE);

        // Exile all the cards from your hand, then draw that many cards. Until the end of your next turn, you may play cards exiled this way.
        this.getSpellAbility().addEffect(new HexMagicEffect());
    }

    private HexMagic(final HexMagic card) {
        super(card);
    }

    @Override
    public HexMagic copy() {
        return new HexMagic(this);
    }
}

class HexMagicEffect extends OneShotEffect {

    HexMagicEffect() {
        super(Outcome.Benefit);
        staticText = "Exile all the cards from your hand, then draw that many cards. "
            + "Until the end of your next turn, you may play cards exiled this way.";
    }

    private HexMagicEffect(final HexMagicEffect effect) {
        super(effect);
    }

    @Override
    public HexMagicEffect copy() {
        return new HexMagicEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Cards cards = new CardsImpl(controller.getHand());
        int count = cards.size();
        controller.moveCardsToExile(
                cards.getCards(game), source, game, true,
                CardUtil.getExileZoneId(game, source),
                CardUtil.getSourceName(game, source)
        );
        controller.drawCards(count, source, game);
        cards.retainZone(Zone.EXILED, game);
        for (Card card : cards.getCards(game)) {
            CardUtil.makeCardPlayable(game, source, card, false, Duration.UntilEndOfYourNextTurn, false);
        }
        return true;
    }
}
