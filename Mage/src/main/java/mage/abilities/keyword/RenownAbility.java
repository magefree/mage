package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.hint.common.RenownedHint;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public class RenownAbility extends TriggeredAbilityImpl {

    private int renownValue;

    public RenownAbility(int renownValue) {
        super(Zone.BATTLEFIELD, new BecomesRenownedSourceEffect(renownValue), false);
        this.renownValue = renownValue;

        this.addHint(RenownedHint.instance);
    }

    private RenownAbility(final RenownAbility ability) {
        super(ability);
        this.renownValue = ability.renownValue;
    }

    @Override
    public RenownAbility copy() {
        return new RenownAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(getSourceId());
        return sourcePermanent != null && !sourcePermanent.isRenowned();
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getSourceId().equals(getSourceId())
                && ((DamagedPlayerEvent) event).isCombatDamage();
    }

    public int getRenownValue() {
        return renownValue;
    }
}

class BecomesRenownedSourceEffect extends OneShotEffect {

    BecomesRenownedSourceEffect(int renownValue) {
        super(Outcome.BoostCreature);
        this.staticText = setText(renownValue);
    }

    private BecomesRenownedSourceEffect(final BecomesRenownedSourceEffect effect) {
        super(effect);
    }

    @Override
    public BecomesRenownedSourceEffect copy() {
        return new BecomesRenownedSourceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null && source instanceof RenownAbility) {
            game.informPlayers(permanent.getLogName() + " is now renowned");
            int renownValue = ((RenownAbility) source).getRenownValue();
            // handle renown = X
            if (renownValue == Integer.MAX_VALUE) {
                renownValue = source.getManaCostsToPay().getX();
            }
            new AddCountersSourceEffect(CounterType.P1P1.createInstance(renownValue), true).apply(game, source);
            permanent.setRenowned(true);
            game.fireEvent(GameEvent.getEvent(GameEvent.EventType.BECOMES_RENOWNED, source.getSourceId(), source, source.getControllerId(), renownValue));
            return true;
        }
        return false;
    }

    private String setText(int renownValue) {
        // Renown 1 (When this creature deals combat damage to a player, if it isn't renowned, put a +1/+1 counter on it and it becomes renowned.)
        StringBuilder sb = new StringBuilder("Renown ");
        sb.append(renownValue == Integer.MAX_VALUE ? "X" : renownValue)
                .append(" <i>(When this creature deals combat damage to a player, if it isn't renowned, put ")
                .append(renownValue == Integer.MAX_VALUE ? "X" : CardUtil.numberToText(renownValue, "a"))
                .append(" +1/+1 counter on it and it becomes renowned.)</i>");
        return sb.toString();
    }

}
