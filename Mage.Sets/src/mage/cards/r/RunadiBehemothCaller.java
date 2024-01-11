package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.FilterSpell;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;

import java.util.UUID;

/**
 *
 * @author @stwalsh4118
 */
public final class RunadiBehemothCaller extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("creatures with three or more +1/+1 counters on them");

    private static final FilterSpell filterSpell = new FilterSpell("a creature spell with mana value 5 or greater");

    static {
        filter.add(RunadiBehemothCallerPredicate.instance);
        filterSpell.add(CardType.CREATURE.getPredicate());
        filterSpell.add(new ManaValuePredicate(ComparisonType.OR_GREATER, 5));
    }

    public RunadiBehemothCaller(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");
        
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Whenever you cast a creature spell with mana value 5 or greater, that creature enters the battlefield with X additional +1/+1 counters on it, where X is its mana value minus 4.
        this.addAbility(new SpellCastControllerTriggeredAbility(new RunadiBehemothCallerCounterEffect(), filterSpell,
                false, SetTargetPointer.SPELL));

        // Creature you control with three or more +1/+1 counters on them have haste.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                HasteAbility.getInstance(), Duration.WhileOnBattlefield, filter
        ).setText("creatures you control with three or more +1/+1 counters on them have haste")));
        
        // {T}: Add {G}.
        this.addAbility(new GreenManaAbility());

    }

    private RunadiBehemothCaller(final RunadiBehemothCaller card) {
        super(card);
    }

    @Override
    public RunadiBehemothCaller copy() {
        return new RunadiBehemothCaller(this);
    }
}

class RunadiBehemothCallerCounterEffect extends ReplacementEffectImpl {

    RunadiBehemothCallerCounterEffect() {
        super(Duration.EndOfTurn, Outcome.BoostCreature);
    }

    private RunadiBehemothCallerCounterEffect(final RunadiBehemothCallerCounterEffect effect) {
        super(effect);
    }

    @Override
    public RunadiBehemothCallerCounterEffect copy() {
        return new RunadiBehemothCallerCounterEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Spell spell = game.getSpellOrLKIStack(getTargetPointer().getFirst(game, source));
        return spell != null && event.getTargetId().equals(spell.getSourceId());
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent creature = ((EntersTheBattlefieldEvent) event).getTarget();
        if (creature != null) {
            Spell spell = game.getSpellOrLKIStack(creature.getId());
            if (spell != null) {
                int countersToAdd = spell.getManaValue() - 4;
                creature.addCounters(CounterType.P1P1.createInstance(countersToAdd),
                        source.getControllerId(), source, game, event.getAppliedEffects());
            }
        }
        return false;
    }
}

enum RunadiBehemothCallerPredicate implements Predicate<Permanent> {
    instance;

    @Override
    public boolean apply(Permanent input, Game game) {
        return input.getCounters(game).getCount(CounterType.P1P1) >= 3;
    }

    @Override
    public String toString() {
        return "with three or more +1/+1 counters on them";
    }
}
