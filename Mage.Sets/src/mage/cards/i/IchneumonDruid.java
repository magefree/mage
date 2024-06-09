
package mage.cards.i;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.Watcher;

/**
 *
 * @author L_J
 */
public final class IchneumonDruid extends CardImpl {

    public IchneumonDruid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever an opponent casts an instant spell other than the first instant spell that player casts each turn, Ichneumon Druid deals 4 damage to that player.
        this.addAbility(new IchneumonDruidAbility(), new IchneumonDruidWatcher());
    }

    private IchneumonDruid(final IchneumonDruid card) {
        super(card);
    }

    @Override
    public IchneumonDruid copy() {
        return new IchneumonDruid(this);
    }
}

class IchneumonDruidAbility extends TriggeredAbilityImpl {

    public IchneumonDruidAbility() {
        super(Zone.BATTLEFIELD, new DamageTargetEffect(StaticValue.get(4), false, "that player", true));
    }

    private IchneumonDruidAbility(final IchneumonDruidAbility ability) {
        super(ability);
    }

    @Override
    public IchneumonDruidAbility copy() {
        return new IchneumonDruidAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!event.getPlayerId().equals(controllerId)) {
            Spell spell = game.getStack().getSpell(event.getTargetId());
            if (spell != null && spell.isInstant(game)) {
                IchneumonDruidWatcher watcher = game.getState().getWatcher(IchneumonDruidWatcher.class);
                if (watcher != null && watcher.moreThanTwoInstantsCast(event.getPlayerId(), game)) {
                    for (Effect effect : getEffects()) {
                        effect.setTargetPointer(new FixedTarget(event.getPlayerId()));
                    }
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever an opponent casts an instant spell other than the first instant spell that player casts each turn, {this} deals 4 damage to that player.";
    }
}

class IchneumonDruidWatcher extends Watcher {

    private final Map<UUID, Integer> playerInstantCount = new HashMap<>();

    public IchneumonDruidWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SPELL_CAST) {
            UUID playerId = event.getPlayerId();
            Spell spell = game.getStack().getSpell(event.getTargetId());
            if (spell != null && spell.isInstant(game)) {
                playerInstantCount.putIfAbsent(event.getPlayerId(), 0);
                playerInstantCount.compute(playerId, (k, v) -> v + 1);
            }
        }
    }

    public boolean moreThanTwoInstantsCast(UUID playerId, Game game) {
        return playerInstantCount.get(playerId) > 1;
    }

    @Override
    public void reset() {
        playerInstantCount.clear();
    }
}
