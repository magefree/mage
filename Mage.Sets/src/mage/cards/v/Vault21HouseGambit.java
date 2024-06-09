package mage.cards.v;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.Effects;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.discard.DiscardControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.token.TreasureToken;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInHand;

import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class Vault21HouseGambit extends CardImpl {

    public Vault21HouseGambit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I, II -- Discard a card, then draw a card.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_II,
                new Effects(
                        new DiscardControllerEffect(1),
                        new DrawCardSourceControllerEffect(1)
                                .concatBy(", then")
                )
        );

        // III -- Reveal up to five nonland cards from your hand. For each of those cards that has the same mana value as another card revealed this way, create a Treasure token.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_III, new Vault21HouseGambitEffect());
        this.addAbility(sagaAbility);
    }

    private Vault21HouseGambit(final Vault21HouseGambit card) {
        super(card);
    }

    @Override
    public Vault21HouseGambit copy() {
        return new Vault21HouseGambit(this);
    }
}

class Vault21HouseGambitEffect extends OneShotEffect {

    Vault21HouseGambitEffect() {
        super(Outcome.Benefit);
        staticText = "reveal up to five nonland cards from your hand. For each of those cards " +
                "that has the same mana value as another card revealed this way, create a Treasure token";
    }

    private Vault21HouseGambitEffect(final Vault21HouseGambitEffect effect) {
        super(effect);
    }

    @Override
    public Vault21HouseGambitEffect copy() {
        return new Vault21HouseGambitEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetCard target = new TargetCardInHand(0, 5, StaticFilters.FILTER_CARD_NON_LAND);
        player.choose(outcome, player.getHand(), target, source, game);
        Cards cards = new CardsImpl(target.getTargets());
        if (cards.isEmpty()) {
            return false;
        }
        player.revealCards(source, cards, game);
        int count = cards
                .getCards(game)
                .stream()
                .map(MageObject::getManaValue)
                .collect(Collectors.toMap(Function.identity(), x -> 1, Integer::sum))
                .values()
                .stream()
                .mapToInt(x -> x > 1 ? x : 0)
                .sum();
        return count > 0 && new TreasureToken().putOntoBattlefield(count, game, source);
    }
}
