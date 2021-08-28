package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.RollDieEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author spjspj
 */
public final class SnickeringSquirrel extends CardImpl {

    public SnickeringSquirrel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");

        this.subtype.add(SubType.SQUIRREL, SubType.ADVISOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // You may tap Snickering Squirrel to increase the result of a die any player rolled by 1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SnickeringSquirrelEffect()));
    }

    private SnickeringSquirrel(final SnickeringSquirrel card) {
        super(card);
    }

    @Override
    public SnickeringSquirrel copy() {
        return new SnickeringSquirrel(this);
    }
}

class SnickeringSquirrelEffect extends ReplacementEffectImpl {

    SnickeringSquirrelEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "You may tap {this} to increase the result of a die any player rolled by 1";
    }

    SnickeringSquirrelEffect(final SnickeringSquirrelEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        Player dieRoller = game.getPlayer(event.getPlayerId());
        if (controller == null || dieRoller == null) {
            return false;
        }
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent == null || permanent.isTapped()) {
            return false;
        }
        // TODO: allow AI for itself
        // TODO: remove tap check on applies (no useless replace events)?
        if (controller.chooseUse(Outcome.AIDontUseIt, "Tap this to increase the result of a die ("
                + event.getAmount() + ") " + dieRoller.getName() + " rolled by 1?", source, game)) {
            permanent.tap(source, game);
            ((RollDieEvent) event).incResultModifier(1);
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ROLL_DIE
                && ((RollDieEvent) event).getRollDieType() == RollDieType.NUMERICAL;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public SnickeringSquirrelEffect copy() {
        return new SnickeringSquirrelEffect(this);
    }
}
