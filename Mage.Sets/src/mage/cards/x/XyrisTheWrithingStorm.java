package mage.cards.x;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.token.SnakeToken;
import mage.players.Player;
import mage.watchers.common.CardsDrawnDuringDrawStepWatcher;

import java.util.UUID;

/**
 * @author AsterAether
 */
public final class XyrisTheWrithingStorm extends CardImpl {

    public XyrisTheWrithingStorm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{U}{R}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.SNAKE);
        this.subtype.add(SubType.LEVIATHAN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever an opponent draws a card except the first one they draw in each of their draw steps, create a 1/1 green Snake creature token.
        this.addAbility(new XyrisTheWrithingStormDrawAbility(), new CardsDrawnDuringDrawStepWatcher());
        // Whenever Xyris, the Writhing Storm deals combat damage to a player, you and that player each draw that many cards.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new XyrisTheWrithingStormCombatDamageEffect(), false, true));
    }

    private XyrisTheWrithingStorm(final XyrisTheWrithingStorm card) {
        super(card);
    }

    @Override
    public XyrisTheWrithingStorm copy() {
        return new XyrisTheWrithingStorm(this);
    }
}

class XyrisTheWrithingStormDrawAbility extends TriggeredAbilityImpl  {

    public XyrisTheWrithingStormDrawAbility() {
        super(Zone.BATTLEFIELD, new CreateTokenEffect(new SnakeToken(), 1), false);
    }

    public XyrisTheWrithingStormDrawAbility(final XyrisTheWrithingStormDrawAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DREW_CARD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (game.getPlayer(this.getControllerId()).hasOpponent(event.getPlayerId(), game)) {
            if (game.isActivePlayer(event.getPlayerId())
                    && game.getPhase().getStep().getType() == PhaseStep.DRAW) {
                CardsDrawnDuringDrawStepWatcher watcher = game.getState().getWatcher(CardsDrawnDuringDrawStepWatcher.class);
                if (watcher != null && watcher.getAmountCardsDrawn(event.getPlayerId()) > 1) {
                    return true;
                }
            } else {
                return true;
            }
        }
        return false;
    }

    @Override
    public TriggeredAbility copy() {
        return new XyrisTheWrithingStormDrawAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever an opponent draws a card except the first one they draw in each of their draw steps, create a 1/1 green Snake creature token.";
    }
}


class XyrisTheWrithingStormCombatDamageEffect extends OneShotEffect {

    public XyrisTheWrithingStormCombatDamageEffect() {
        super(Outcome.DrawCard);
        this.staticText = "you and that player each draw that many cards.";
    }

    public XyrisTheWrithingStormCombatDamageEffect(final XyrisTheWrithingStormCombatDamageEffect effect) {
        super(effect);
    }

    @Override
    public XyrisTheWrithingStormCombatDamageEffect copy() {
        return new XyrisTheWrithingStormCombatDamageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player sourceController = game.getPlayer(source.getControllerId());
        Player damagedPlayer = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        if (sourceController != null && damagedPlayer != null) {
            int amount = (Integer) getValue("damage");
            if (amount > 0) {
                sourceController.drawCards(amount, source.getSourceId(), game);
                damagedPlayer.drawCards(amount, source.getSourceId(), game);
                return true;
            }
        }
        return false;
    }
}