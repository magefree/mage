
package mage.cards.t;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.BeginningOfYourEndStepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.CompoundCondition;
import mage.abilities.condition.Condition;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.AttachedCondition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.SacrificeEquippedEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.DamagedCreatureEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.watchers.Watcher;


/**
 *
 * @author Quercitron
 */
public final class ThirstingAxe extends CardImpl {

    public ThirstingAxe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +4/+0.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(4, 0)));

        // At the beginning of your end step, if equipped creature didn't deal combat damage to a creature this turn, sacrifice it.
        TriggeredAbility ability = new BeginningOfYourEndStepTriggeredAbility(new SacrificeEquippedEffect(), false);
        Condition condition = new CompoundCondition(
                AttachedCondition.instance,
                new InvertCondition(new EquippedDealtCombatDamageToCreatureCondition()));
        String triggeredAbilityText = "At the beginning of your end step, if equipped creature " +
            "didn't deal combat damage to a creature this turn, sacrifice it.";
        ConditionalInterveningIfTriggeredAbility sacrificeTriggeredAbility = new ConditionalInterveningIfTriggeredAbility(ability, condition, triggeredAbilityText);
        this.addAbility(sacrificeTriggeredAbility, new CombatDamageToCreatureWatcher());

        // Equip {2}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(2)));
    }

    public ThirstingAxe(final ThirstingAxe card) {
        super(card);
    }

    @Override
    public ThirstingAxe copy() {
        return new ThirstingAxe(this);
    }
}

class EquippedDealtCombatDamageToCreatureCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent equipment = game.getPermanent(source.getSourceId());
        if (equipment != null && equipment.getAttachedTo() != null) {
            CombatDamageToCreatureWatcher watcher =
                    game.getState().getWatcher(CombatDamageToCreatureWatcher.class);
            return watcher != null && watcher.dealtDamage(equipment.getAttachedTo(), equipment.getAttachedToZoneChangeCounter(), game);
        }
        return false;
    }

}

class CombatDamageToCreatureWatcher extends Watcher {

    // which objects dealt combat damage to creature during the turn
    public final Set<MageObjectReference> dealtCombatDamageToCreature;

    final static String BASIC_KEY = "CombatDamageToCreatureWatcher";

    public CombatDamageToCreatureWatcher() {
        super(BASIC_KEY, WatcherScope.GAME);
        dealtCombatDamageToCreature = new HashSet<>();
    }

    public CombatDamageToCreatureWatcher(final CombatDamageToCreatureWatcher watcher) {
        super(watcher);
        dealtCombatDamageToCreature = new HashSet<>(watcher.dealtCombatDamageToCreature);
    }

    @Override
    public CombatDamageToCreatureWatcher copy() {
        return new CombatDamageToCreatureWatcher(this);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DAMAGED_CREATURE) {
            if (((DamagedCreatureEvent) event).isCombatDamage()) {
                MageObjectReference damageSource = new MageObjectReference(event.getSourceId(), game);
                dealtCombatDamageToCreature.add(damageSource);
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        dealtCombatDamageToCreature.clear();
    }

    public boolean dealtDamage(UUID objectId, int zoneChangeCounter, Game game) {
        MageObjectReference reference = new MageObjectReference(objectId, zoneChangeCounter, game);
        return dealtCombatDamageToCreature.contains(reference);
    }

}
