package mage.cards.x;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.OpponentDrawCardExceptFirstCardDrawStepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.token.SnakeToken;
import mage.players.Player;

import java.util.UUID;

/**
 * @author AsterAether
 */
public final class XyrisTheWrithingStorm extends CardImpl {

    public XyrisTheWrithingStorm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{U}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SNAKE);
        this.subtype.add(SubType.LEVIATHAN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever an opponent draws a card except the first one they draw in each of their draw steps, create a 1/1 green Snake creature token.
        this.addAbility(new OpponentDrawCardExceptFirstCardDrawStepTriggeredAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new SnakeToken(), 1), false));

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
                sourceController.drawCards(amount, source, game);
                damagedPlayer.drawCards(amount, source, game);
                return true;
            }
        }
        return false;
    }
}