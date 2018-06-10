
package mage.cards.u;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author Loki
 */
public final class UmezawasJitte extends CardImpl {

    public UmezawasJitte(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.EQUIPMENT);

        // Whenever equipped creature deals combat damage, put two charge counters on Umezawa's Jitte.
        this.addAbility(new UmezawasJitteAbility());

        // Remove a charge counter from Umezawa's Jitte: Choose one &mdash; Equipped creature gets +2/+2 until end of turn.
        Ability ability = new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new BoostEquippedEffect(2, 2, Duration.EndOfTurn),
                new RemoveCountersSourceCost(CounterType.CHARGE.createInstance()));

        // Target creature gets -1/-1 until end of turn.
        Mode mode = new Mode();
        mode.getEffects().add(new BoostTargetEffect(-1, -1, Duration.EndOfTurn));
        mode.getTargets().add(new TargetCreaturePermanent());
        ability.addMode(mode);

        // You gain 2 life.
        mode = new Mode();
        mode.getEffects().add(new GainLifeEffect(2));
        ability.addMode(mode);
        this.addAbility(ability);

        // Equip {2}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(2)));
    }

    public UmezawasJitte(final UmezawasJitte card) {
        super(card);
    }

    @Override
    public UmezawasJitte copy() {
        return new UmezawasJitte(this);
    }
}

class UmezawasJitteAbility extends TriggeredAbilityImpl {

    private boolean usedInPhase;

    public UmezawasJitteAbility() {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.CHARGE.createInstance(2)));
        this.usedInPhase = false;
    }

    public UmezawasJitteAbility(final UmezawasJitteAbility ability) {
        super(ability);
        this.usedInPhase = ability.usedInPhase;
    }

    @Override
    public UmezawasJitteAbility copy() {
        return new UmezawasJitteAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        switch(event.getType()) {
            case DAMAGED_CREATURE:
            case DAMAGED_PLANESWALKER:
            case DAMAGED_PLAYER:
            case COMBAT_DAMAGE_STEP_PRE:
                return true;
        }
        return false;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event instanceof DamagedEvent && !usedInPhase && ((DamagedEvent) event).isCombatDamage()) {
            Permanent permanent = game.getPermanent(event.getSourceId());
            if (permanent != null && permanent.getAttachments().contains(this.getSourceId())) {
                usedInPhase = true;
                return true;
            }
        }
        if (event.getType() == EventType.COMBAT_DAMAGE_STEP_PRE) {
            usedInPhase = false;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever equipped creature deals combat damage, " + super.getRule();
    }
}
