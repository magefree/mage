package mage.cards.k;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.*;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.token.TreasureToken;
import mage.players.Player;
import mage.target.common.TargetOpponent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class KorvoldAndTheNobleThief extends CardImpl {

    public KorvoldAndTheNobleThief(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{R}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I, II -- Create a Treasure token.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_II,
                new CreateTokenEffect(new TreasureToken())
        );

        // III -- Exile the top three cards of target opponent's library. You may play those cards this turn.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_III, SagaChapter.CHAPTER_III,
                new KorvoldAndTheNobleThiefEffect(),
                new TargetOpponent()
        );

        this.addAbility(sagaAbility);
    }

    private KorvoldAndTheNobleThief(final KorvoldAndTheNobleThief card) {
        super(card);
    }

    @Override
    public KorvoldAndTheNobleThief copy() {
        return new KorvoldAndTheNobleThief(this);
    }
}

class KorvoldAndTheNobleThiefEffect extends OneShotEffect {

    KorvoldAndTheNobleThiefEffect() {
        super(Outcome.Benefit);
        staticText = "exile the top three cards of target opponent's library. You may play those cards this turn";
    }

    private KorvoldAndTheNobleThiefEffect(final KorvoldAndTheNobleThiefEffect effect) {
        super(effect);
    }

    @Override
    public KorvoldAndTheNobleThiefEffect copy() {
        return new KorvoldAndTheNobleThiefEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(source.getFirstTarget());
        MageObject sourceObject = source.getSourceObject(game);
        if (player == null || opponent == null || sourceObject == null) {
            return false;
        }

        Cards cards = new CardsImpl(opponent.getLibrary().getTopCards(game, 3));

        UUID exileId = CardUtil.getExileZoneId(
                player.getId().toString()
                        + "-" + game.getState().getTurnNum()
                        + "-" + sourceObject.getIdName(),
                game
        );
        String exileName = sourceObject.getIdName()
                + " — T" + game.getState().getTurnNum()
                + " — Player: " + player.getName();
        game.getExile().createZone(exileId, exileName).setCleanupOnEndTurn(true);

        player.moveCardsToExile(cards.getCards(game), source, game, true, exileId, exileName);
        cards.retainZone(Zone.EXILED, game);

        for (Card card : cards.getCards(game)) {
            CardUtil.makeCardPlayable(game, source, card, Duration.EndOfTurn, false);
        }
        return true;
    }
}
