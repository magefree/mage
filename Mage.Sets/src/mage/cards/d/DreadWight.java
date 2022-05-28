package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.delayed.AtTheEndOfCombatDelayedTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalContinuousRuleModifyingEffect;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousRuleModifyingEffect;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.effects.common.counter.RemoveCounterSourceEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth
 */
public final class DreadWight extends CardImpl {

    public DreadWight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // At end of combat, put a paralyzation counter on each creature blocking or blocked by Dread Wight and tap those creatures. Each of those creatures doesn't untap during its controller's untap step for as long as it has a paralyzation counter on it. Each of those creatures gains "{4}: Remove a paralyzation counter from this creature."
        this.addAbility(new DreadWightTriggeredAbility(
                new CreateDelayedTriggeredAbilityEffect(
                        new AtTheEndOfCombatDelayedTriggeredAbility(
                                new DreadWightEffect()))));

    }

    private DreadWight(final DreadWight card) {
        super(card);
    }

    @Override
    public DreadWight copy() {
        return new DreadWight(this);
    }
}

class DreadWightTriggeredAbility extends TriggeredAbilityImpl {

    DreadWightTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect);
        this.usesStack = false;
    }

    DreadWightTriggeredAbility(final DreadWightTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public DreadWightTriggeredAbility copy() {
        return new DreadWightTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return (event.getType() == GameEvent.EventType.BLOCKER_DECLARED);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getSourceId().equals(getSourceId())) { // Dread Wight is the blocker
            getAllEffects().setTargetPointer(new FixedTarget(event.getTargetId(), game));
            return true;
        }
        if (event.getTargetId().equals(getSourceId())) { // Dread Wight is the attacker
            getAllEffects().setTargetPointer(new FixedTarget(event.getSourceId(), game));
            return true;
        }
        return false;
    }

}

class DreadWightEffect extends OneShotEffect {

    String rule = "doesn't untap during its controller's untap step for as long as it has a paralyzation counter on it.";

    public DreadWightEffect() {
        super(Outcome.Detriment);
        this.staticText = "put a paralyzation counter on each creature blocking or blocked by {this} and tap those creatures. "
                + "Each of those creatures doesn't untap during its controller's untap step for as long as it has a paralyzation counter on it. "
                + "Each of those creatures gains \"{4}: Remove a paralyzation counter from this creature.\"";
    }

    public DreadWightEffect(final DreadWightEffect effect) {
        super(effect);
    }

    @Override
    public DreadWightEffect copy() {
        return new DreadWightEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(targetPointer.getFirst(game, source));
        if (permanent != null) {
            // add paralyzation counter
            Effect effect = new AddCountersTargetEffect(CounterType.PARALYZATION.createInstance());
            effect.setTargetPointer(new FixedTarget(permanent, game));
            effect.apply(game, source);
            // tap permanent
            permanent.tap(source, game);
            // does not untap while paralyzation counter is on it
            ContinuousRuleModifyingEffect effect2 = new DreadWightDoNotUntapEffect(
                    Duration.WhileOnBattlefield,
                    permanent.getId());
            effect2.setText("This creature doesn't untap during its controller's untap step for as long as it has a paralyzation counter on it");
            Condition condition = new DreadWightCounterCondition(permanent.getId());
            ConditionalContinuousRuleModifyingEffect conditionalEffect = new ConditionalContinuousRuleModifyingEffect(
                    effect2,
                    condition);
            Ability ability = new SimpleStaticAbility(
                    Zone.BATTLEFIELD,
                    conditionalEffect);
            ContinuousEffect effect3 = new GainAbilityTargetEffect(
                    ability,
                    Duration.WhileOnBattlefield);
            ability.setRuleVisible(true);
            effect3.setTargetPointer(new FixedTarget(permanent, game));
            game.addEffect(effect3, source);
            // each gains 4: remove paralyzation counter
            Ability activatedAbility = new SimpleActivatedAbility(
                    Zone.BATTLEFIELD,
                    new RemoveCounterSourceEffect(CounterType.PARALYZATION.createInstance()),
                    new ManaCostsImpl<>("{4}"));
            ContinuousEffect effect4 = new GainAbilityTargetEffect(
                    activatedAbility,
                    Duration.WhileOnBattlefield);
            effect4.setTargetPointer(new FixedTarget(permanent, game));
            game.addEffect(effect4, source);
            return true;
        }
        return false;
    }
}

class DreadWightDoNotUntapEffect extends ContinuousRuleModifyingEffectImpl {

    UUID permanentId;

    public DreadWightDoNotUntapEffect(Duration duration, UUID permanentId) {
        super(duration, Outcome.Detriment);
        this.permanentId = permanentId;
    }

    public DreadWightDoNotUntapEffect(final DreadWightDoNotUntapEffect effect) {
        super(effect);
        this.permanentId = effect.permanentId;
    }

    @Override
    public DreadWightDoNotUntapEffect copy() {
        return new DreadWightDoNotUntapEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        Permanent permanentToUntap = game.getPermanent((event.getTargetId()));
        if (permanentToUntap != null) {
            return permanentToUntap.getLogName() + " doesn't untap.";
        }
        return null;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.UNTAP;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getTargetId() == permanentId
                && game.isActivePlayer(game.getPermanent(permanentId).getControllerId());
    }
}

class DreadWightCounterCondition implements Condition {

    UUID permanentId;

    public DreadWightCounterCondition(UUID permanentId) {
        this.permanentId = permanentId;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(permanentId);
        if (permanent != null) {
            return permanent.getCounters(game).getCount(CounterType.PARALYZATION) > 0;
        }
        return false;
    }

    @Override
    public String toString() {
        return "has counter on it";
    }
}
