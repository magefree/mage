package mage.cards.s;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.dynamicvalue.common.EffectKeyValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.ThatPlayerControlsTargetAdjuster;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class SatyrFiredancer extends CardImpl {

    public SatyrFiredancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{1}{R}");
        this.subtype.add(SubType.SATYR);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever an instant or sorcery spell you control deals damage to an opponent, Satyr Firedancer deals that much damage to target creature that player controls.
        this.addAbility(new SatyrFiredancerTriggeredAbility());
    }

    private SatyrFiredancer(final SatyrFiredancer card) {
        super(card);
    }

    @Override
    public SatyrFiredancer copy() {
        return new SatyrFiredancer(this);
    }
}

class SatyrFiredancerTriggeredAbility extends TriggeredAbilityImpl {

    SatyrFiredancerTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DamageTargetEffect(new EffectKeyValue("damage", "that much")), false);
        this.addTarget(new TargetCreaturePermanent().withTargetName("target creature that player controls"));
        this.setTargetAdjuster(new ThatPlayerControlsTargetAdjuster());
        setTriggerPhrase("Whenever an instant or sorcery spell you control deals damage to an opponent, ");
    }

    private SatyrFiredancerTriggeredAbility(final SatyrFiredancerTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SatyrFiredancerTriggeredAbility copy() {
        return new SatyrFiredancerTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!isControlledBy(game.getControllerId(event.getSourceId()))) {
            return false;
        }
        MageObject damageSource = game.getObject(event.getSourceId());
        if (damageSource == null) {
            return false;
        }
        UUID damageTargetId = event.getTargetId();
        if (!game.getOpponents(getControllerId()).contains(damageTargetId)) {
            return false;
        }
        MageObject sourceObject = game.getObject(event.getSourceId());
        if (sourceObject == null || !sourceObject.isInstantOrSorcery(game)) {
            return false;
        }
        for (Effect effect : this.getEffects()) {
            effect.setTargetPointer(new FixedTarget(damageTargetId)); // used by adjust targets
            effect.setValue("damage", event.getAmount());
        }
        return true;
    }
}
