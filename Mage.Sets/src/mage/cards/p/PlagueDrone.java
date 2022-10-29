package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class PlagueDrone extends CardImpl {

    public PlagueDrone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");
        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Rot Fly — If an opponent would gain life, that player loses that much life instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PlagueDroneReplacementEffect()).withFlavorWord("Rot Fly"));
    }

    private PlagueDrone(final PlagueDrone card) {
        super(card);
    }

    @Override
    public PlagueDrone copy() {
        return new PlagueDrone(this);
    }
}

class PlagueDroneReplacementEffect extends ReplacementEffectImpl {

    public PlagueDroneReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If an opponent would gain life, that player loses that much life instead";
    }

    public PlagueDroneReplacementEffect(final PlagueDroneReplacementEffect effect) {
        super(effect);
    }

    @Override
    public PlagueDroneReplacementEffect copy() {
        return new PlagueDroneReplacementEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.GAIN_LIFE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            return controller.hasOpponent(event.getPlayerId(), game);
        }
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player opponent = game.getPlayer(event.getPlayerId());
        if (opponent != null) {
            opponent.loseLife(event.getAmount(), game, source, false);
        }
        return true;
    }
}
