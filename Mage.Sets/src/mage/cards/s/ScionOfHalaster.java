package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.LookLibraryControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.watchers.common.CardsDrawnThisTurnWatcher;

import java.util.UUID;

/**
 * @author Rjayz
 */
public final class ScionOfHalaster extends CardImpl {

    public ScionOfHalaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.BACKGROUND);

        // Commander creatures you own have "The first time you would draw a card each turn, instead look at the top two cards of your library. Put one of them into your graveyard and the other back on top of your library. Then draw a card."
        this.addAbility(new SimpleStaticAbility(new GainAbilityAllEffect(
               new SimpleStaticAbility(new ScionOfHalasterReplacementEffect()),
                Duration.WhileOnBattlefield, StaticFilters.FILTER_CREATURES_OWNED_COMMANDER
        )), new CardsDrawnThisTurnWatcher());
    }

    private ScionOfHalaster(final ScionOfHalaster card) {
        super(card);
    }

    @Override
    public ScionOfHalaster copy() {
        return new ScionOfHalaster(this);
    }
}

class ScionOfHalasterReplacementEffect extends ReplacementEffectImpl {

    ScionOfHalasterReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Neutral);
        staticText = "The first time you would draw a card each turn, instead look at the top two cards of your library. Put one of them into your graveyard and the other back on top of your library. Then draw a card";
    }

    ScionOfHalasterReplacementEffect(final mage.cards.s.ScionOfHalasterReplacementEffect effect) {
        super(effect);
    }

    @Override
    public mage.cards.s.ScionOfHalasterReplacementEffect copy() {
        return new mage.cards.s.ScionOfHalasterReplacementEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        new LookLibraryAndPickControllerEffect(2, 1, LookLibraryControllerEffect.PutCards.GRAVEYARD, LookLibraryControllerEffect.PutCards.TOP_ANY).apply(game, source);
        Player you = game.getPlayer(event.getPlayerId());
        if (you != null) {
            you.drawCards(1, source, game, event);
        }
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DRAW_CARD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!event.getPlayerId().equals(source.getControllerId())) {
            return false;
        }
        CardsDrawnThisTurnWatcher watcher = game.getState().getWatcher(CardsDrawnThisTurnWatcher.class);
        return watcher != null && watcher.getCardsDrawnThisTurn(event.getPlayerId()) == 0;
    }
}