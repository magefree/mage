package mage.cards.c;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import mage.Mana;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.ControlledCreaturesDealCombatDamagePlayerTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author NinthWorld
 */
public final class CovertOps extends CardImpl {

    public CovertOps(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");
        

        // Whenever a face-down creature you control deals combat damage to a player, draw a card.
        this.addAbility(new CovertOpsTriggeredAbility());

        // {T}: Add {C} to your mana pool.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, Mana.ColorlessMana(1), new TapSourceCost()));
    }

    public CovertOps(final CovertOps card) {
        super(card);
    }

    @Override
    public CovertOps copy() {
        return new CovertOps(this);
    }
}

// Created from ControlledCreaturesDealCombatDamagePlayerTriggeredAbility
class CovertOpsTriggeredAbility extends TriggeredAbilityImpl {

    private boolean madeDamage = false;
    private Set<UUID> damagedPlayerIds = new HashSet<>();

    public CovertOpsTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), false);
    }

    public CovertOpsTriggeredAbility(final CovertOpsTriggeredAbility ability) {
        super(ability);
        this.madeDamage = ability.madeDamage;
        this.damagedPlayerIds = new HashSet<>();
        this.damagedPlayerIds.addAll(ability.damagedPlayerIds);
    }

    @Override
    public CovertOpsTriggeredAbility copy() {
        return new CovertOpsTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER
                || event.getType() == GameEvent.EventType.COMBAT_DAMAGE_STEP_PRIORITY
                || event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DAMAGED_PLAYER) {
            DamagedPlayerEvent damageEvent = (DamagedPlayerEvent) event;
            Permanent p = game.getPermanent(event.getSourceId());
            if (damageEvent.isCombatDamage() && p != null && p.isControlledBy(this.getControllerId()) && p.isFaceDown(game)) {
                madeDamage = true;
                damagedPlayerIds.add(event.getPlayerId());
            }
        }
        if (event.getType() == GameEvent.EventType.COMBAT_DAMAGE_STEP_PRIORITY) {
            if (madeDamage) {
                Set<UUID> damagedPlayersCopy = new HashSet<>();
                damagedPlayersCopy.addAll(damagedPlayerIds);
                for (Effect effect : this.getEffects()) {
                    effect.setValue("damagedPlayers", damagedPlayersCopy);
                }
                damagedPlayerIds.clear();
                madeDamage = false;
                return true;
            }
        }
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE && event.getTargetId().equals(getSourceId())) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
            if (zEvent.getFromZone() == Zone.GRAVEYARD) {
                damagedPlayerIds.clear();
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a face-down creature you control deal combat damage to a player, " + super.getRule();
    }
}