
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.ShuffleHandGraveyardAllEffect;
import mage.abilities.effects.common.continuous.CantCastMoreThanOneSpellEffect;
import mage.abilities.effects.common.continuous.MaximumHandSizeControllerEffect;
import mage.abilities.effects.common.continuous.MaximumHandSizeControllerEffect.HandSizeModification;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

/**
 *
 * @author L_J
 */
public final class MineMineMine extends CardImpl {

    public MineMineMine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{G}{G}");

        // When Mine, Mine, Mine enters the battlefield, each player puts their library into their hand.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new MineMineMineDrawEffect()));

        // Players have no maximum hand size and don't lose the game for drawing from an empty library.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new MaximumHandSizeControllerEffect(Integer.MAX_VALUE, Duration.WhileOnBattlefield, HandSizeModification.SET, TargetController.ANY)
                        .setText("Players have no maximum hand size and don't lose the game for drawing from an empty library")));
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new MineMineMineDontLoseEffect()));

        // Each player can't cast more than one spell each turn.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantCastMoreThanOneSpellEffect(TargetController.ANY)));

        // When Mine, Mine, Mine leaves the battlefield, each player shuffles their hand and graveyard into their library.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new ShuffleHandGraveyardAllEffect(), false));
    }

    private MineMineMine(final MineMineMine card) {
        super(card);
    }

    @Override
    public MineMineMine copy() {
        return new MineMineMine(this);
    }
}

class MineMineMineDrawEffect extends OneShotEffect {

    MineMineMineDrawEffect() {
        super(Outcome.DrawCard);
        this.staticText = "each player puts their library into their hand";
    }

    MineMineMineDrawEffect(final MineMineMineDrawEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                CardsImpl libraryCards = new CardsImpl();
                libraryCards.addAllCards(player.getLibrary().getCards(game));
                player.moveCards(libraryCards, Zone.HAND, source, game);
            }
        }
        return true;
    }

    @Override
    public MineMineMineDrawEffect copy() {
        return new MineMineMineDrawEffect(this);
    }
}

class MineMineMineDontLoseEffect extends ReplacementEffectImpl {

    MineMineMineDontLoseEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Neutral);
    }

    MineMineMineDontLoseEffect(final MineMineMineDontLoseEffect effect) {
        super(effect);
    }

    @Override
    public MineMineMineDontLoseEffect copy() {
        return new MineMineMineDontLoseEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DRAW_CARD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Player player = game.getPlayer(event.getPlayerId());
        if (player != null && player.getLibrary().getCards(game).isEmpty()) {
            return true;
        }
        return false;
    }
}
