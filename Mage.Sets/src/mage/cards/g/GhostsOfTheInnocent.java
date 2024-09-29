package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.PreventionEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.PreventDamageEvent;
import mage.game.events.PreventedDamageEvent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class GhostsOfTheInnocent extends CardImpl {

    public GhostsOfTheInnocent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{W}{W}");

        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // If a source would deal damage to a creature or player, it deals half that damage, rounded down, to that creature or player instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GhostsOfTheInnocentPreventDamageEffect()));
    }

    private GhostsOfTheInnocent(final GhostsOfTheInnocent card) {
        super(card);
    }

    @Override
    public GhostsOfTheInnocent copy() {
        return new GhostsOfTheInnocent(this);
    }
}

class GhostsOfTheInnocentPreventDamageEffect extends ReplacementEffectImpl implements PreventionEffect {

    public GhostsOfTheInnocentPreventDamageEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Damage);
        staticText = "If a source would deal damage to a permanent or player, it deals half that damage, rounded down, to that permanent or player instead";
    }

    private GhostsOfTheInnocentPreventDamageEffect(final GhostsOfTheInnocentPreventDamageEffect effect) {
        super(effect);
    }

    @Override
    public GhostsOfTheInnocentPreventDamageEffect copy() {
        return new GhostsOfTheInnocentPreventDamageEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGE_PLAYER
                || event.getType() == EventType.DAMAGE_PERMANENT;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        int amount = (int) Math.ceil(event.getAmount() / 2.0);
        GameEvent preventEvent = new PreventDamageEvent(event.getTargetId(), source.getSourceId(), source, source.getControllerId(), amount, ((DamageEvent) event).isCombatDamage());
        if (!game.replaceEvent(preventEvent)) {
            event.setAmount(event.getAmount() - amount);
            game.fireEvent(new PreventedDamageEvent(event.getTargetId(), source.getSourceId(), source, source.getControllerId(), amount));
        }
        return false;
    }
}
