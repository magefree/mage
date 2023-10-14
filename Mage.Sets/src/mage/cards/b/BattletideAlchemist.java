package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.PreventionEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.PreventDamageEvent;
import mage.game.events.PreventedDamageEvent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class BattletideAlchemist extends CardImpl {

    public BattletideAlchemist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");
        this.subtype.add(SubType.KITHKIN, SubType.CLERIC);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // If a source would deal damage to a player, you may prevent X of that damage, where X is the number of Clerics you control.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BattletideAlchemistEffect()));
    }

    private BattletideAlchemist(final BattletideAlchemist card) {
        super(card);
    }

    @Override
    public BattletideAlchemist copy() {
        return new BattletideAlchemist(this);
    }
}

class BattletideAlchemistEffect extends PreventionEffectImpl {

    BattletideAlchemistEffect() {
        super(Duration.WhileOnBattlefield);
        this.staticText = "If a source would deal damage to a player, you may prevent X of that damage, where X is the number of Clerics you control";
    }

    private BattletideAlchemistEffect(final BattletideAlchemistEffect effect) {
        super(effect);
    }

    @Override
    public BattletideAlchemistEffect copy() {
        return new BattletideAlchemistEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        boolean result = false;
        Player controller = game.getPlayer(source.getControllerId());
        Player targetPlayer = game.getPlayer(event.getTargetId());
        if (controller != null && targetPlayer != null) {
            int numberOfClericsControlled = new PermanentsOnBattlefieldCount(new FilterControlledCreaturePermanent(SubType.CLERIC, "Clerics")).calculate(game, source, this);
            int toPrevent = Math.min(numberOfClericsControlled, event.getAmount());
            if (toPrevent > 0 && controller.chooseUse(Outcome.PreventDamage, "Prevent " + toPrevent + " damage to " + targetPlayer.getName() + '?', source, game)) {
                GameEvent preventEvent = new PreventDamageEvent(event.getTargetId(), source.getSourceId(), source, source.getControllerId(), toPrevent, ((DamageEvent) event).isCombatDamage());
                if (!game.replaceEvent(preventEvent)) {
                    if (event.getAmount() >= toPrevent) {
                        event.setAmount(event.getAmount() - toPrevent);
                    } else {
                        event.setAmount(0);
                        result = true;
                    }
                    game.informPlayers("Battletide Alchemist prevented " + toPrevent + " damage to " + targetPlayer.getName());
                    game.fireEvent(new PreventedDamageEvent(event.getTargetId(), source.getSourceId(), source, source.getControllerId(), toPrevent));
                }
            }
        }
        return result;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGE_PLAYER;
    }

}
