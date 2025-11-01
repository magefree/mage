package mage.abilities.effects.keyword;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.Mode;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderOwnerControlTargetEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.custom.CreatureToken;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

/**
 * @author TheElk801
 */
public class EarthbendTargetEffect extends OneShotEffect {

    private final DynamicValue amount;

    public EarthbendTargetEffect(int amount) {
        this(StaticValue.get(amount));
    }

    public EarthbendTargetEffect(DynamicValue amount) {
        super(Outcome.Benefit);
        this.amount = amount;
    }

    private EarthbendTargetEffect(final EarthbendTargetEffect effect) {
        super(effect);
        this.amount = effect.amount;
    }

    @Override
    public EarthbendTargetEffect copy() {
        return new EarthbendTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            return false;
        }
        game.addEffect(new BecomesCreatureTargetEffect(
                new CreatureToken(0, 0)
                        .withAbility(HasteAbility.getInstance()),
                false, true, Duration.Custom
        ), source);
        int value = amount.calculate(game, source, this);
        permanent.addCounters(CounterType.P1P1.createInstance(value), source, game);
        game.addDelayedTriggeredAbility(new EarthbendingDelayedTriggeredAbility(permanent, game), source);
        game.fireEvent(GameEvent.getEvent(
                GameEvent.EventType.EARTHBENDED, permanent.getId(),
                source, source.getControllerId(), value
        ));
        return true;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        StringBuilder sb = new StringBuilder("earthbend ");
        sb.append(amount);
        if (!(amount instanceof StaticValue)) {
            sb.append(", where X is ");
            sb.append(amount.getMessage());
        }
        sb.append(". <i>(Target land you control becomes a 0/0 creature with haste that's still a land. Put ");
        String value = amount instanceof StaticValue
                ? CardUtil.numberToText(((StaticValue) amount).getValue(), "a")
                : amount.toString();
        sb.append(value);
        sb.append(" +1/+1 counter");
        sb.append(("a".equals(value) ? "" : "s"));
        sb.append(" on it. When it dies or is exiled, return it to the battlefield tapped.)</i>");
        return sb.toString();
    }
}

class EarthbendingDelayedTriggeredAbility extends DelayedTriggeredAbility {

    private final MageObjectReference mor;

    EarthbendingDelayedTriggeredAbility(Permanent permanent, Game game) {
        super(new ReturnToBattlefieldUnderOwnerControlTargetEffect(true, false), Duration.Custom, true, false);
        this.mor = new MageObjectReference(permanent, game, 1);
        this.getAllEffects().setTargetPointer(new FixedTarget(this.mor));
    }

    private EarthbendingDelayedTriggeredAbility(final EarthbendingDelayedTriggeredAbility ability) {
        super(ability);
        this.mor = ability.mor;
    }

    @Override
    public EarthbendingDelayedTriggeredAbility copy() {
        return new EarthbendingDelayedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        return zEvent.getFromZone() == Zone.BATTLEFIELD
                && (zEvent.getToZone() == Zone.GRAVEYARD || zEvent.getToZone() == Zone.EXILED)
                && mor.refersTo(zEvent.getTarget(), game, -1);
    }

    @Override
    public String getRule() {
        return "When it dies or is exiled, return it to the battlefield tapped.";
    }
}
