package mage.cards.d;

import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DamageWithPowerFromOneToAnotherTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author htrajan
 */
public final class DevouringTendrils extends CardImpl {

    public static final FilterPermanent filter = new FilterCreatureOrPlaneswalkerPermanent("creature or planeswalker you don't control");

    static {
        filter.add(TargetController.NOT_YOU.getControllerPredicate());
    }

    public DevouringTendrils(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{G}");

        // Target creature you control deals damage equal to its power to target creature or planeswalker you don't control. When the permanent you don't control dies this turn, you gain 2 life.
        Effect effect = new DamageWithPowerFromOneToAnotherTargetEffect();
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        TargetPermanent target = new TargetPermanent(filter);
        target.setTargetTag(2);
        this.getSpellAbility().addTarget(target);

        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(new DevouringTendrilsDelayedTriggeredAbility(), true));
    }

    private DevouringTendrils(final DevouringTendrils card) {
        super(card);
    }

    @Override
    public DevouringTendrils copy() {
        return new DevouringTendrils(this);
    }
}

class DevouringTendrilsDelayedTriggeredAbility extends DelayedTriggeredAbility {

    DevouringTendrilsDelayedTriggeredAbility(){
        super(new GainLifeEffect(2), Duration.EndOfTurn, false);
    }

    DevouringTendrilsDelayedTriggeredAbility(DevouringTendrilsDelayedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (zEvent.isDiesEvent()) {
            Target target = getTargets().stream()
                .filter(t -> t.getTargetTag() == 2)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Expected to find target with tag 2 but found none"));
            return zEvent.getTarget() != null && zEvent.getTargetId().equals(target.getFirstTarget());
        }
        return false;
    }

    @Override
    public DevouringTendrilsDelayedTriggeredAbility copy() {
        return new DevouringTendrilsDelayedTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "When the permanent you don't control dies this turn, you gain 2 life.";
    }
}