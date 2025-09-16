package mage.cards.t;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.ExileGraveyardAllTargetPlayerEffect;
import mage.cards.*;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.players.PlayerList;
import mage.target.Target;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetDiscard;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 *
 * @author Jmlundeen
 */
public final class TheDeathOfGwenStacy extends CardImpl {

    public TheDeathOfGwenStacy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}");
        
        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I -- Destroy target creature.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_I, new DestroyTargetEffect(), new TargetCreaturePermanent());

        // II -- Each player may discard a card. Each player who doesn't loses 3 life.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_II, new TheDeathOfGwenStacyEffect());

        // III -- Exile any number of target players' graveyards.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_III, new ExileGraveyardAllTargetPlayerEffect(),
                new TargetPlayer(0, Integer.MAX_VALUE, false));

        this.addAbility(sagaAbility);
    }

    private TheDeathOfGwenStacy(final TheDeathOfGwenStacy card) {
        super(card);
    }

    @Override
    public TheDeathOfGwenStacy copy() {
        return new TheDeathOfGwenStacy(this);
    }
}

class TheDeathOfGwenStacyEffect extends OneShotEffect {

    TheDeathOfGwenStacyEffect() {
        super(Outcome.Neutral);
        this.staticText = "each player may discard a card. Each player who doesn't loses 3 life";
    }

    private TheDeathOfGwenStacyEffect(final TheDeathOfGwenStacyEffect effect) {
        super(effect);
    }

    @Override
    public TheDeathOfGwenStacyEffect copy() {
        return new TheDeathOfGwenStacyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller == null || sourceObject == null) {
            return false;
        }
        // Store for each player the cards to discard, that's important because all discard shall happen at the same time
        Map<UUID, Cards> cardsToDiscard = new HashMap<>();
        PlayerList playersInRange = game.getState().getPlayersInRange(controller.getId(), game);

        // choose cards to discard
        for (UUID playerId : playersInRange) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                Target target = new TargetDiscard(0, 1, new FilterCard(), playerId)
                        .withChooseHint("Choose a card to discard or lose 3 life");
                player.chooseTarget(outcome, target, source, game);
                Cards cards = new CardsImpl(target.getTargets());
                cardsToDiscard.put(playerId, cards);
            }
        }
        // discard all chosen cards
        for (UUID playerId : playersInRange) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                Cards cardsPlayer = cardsToDiscard.get(playerId);
                if (cardsPlayer != null && !cardsPlayer.isEmpty()) {
                    for (UUID cardId : cardsPlayer) {
                        Card card = game.getCard(cardId);
                        player.discard(card, false, source, game);
                    }
                } else {
                    player.loseLife(3, game, source, false);
                }
            }
        }
        return true;
    }
}
