package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 *
 * @author Susucr
 */
public final class PhialOfGaladriel extends CardImpl {

    public PhialOfGaladriel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");
        
        this.supertype.add(SuperType.LEGENDARY);

        // If you would draw a card while you have no cards in hand, draw two cards instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PhialOfGaladrielDrawEffect()));
        // If you would gain life while you have 5 or less life, you gain twice that much life instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PhialOfGaladrielLifeEffect()));

        // {T}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());
    }

    private PhialOfGaladriel(final PhialOfGaladriel card) {
        super(card);
    }

    @Override
    public PhialOfGaladriel copy() {
        return new PhialOfGaladriel(this);
    }
}

class PhialOfGaladrielLifeEffect extends ReplacementEffectImpl {

    PhialOfGaladrielLifeEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If you would gain life while you have 5 or less life, " +
            "you gain twice that much life instead";
    }

    private PhialOfGaladrielLifeEffect(final PhialOfGaladrielLifeEffect effect) {
        super(effect);
    }

    @Override
    public PhialOfGaladrielLifeEffect copy() {
        return new PhialOfGaladrielLifeEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(CardUtil.overflowMultiply(event.getAmount(), 2));
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.GAIN_LIFE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        UUID playerId = source.getControllerId();
        if(playerId == null || !event.getPlayerId().equals(playerId)){
            return false;
        }

        Player player = game.getPlayer(playerId);
        if(player == null){
            return false;
        }

        return player.getLife() <= 5;
    }
}

class PhialOfGaladrielDrawEffect extends ReplacementEffectImpl {

    PhialOfGaladrielDrawEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Neutral);
        staticText = "If you would draw a card while you have no cards in hand, draw two cards instead";
    }

    private PhialOfGaladrielDrawEffect(final PhialOfGaladrielDrawEffect effect) {
        super(effect);
    }

    @Override
    public PhialOfGaladrielDrawEffect copy() {
        return new PhialOfGaladrielDrawEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            controller.drawCards(2, source, game, event);
        }
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DRAW_CARD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        UUID playerId = source.getControllerId();
        if(playerId == null || !event.getPlayerId().equals(playerId)){
            return false;
        }

        Player player = game.getPlayer(playerId);
        if(player == null){
            return false;
        }

        return player.getHand().size() == 0;
    }
}
