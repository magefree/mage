package mage.abilities.common;

import java.util.List;

import mage.abilities.Ability;
import mage.abilities.AbilityImpl;
import mage.abilities.ActivatedAbility;
import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.Modes;
import mage.abilities.StaticAbility;
import mage.abilities.TriggeredAbility;
import mage.abilities.condition.CompoundCondition;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SolvedSourceCondition;
import mage.abilities.condition.common.UnsolvedSourceCondition;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.decorator.ConditionalAsThoughEffect;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.AsThoughEffect;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.Effects;
import mage.abilities.effects.OneShotEffect;
import mage.constants.EffectType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;
import mage.watchers.Watcher;

/**
 * The Case mechanic was added in Murders at Karlov Manor [MKM].
 * Case is a subtype of an enchantment. While on the battlefield,
 * the Case can be in one of two states: unsolved or solved.
 * Upon entering the battlefield, its state will be unsolved.
 * <br>
 * Each Case has three abilities:
 * 1. An ETB trigger or static ability (whether unsolved or solved).
 * 2. A conditional end of turn triggered ability that changes to state to be solved.
 * 3. A triggered, activated or static ability (only when solved).
 *
 * @author DominionSpy
 */
public class CaseAbility extends SimpleStaticAbility {

    public CaseAbility(Ability initialAbility, Condition toSolveCondition, Ability solvedAbility) {
        super(Zone.ALL, null);

        if (initialAbility instanceof TriggeredAbility) {
            addSubAbility(new CaseTriggeredAbility((TriggeredAbility) initialAbility));
        } else if (initialAbility instanceof ActivatedAbility) {
            addSubAbility(new CaseActivatedAbility((ActivatedAbility) initialAbility));
        } else if (initialAbility instanceof StaticAbility) {
            addSubAbility(new CaseStaticAbility((StaticAbility) initialAbility));
        } else {
            throw new IllegalArgumentException("Initial Case ability supports only triggered, activated and static abilities");
        }

        addSubAbility(new BeginningOfEndStepTriggeredAbility(
                new SolveEffect(), TargetController.YOU,
                new CompoundCondition(UnsolvedSourceCondition.instance, toSolveCondition),
                false)
                .setTriggerPhrase("To solve &mdash; " +
                        CardUtil.getTextWithFirstCharUpperCase(removeIf(toSolveCondition.toString())) +
                        ". <i>(If unsolved, solve at the beginning of your end step.)</i>"));

        if (solvedAbility instanceof TriggeredAbility) {
            addSubAbility(new CaseTriggeredAbility((TriggeredAbility) solvedAbility, true));
        } else if (solvedAbility instanceof ActivatedAbility) {
            addSubAbility(new CaseActivatedAbility((ActivatedAbility) solvedAbility, true));
        } else if (solvedAbility instanceof StaticAbility) {
            addSubAbility(new CaseStaticAbility((StaticAbility) solvedAbility, true));
        } else {
            throw new IllegalArgumentException("Solved Case ability supports only triggered, activated and static abilities");
        }
    }

    protected CaseAbility(final CaseAbility ability) {
        super(ability);
    }

    @Override
    public CaseAbility copy() {
        return new CaseAbility(this);
    }

    private static String removeIf(String text) {
        if (text.startsWith("if ")) {
            return text.substring(3);
        }
        return text;
    }
}

enum TrueCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }
}

class CaseTriggeredAbility extends ConditionalTriggeredAbility {

    CaseTriggeredAbility(TriggeredAbility ability) {
        this(ability, false);
    }

    CaseTriggeredAbility(TriggeredAbility ability, boolean solved) {
        super(ability, solved ? SolvedSourceCondition.instance : TrueCondition.instance,
                (solved ? "Solved &mdash; " : "") + CardUtil.getTextWithFirstCharUpperCase(ability.getRule("this Case")));
    }

    private CaseTriggeredAbility(final CaseTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public CaseTriggeredAbility copy() {
        return new CaseTriggeredAbility(this);
    }
}

class CaseActivatedAbility extends ConditionalActivatedAbility {

    CaseActivatedAbility(ActivatedAbility ability) {
        this(ability, false);
    }

    CaseActivatedAbility(ActivatedAbility ability, boolean solved) {
        super(ability.getZone(), ability.getEffects().get(0), ability.getCosts(),
                solved ? SolvedSourceCondition.instance : TrueCondition.instance,
                (solved ? "Solved &mdash; " : "") + CardUtil.getTextWithFirstCharUpperCase(ability.getRule("this Case")));
    }

    private CaseActivatedAbility(final CaseActivatedAbility ability) {
        super(ability);
    }

    @Override
    public CaseActivatedAbility copy() {
        return new CaseActivatedAbility(this);
    }
}

class CaseActivatedAbility2 extends ActivatedAbilityImpl {

    private ActivatedAbility ability;
    private Condition condition;

    CaseActivatedAbility2(ActivatedAbility ability) {
        this(ability, false);
    }

    CaseActivatedAbility2(ActivatedAbility ability, boolean solved) {
        super(ability.getAbilityType(), ability.getZone());
        this.ability = ability;
        this.condition = solved ? SolvedSourceCondition.instance : TrueCondition.instance;
    }

    private CaseActivatedAbility2(final CaseActivatedAbility2 ability) {
        super(ability);
    }

    @Override
    public CaseActivatedAbility2 copy() {
        return new CaseActivatedAbility2(this);
    }

    @Override
    public Effects getEffects() {
        return ability.getEffects();
    }

    @Override
    public void addEffect(Effect effect) {
        ability.addEffect(effect);
    }

    @Override
    public Modes getModes() {
        return ability.getModes();
    }

    @Override
    public List<Watcher> getWatchers() {
        return ability.getWatchers();
    }

    @Override
    public void addWatcher(Watcher watcher) {
        ability.addWatcher(watcher);
    }

    @Override
    public Effects getEffects(Game game, EffectType effectType) {
        return ability.getEffects(game, effectType);
    }
}

class CaseStaticAbility extends AbilityImpl {

    private final Ability ability;

    CaseStaticAbility(Ability ability) {
        this(ability, false);
    }

    CaseStaticAbility(Ability ability, boolean solved) {
        super(ability.getAbilityType(), ability.getZone());
        this.ability = ability;
    }

    private CaseStaticAbility(final CaseStaticAbility ability) {
        super(ability);
        this.ability = ability.ability.copy();
    }

    @Override
    public CaseStaticAbility copy() {
        return new CaseStaticAbility(this);
    }

    @Override
    public String getRule(String source) {
        return super.getRule("this Case");
    }

    @Override
    public Effects getEffects() {
        return ability.getEffects();
    }

    @Override
    public void addEffect(Effect effect) {
        ability.addEffect(effect);
    }

    @Override
    public Modes getModes() {
        return ability.getModes();
    }

    @Override
    public List<Watcher> getWatchers() {
        return ability.getWatchers();
    }

    @Override
    public void addWatcher(Watcher watcher) {
        ability.addWatcher(watcher);
    }

    @Override
    public Effects getEffects(Game game, EffectType effectType) {
        return ability.getEffects(game, effectType);
    }
}

class SolveEffect extends OneShotEffect {

    SolveEffect() {
        super(Outcome.Benefit);
    }

    private SolveEffect(final SolveEffect effect) {
        super(effect);
    }

    @Override
    public SolveEffect copy() {
        return new SolveEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent == null || permanent.isSolved()) {
            return false;
        }
        permanent.setSolved(true, game, source);
        return true;
    }
}
