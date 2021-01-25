package mage.cards.t;

import java.util.*;

import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.*;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterPermanentCard;
import mage.game.Game;
import mage.game.events.TargetEvent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
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
        SagaAbility sagaAbility = new SagaAbility(this, SagaChapter.CHAPTER_III);

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

    public TheThreeSeasonsEffect() {
        super(Outcome.Neutral);
        staticText = "Choose up to three cards in each graveyard. Their owners shuffle those cards into their libraries";
    }

    private TheThreeSeasonsEffect (final TheThreeSeasonsEffect effect) {
        super(effect);
    }

    @Override
    public TheThreeSeasonsEffect copy() {
        return new TheThreeSeasonsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Map<Player, Cards> playerCardsMap = new LinkedHashMap<>();
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    TheThreeSeasonsTarget target = new TheThreeSeasonsTarget(player);
                    controller.chooseTarget(outcome, target, source, game);
                    playerCardsMap.put(player, new CardsImpl(target.getTargets()));
                }
            }
            for (Map.Entry<Player, Cards> entry : playerCardsMap.entrySet()) {
                entry.getKey().shuffleCardsToLibrary(entry.getValue(), game, source);
            }
            return true;
        }
        return false;
    }
}

class TheThreeSeasonsTarget extends TargetCard {

    private final Player player;

    public TheThreeSeasonsTarget(Player player) {
        super(0, 3, Zone.GRAVEYARD, new FilterCard(
                "cards in " + player.getLogName() + "'s graveyard"
        ));
        this.setNotTarget(true);
        this.player = player;
    }

    private TheThreeSeasonsTarget(final TheThreeSeasonsTarget target) {
        super(target);
        this.player = target.player;
    }

    @Override
    public TheThreeSeasonsTarget copy() {
        return new TheThreeSeasonsTarget(this);
    }

    @Override
    public boolean canTarget(UUID id, Game game) {
        Card card = game.getCard(id);
        return card != null && game.getState().getZone(card.getId()) == Zone.GRAVEYARD
                && player.getGraveyard().contains(id) && filter.match(card, game);
    }

    @Override
    public boolean canTarget(UUID id, Ability source, Game game) {
        return this.canTarget(id, game);
    }

    @Override
    public boolean canTarget(UUID playerId, UUID id, Ability source, Game game) {
        return this.canTarget(id, game);
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceId, UUID sourceControllerId, Game game) {
        Set<UUID> possibleTargets = new HashSet<>();
        for (Card card : player.getGraveyard().getCards(filter, game)) {
            if (sourceId == null || isNotTarget() || !game.replaceEvent(new TargetEvent(card, sourceId, sourceControllerId))) {
                possibleTargets.add(card.getId());
            }
        }
        return possibleTargets;
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Cards cards, Game game) {
        Set<UUID> possibleTargets = new HashSet<>();
        for (Card card : cards.getCards(filter, game)) {
            if (player.getGraveyard().getCards(game).contains(card)) {
                possibleTargets.add(card.getId());
            }
        }
        return possibleTargets;
    }
}
