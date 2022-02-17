package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterPermanentCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInGraveyard;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author weirddan455
 */
public final class TheThreeSeasons extends CardImpl {

    private static final FilterPermanentCard filter
            = new FilterPermanentCard("snow permanent cards from your graveyard");

    static {
        filter.add(SuperType.SNOW.getPredicate());
    }

    public TheThreeSeasons(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{G}{U}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I — Mill three cards.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_I, new MillCardsControllerEffect(3));

        // II — Return up to two target snow permanent cards from your graveyard to your hand.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_II, SagaChapter.CHAPTER_II,
                new ReturnFromGraveyardToHandTargetEffect(), new TargetCardInYourGraveyard(0, 2, filter)
        );

        // III — Choose up to three cards in each graveyard. Their owners shuffle those cards into their libraries.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_III, new TheThreeSeasonsEffect());
        this.addAbility(sagaAbility);
    }

    private TheThreeSeasons(final TheThreeSeasons card) {
        super(card);
    }

    @Override
    public TheThreeSeasons copy() {
        return new TheThreeSeasons(this);
    }
}

class TheThreeSeasonsEffect extends OneShotEffect {

    TheThreeSeasonsEffect() {
        super(Outcome.Neutral);
        staticText = "Choose three cards in each graveyard. Their owners shuffle those cards into their libraries";
    }

    private TheThreeSeasonsEffect(final TheThreeSeasonsEffect effect) {
        super(effect);
    }

    @Override
    public TheThreeSeasonsEffect copy() {
        return new TheThreeSeasonsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Map<Player, Cards> playerCardsMap = new LinkedHashMap<>();
        for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            int cardCount = Math.min(player.getGraveyard().size(), 3);
            if (cardCount < 1) {
                continue;
            }
            TargetCard target = new TargetCardInGraveyard(cardCount, StaticFilters.FILTER_CARD);
            target.setNotTarget(true);
            controller.chooseTarget(outcome, player.getGraveyard(), target, source, game);
            playerCardsMap.put(player, new CardsImpl(target.getTargets()));
        }
        for (Map.Entry<Player, Cards> entry : playerCardsMap.entrySet()) {
            entry.getKey().shuffleCardsToLibrary(entry.getValue(), game, source);
        }
        return true;
    }
}
