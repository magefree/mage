
package mage.cards.t;

import java.util.UUID;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public final class TouchOfMoonglove extends CardImpl {

    public TouchOfMoonglove(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{B}");

        // Target creature you control gets +1/+0 and gains deathtouch until end of turn.
        Effect effect = new BoostTargetEffect(1, 0, Duration.EndOfTurn);
        effect.setText("Target creature you control gets +1/+0");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(
                DeathtouchAbility.getInstance(), Duration.EndOfTurn, "and gains deathtouch until end of turn"));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        // Whenever a creature dealt damage by that creature this turn dies, its controller loses 2 life.
        this.getSpellAbility().addEffect(new TouchOfMoongloveAddTriggerEffect());

    }

    private TouchOfMoonglove(final TouchOfMoonglove card) {
        super(card);
    }

    @Override
    public TouchOfMoonglove copy() {
        return new TouchOfMoonglove(this);
    }
}

class TouchOfMoongloveAddTriggerEffect extends OneShotEffect {

    public TouchOfMoongloveAddTriggerEffect() {
        super(Outcome.LoseLife);
        this.staticText = "Whenever a creature dealt damage by that creature dies this turn, its controller loses 2 life";
    }

    public TouchOfMoongloveAddTriggerEffect(final TouchOfMoongloveAddTriggerEffect effect) {
        super(effect);
    }

    @Override
    public TouchOfMoongloveAddTriggerEffect copy() {
        return new TouchOfMoongloveAddTriggerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        if (permanent != null) {
            DelayedTriggeredAbility delayedAbility = new TouchOfMoongloveDelayedTriggeredAbility(new MageObjectReference(permanent, game));
            game.addDelayedTriggeredAbility(delayedAbility, source);
        }
        return true;
    }
}

class TouchOfMoongloveDelayedTriggeredAbility extends DelayedTriggeredAbility {

    private final MageObjectReference creatureToCheck;

    public TouchOfMoongloveDelayedTriggeredAbility(MageObjectReference creatureToCheck) {
        super(new LoseLifeTargetEffect(2), Duration.EndOfTurn, false);
        this.creatureToCheck = creatureToCheck;
    }

    public TouchOfMoongloveDelayedTriggeredAbility(TouchOfMoongloveDelayedTriggeredAbility ability) {
        super(ability);
        this.creatureToCheck = ability.creatureToCheck;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (zEvent.isDiesEvent()
                && zEvent.getTarget().isCreature(game)) {
            boolean damageDealt = false;
            for (MageObjectReference mor : zEvent.getTarget().getDealtDamageByThisTurn()) {
                if (mor.equals(creatureToCheck)) {
                    damageDealt = true;
                    break;
                }
            }
            if (damageDealt) {
                for (Effect effect : getEffects()) {
                    effect.setTargetPointer(new FixedTarget(zEvent.getTarget().getControllerId()));
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public TouchOfMoongloveDelayedTriggeredAbility copy() {
        return new TouchOfMoongloveDelayedTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever a creature dealt damage by that creature this turn dies, its controller loses 2 life.";
    }
}
