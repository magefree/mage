package mage.cards.d;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageWithPowerFromOneToAnotherTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author htrajan
 */
public final class DevouringTendrils extends CardImpl {

    private static final FilterPermanent filter = new FilterCreatureOrPlaneswalkerPermanent("creature or planeswalker you don't control");

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

        this.getSpellAbility().addEffect(new DevouringTendrilsEffect());
    }

    private DevouringTendrils(final DevouringTendrils card) {
        super(card);
    }

    @Override
    public DevouringTendrils copy() {
        return new DevouringTendrils(this);
    }
}

class DevouringTendrilsEffect extends OneShotEffect {

    public DevouringTendrilsEffect() {
        super(Outcome.Benefit);
        this.staticText = "when the permanent you don't control dies this turn, you gain 2 life";
    }

    private DevouringTendrilsEffect(final DevouringTendrilsEffect effect) {
        super(effect);
    }

    @Override
    public DevouringTendrilsEffect copy() {
        return new DevouringTendrilsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Target target = source.getTargets().stream()
            .filter(t -> t.getTargetTag() == 2)
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("Expected to find target with tag 2 but found none"));
        Permanent permanent = game.getPermanent(target.getFirstTarget());
        if (permanent != null) {
            DelayedTriggeredAbility delayedAbility = new DevouringTendrilsDelayedTriggeredAbility(new MageObjectReference(permanent, game));
            game.addDelayedTriggeredAbility(delayedAbility, source);
            return true;
        }
        return false;
    }
}

class DevouringTendrilsDelayedTriggeredAbility extends DelayedTriggeredAbility {

    private final MageObjectReference mor;

    DevouringTendrilsDelayedTriggeredAbility(MageObjectReference mor){
        super(new GainLifeEffect(2), Duration.EndOfTurn, false);
        this.mor = mor;
    }

    private DevouringTendrilsDelayedTriggeredAbility(final DevouringTendrilsDelayedTriggeredAbility ability) {
        super(ability);
        this.mor = ability.mor;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        return zEvent.isDiesEvent() && mor.refersTo(zEvent.getTarget(), game);
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
