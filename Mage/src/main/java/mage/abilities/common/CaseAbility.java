package mage.abilities.common;

import mage.abilities.Ability;
import mage.abilities.condition.CompoundCondition;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SolvedSourceCondition;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.decorator.ConditionalAsThoughEffect;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.decorator.ConditionalReplacementEffect;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

/**
 * The Case mechanic was added in Murders at Karlov Manor [MKM].
 * <ul>
 * <li>Each Case has two special keyword abilities: to solve and solved.</li>
 * <li>"To Solve — [condition]" means "At the beginning of your end step,
 * if [condition] and this Case is not solved, it becomes solved."</li>
 * <li>The meaning of "solved" differs based on what type of ability follows it.
 * "Solved — [activated ability]" means "[Activated ability].
 * Activate only if this Case is solved." Activated abilities contain a colon.
 * They're generally written "[Cost]: [Effect]."</li>
 * <li>"Solved — [Triggered ability]" means "[Triggered ability].
 * This ability triggers only if this Case is solved."
 * Triggered abilities use the word "when," "whenever," or "at."
 * They're often written as "[Trigger condition], [effect]."</li>
 * <li>"Solved — [static ability]" means "As long as this Case is solved, [static ability]."
 * Static abilities are written as statements, such as "Creatures you control get +1/+1"
 * or "Instant and sorcery spells you cast cost {1} less to cast."</li>
 * <li>"To solve" abilities will check for their condition twice:
 * once when the ability would trigger, and once when it resolves.
 * If the condition isn't true at the beginning of your end step,
 * the ability won't trigger at all.
 * If the condition isn't true when the ability resolves, the Case won't become solved.</li>
 * <li>Once a Case becomes solved, it stays solved until it leaves the battlefield.</li>
 * <li>Cases don't lose their other abilities when they become solved.</li>
 * <li>Being solved is not part of a permanent's copiable values.
 * A permanent that becomes a copy of a solved Case is not solved.
 * A solved Case that somehow becomes a copy of a different Case stays solved.</li>
 * </ul>
 *
 * @author DominionSpy
 */
public class CaseAbility extends SimpleStaticAbility {

    /**
     * Constructs a Case with three abilities:
     * <ul>
     *     <li>A initial ability the Case has at all times</li>
     *     <li>A "To solve" ability that will conditionally solve the Case
     *     at the beginning of the controller's end step</li>
     *     <li>A "Solved" ability the Case has when solved</li>
     * </ul>
     * The "Solved" ability must be one of the following:
     * <ul>
     *     <li>{@link ConditionalActivatedAbility} using the condition {@link SolvedSourceCondition}.SOLVED</li>
     *     <li>{@link ConditionalTriggeredAbility} using the condition {@link SolvedSourceCondition}.SOLVED</li>
     *     <li>{@link SimpleStaticAbility} with only {@link ConditionalAsThoughEffect} or {@link ConditionalContinuousEffect} effects</li>
     * </ul>
     *
     * @param initialAbility The ability that a Case has at all times
     * @param toSolveCondition The condition to be checked when solving
     * @param solvedAbility The ability that a solved Case has
     */
    public CaseAbility(Ability initialAbility, Condition toSolveCondition, Ability solvedAbility) {
        super(Zone.ALL, null);

        if (initialAbility instanceof EntersBattlefieldTriggeredAbility) {
            ((EntersBattlefieldTriggeredAbility) initialAbility).setTriggerPhrase("When this Case enters the battlefield, ");
        }
        addSubAbility(initialAbility);

        addSubAbility(new CaseSolveAbility(toSolveCondition));

        if (solvedAbility instanceof ConditionalActivatedAbility) {
            ((ConditionalActivatedAbility) solvedAbility).hideCondition();
        } else if (!(solvedAbility instanceof ConditionalTriggeredAbility)) {
            if (solvedAbility instanceof SimpleStaticAbility) {
                for (Effect effect : solvedAbility.getEffects()) {
                    if (!(effect instanceof ConditionalContinuousEffect ||
                            effect instanceof ConditionalAsThoughEffect ||
                            effect instanceof ConditionalReplacementEffect)) {
                        throw new IllegalArgumentException("Wrong code usage: solvedAbility must be one of ConditionalActivatedAbility, " +
                                "ConditionalTriggeredAbility, or StaticAbility with conditional effects.");
                    }
                }
            } else {
                throw new IllegalArgumentException("Wrong code usage: solvedAbility must be one of ConditionalActivatedAbility, " +
                        "ConditionalTriggeredAbility, or StaticAbility with conditional effects.");
            }
        }
        addSubAbility(solvedAbility.withFlavorWord("Solved")); // TODO: Technically this shouldn't be italicized
    }

    protected CaseAbility(final CaseAbility ability) {
        super(ability);
    }

    @Override
    public CaseAbility copy() {
        return new CaseAbility(this);
    }

}

class CaseSolveAbility extends BeginningOfEndStepTriggeredAbility {

    CaseSolveAbility(Condition condition) {
        super(new SolveEffect(), TargetController.YOU,
                new CompoundCondition(condition, SolvedSourceCondition.UNSOLVED), false);
        withFlavorWord("To solve"); // TODO: technically this shouldn't be italicized
        setTriggerPhrase(CardUtil.getTextWithFirstCharUpperCase(trimIf(condition.toString())));
    }

    private CaseSolveAbility(final CaseSolveAbility ability) {
        super(ability);
    }

    @Override
    public CaseSolveAbility copy() {
        return new CaseSolveAbility(this);
    }

    @Override
    public String getRule() {
        return super.getRule() + ". <i>(If unsolved, solve at the beginning of your end step.)</i>";
    }

    private static String trimIf(String text) {
        if (text.startsWith("if ")) {
            return text.substring(3);
        }
        return text;
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
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null || permanent.isSolved()) {
            return false;
        }
        return permanent.solve(game, source);
    }
}
