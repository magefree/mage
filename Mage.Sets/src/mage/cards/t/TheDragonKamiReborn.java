package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.DiesThisOrAnotherTriggeredAbility;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.Effects;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileSagaAndReturnTransformedEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.*;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInLibrary;
import mage.util.CardUtil;

import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheDragonKamiReborn extends TransformingDoubleFacedCard {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.DRAGON);

    public TheDragonKamiReborn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.ENCHANTMENT}, new SubType[]{SubType.SAGA}, "{2}{G}",
                "Dragon-Kami's Egg",
                new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, new SubType[]{SubType.EGG}, "G"
        );

        // The Dragon-Kami Reborn
        // (As this Saga enters and after your draw step, add a lore counter.)
        SagaAbility sagaAbility = new SagaAbility(this.getLeftHalfCard());

        // I, II — You gain 2 life. Look at the top three cards of your library. Exile one of them face down with a hatching counter on it, then put the rest on the bottom of your library in any order.
        sagaAbility.addChapterEffect(
                this.getLeftHalfCard(), SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_II,
                new Effects(new GainLifeEffect(2), new TheDragonKamiRebornEffect())
        );

        // III — Exile this Saga, then return it to the battlefield transformed under your control.
        sagaAbility.addChapterEffect(this.getLeftHalfCard(), SagaChapter.CHAPTER_III, new ExileSagaAndReturnTransformedEffect());

        this.getLeftHalfCard().addAbility(sagaAbility);

        // Dragon-Kami's Egg
        this.getRightHalfCard().setPT(0, 1);

        // Whenever Dragon-Kami's Egg or a Dragon you control dies, you may cast a creature spell from among cards you own in exile with hatching counters on them without paying its mana cost.
        this.getRightHalfCard().addAbility(new DiesThisOrAnotherTriggeredAbility(
                new DragonKamisEggEffect(), false, filter
        ).setTriggerPhrase("Whenever {this} or a Dragon you control dies, "));
    }

    private TheDragonKamiReborn(final TheDragonKamiReborn card) {
        super(card);
    }

    @Override
    public TheDragonKamiReborn copy() {
        return new TheDragonKamiReborn(this);
    }
}

class TheDragonKamiRebornEffect extends OneShotEffect {

    TheDragonKamiRebornEffect() {
        super(Outcome.Benefit);
        staticText = "Look at the top three cards of your library. Exile one of them face down " +
                "with a hatching counter on it, then put the rest on the bottom of your library in any order";
    }

    private TheDragonKamiRebornEffect(final TheDragonKamiRebornEffect effect) {
        super(effect);
    }

    @Override
    public TheDragonKamiRebornEffect copy() {
        return new TheDragonKamiRebornEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, 3));
        if (cards.isEmpty()) {
            return false;
        }
        TargetCard target = new TargetCardInLibrary();
        player.choose(outcome, cards, target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        if (card != null) {
            player.moveCards(card, Zone.EXILED, source, game);
            card.setFaceDown(true, game);
            card.addCounters(CounterType.HATCHLING.createInstance(), source, game);
        }
        cards.retainZone(Zone.LIBRARY, game);
        player.putCardsOnBottomOfLibrary(cards, game, source, true);
        return true;
    }
}

class DragonKamisEggEffect extends OneShotEffect {

    DragonKamisEggEffect() {
        super(Outcome.Benefit);
        staticText = "you may cast a creature spell from among cards you own in exile " +
                "with hatching counters on them without paying its mana cost";
    }

    private DragonKamisEggEffect(final DragonKamisEggEffect effect) {
        super(effect);
    }

    @Override
    public DragonKamisEggEffect copy() {
        return new DragonKamisEggEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl();
        game.getExile()
                .getCardsOwned(game, player.getId())
                .stream()
                .filter(Objects::nonNull)
                .filter(card -> card.getCounters(game).containsKey(CounterType.HATCHLING))
                .forEach(cards::add);
        return !cards.isEmpty() && CardUtil.castSpellWithAttributesForFree(
                player, source, game, cards, StaticFilters.FILTER_CARD_CREATURE
        );
    }
}
