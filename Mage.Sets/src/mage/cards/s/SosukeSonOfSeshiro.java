
package mage.cards.s;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.delayed.AtTheEndOfCombatDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.events.DamagedCreatureEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 *
 * @author LevelX
 */
public final class SosukeSonOfSeshiro extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Snake creatures");

    static {
            filter.add(new SubtypePredicate(SubType.SNAKE));
    }

    public SosukeSonOfSeshiro(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}{G}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.SNAKE);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Other Snake creatures you control get +1/+0.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(1, 0, Duration.WhileOnBattlefield, filter, true)));
        // Whenever a Warrior you control deals combat damage to a creature, destroy that creature at end of combat.
        Effect effect = new CreateDelayedTriggeredAbilityEffect(
                new AtTheEndOfCombatDelayedTriggeredAbility(new DestroyTargetEffect()), true);
        effect.setText("destroy that creature at end of combat");
        this.addAbility(new SosukeSonOfSeshiroTriggeredAbility(effect));
    }

    public SosukeSonOfSeshiro(final SosukeSonOfSeshiro card) {
        super(card);
    }

    @Override
    public SosukeSonOfSeshiro copy() {
        return new SosukeSonOfSeshiro(this);
    }
}

class SosukeSonOfSeshiroTriggeredAbility extends TriggeredAbilityImpl {

    SosukeSonOfSeshiroTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect);
    }

    SosukeSonOfSeshiroTriggeredAbility(final SosukeSonOfSeshiroTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SosukeSonOfSeshiroTriggeredAbility copy() {
        return new SosukeSonOfSeshiroTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.DAMAGED_CREATURE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (((DamagedCreatureEvent) event).isCombatDamage()) {
            Permanent sourceCreature = game.getPermanent(event.getSourceId());
            Permanent targetCreature = game.getPermanent(event.getTargetId());
            if (sourceCreature != null && sourceCreature.isControlledBy(this.getControllerId())
                && targetCreature != null && sourceCreature.hasSubtype(SubType.WARRIOR, game)) {
                    this.getEffects().get(0).setTargetPointer(new FixedTarget(targetCreature.getId()));
                    return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a Warrior you control deals combat damage to a creature, destroy that creature at end of combat.";
    }
}
