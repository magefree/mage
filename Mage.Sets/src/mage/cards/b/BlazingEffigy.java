
package mage.cards.b;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetCreaturePermanent;
import mage.util.CardUtil;
import mage.watchers.Watcher;

/**
 *
 * @author TheElk801 & L_J
 */
public final class BlazingEffigy extends CardImpl {

    public BlazingEffigy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");
        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // When Blazing Effigy dies, it deals X damage to target creature, where X is 3 plus the amount of damage dealt to Blazing Effigy this turn by other sources named Blazing Effigy.
        Ability ability = new DiesSourceTriggeredAbility(new DamageTargetEffect(BlazingEffigyCount.instance), false);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability, new BlazingEffigyWatcher());
    }

    private BlazingEffigy(final BlazingEffigy card) {
        super(card);
    }

    @Override
    public BlazingEffigy copy() {
        return new BlazingEffigy(this);
    }
}

enum BlazingEffigyCount implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        BlazingEffigyWatcher watcher = game.getState().getWatcher(BlazingEffigyWatcher.class);
        if (watcher == null) {
            return 3;
        }
        int effigyDamage = watcher.damageDoneTo(sourceAbility.getSourceId(), sourceAbility.getSourceObjectZoneChangeCounter() - 1, game);
        return CardUtil.overflowInc(3, effigyDamage);
    }

    @Override
    public BlazingEffigyCount copy() {
        return BlazingEffigyCount.instance;
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "3 plus the amount of damage dealt to {this} this turn by other sources named Blazing Effigy";
    }
}

class BlazingEffigyWatcher extends Watcher {

    private final Map<MageObjectReference, Integer> damagedObjects = new HashMap<>();

    public BlazingEffigyWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.DAMAGED_PERMANENT) { return; }

        if (event.getSourceId().equals(event.getTargetId())) { return; }

        // TODO: Should damageSourceRef be used for anything?
        MageObjectReference damageSourceRef = new MageObjectReference(event.getSourceId(), game);
        MageObjectReference damageTargetRef = new MageObjectReference(event.getTargetId(), game);
        if (game.getPermanentOrLKIBattlefield(event.getSourceId()) != null && game.getPermanentOrLKIBattlefield(event.getSourceId()).getName().equals("Blazing Effigy")) {
            damagedObjects.putIfAbsent(damageTargetRef, 0);
            damagedObjects.compute(damageTargetRef, (k, damage) -> damage + event.getAmount());
        }
    }

    @Override
    public void reset() {
        super.reset();
        damagedObjects.clear();
    }

    public int damageDoneTo(UUID objectId, int zoneChangeCounter, Game game) {
        MageObjectReference mor = new MageObjectReference(objectId, zoneChangeCounter, game);
        return damagedObjects.getOrDefault(mor, 0);
    }
}
