package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.watchers.common.ManaPaidSourceWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BorealOutrider extends CardImpl {

    public BorealOutrider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.supertype.add(SuperType.SNOW);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Whenever you cast a creature spell, if {S} of any of that spell's color was spent to cast it, that creature enters the battlefield with an additional +1/+1 counter on it.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new SpellCastControllerTriggeredAbility(
                        new BorealOutriderEffect(), StaticFilters.FILTER_SPELL_A_CREATURE, false, true
                ), BorealOutriderCondition.instance, "Whenever you cast a creature spell, " +
                "if {S} of any of that spell's colors was spent to cast it, that creature " +
                "enters the battlefield with an additional +1/+1 counter on it."
        ));
    }

    private BorealOutrider(final BorealOutrider card) {
        super(card);
    }

    @Override
    public BorealOutrider copy() {
        return new BorealOutrider(this);
    }
}

enum BorealOutriderCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = (Spell) source.getEffects().get(0).getValue("spellCast");
        return spell != null && ManaPaidSourceWatcher.checkSnowColor(spell, game);
    }
}

class BorealOutriderEffect extends ReplacementEffectImpl {

    BorealOutriderEffect() {
        super(Duration.EndOfStep, Outcome.BoostCreature);
    }

    private BorealOutriderEffect(BorealOutriderEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Spell spell = (Spell) getValue("spellCast");
        return spell != null && event.getTargetId().equals(spell.getCard().getId());
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent creature = ((EntersTheBattlefieldEvent) event).getTarget();
        if (creature != null) {
            creature.addCounters(CounterType.P1P1.createInstance(), source.getControllerId(), source, game, event.getAppliedEffects());
            discard();
        }
        return false;
    }

    @Override
    public BorealOutriderEffect copy() {
        return new BorealOutriderEffect(this);
    }
}
