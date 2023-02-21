package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.events.EntersTheBattlefieldEvent;

/**
 *
 * @author @stwalsh4118
 */
public final class RunadiBehemothCaller extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("creatures with three or more +1/+1 counters on them");

    static {
        filter.add(P1P1CounterAmountPredicate.instance);
    }

    public RunadiBehemothCaller(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");
        
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Whenever you cast a creature spell with mana value 5 or greater, that creature enters the battlefield with X additional +1/+1 counters on it, where X is its mana value minus 4.
        this.addAbility(new RunadiBehemothCallerTriggeredAbility());

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

class RunadiBehemothCallerTriggeredAbility extends TriggeredAbilityImpl {

    public RunadiBehemothCallerTriggeredAbility() {
        super(Zone.BATTLEFIELD, null, false);
    }

    public RunadiBehemothCallerTriggeredAbility(final RunadiBehemothCallerTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public RunadiBehemothCallerTriggeredAbility copy() {
        return new RunadiBehemothCallerTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.CAST_SPELL;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getPlayerId().equals(this.getControllerId())) {
            Spell spell = game.getStack().getSpell(event.getTargetId());
            if (spell != null && spell.isCreature()) {
                int manaValue = spell.getManaValue();
                if (manaValue >= 5) {
                    // spell.addAbilityForCopy(new EntersBattlefieldAbility(new AddCountersSourceEffect()));
                    this.addEffect(new RunadiBehemothCallerCounterEffect(spell.getSourceId()));
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever you cast a creature spell with mana value 5 or greater, that creature enters the battlefield with X additional +1/+1 counters on it, where X is its mana value minus 4.";
    }
}

class RunadiBehemothCallerCounterEffect extends ReplacementEffectImpl {

    private final UUID spellCastId;

    public RunadiBehemothCallerCounterEffect(UUID spellCastId) {
        super(Duration.EndOfTurn, Outcome.BoostCreature);
        this.spellCastId = spellCastId;
    }

    private RunadiBehemothCallerCounterEffect(final RunadiBehemothCallerCounterEffect effect) {
        super(effect);
        this.spellCastId = effect.spellCastId;
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
        return spellCastId.equals(event.getTargetId());
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent creature = ((EntersTheBattlefieldEvent) event).getTarget();
        if (creature != null) {
            creature.addCounters(CounterType.P1P1.createInstance(creature.getManaValue() - 4), source.getControllerId(), source, game, event.getAppliedEffects());
        }
        return false;
    }
}


enum P1P1CounterAmountPredicate implements Predicate<Permanent> {
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
