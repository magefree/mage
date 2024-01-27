package mage.abilities.common;

import mage.abilities.Ability;
import mage.abilities.condition.CompoundCondition;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SolvedSourceCondition;
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

    public CaseAbility(Ability initialAbility, Condition toSolveCondition, Ability solvedAbility) {
        super(Zone.ALL, null);

        addSubAbility(initialAbility.withNameReplacement("this Case"));

        addSubAbility(new CaseSolveAbility(toSolveCondition));

        addSubAbility(solvedAbility.withNameReplacement("this Case")
                .withFlavorWord("Solved"));
    }

    protected CaseAbility(final CaseAbility ability) {
        super(ability);
    }

    @Override
    public CaseAbility copy() {
        return new CaseAbility(this);
    }

    @Override
    public String getRule() {
        return super.getRule().replace("{this}", "this Case");
    }
}

class CaseSolveAbility extends BeginningOfEndStepTriggeredAbility {

    CaseSolveAbility(Condition condition) {
        super(new SolveEffect(), TargetController.YOU,
                new CompoundCondition(condition, SolvedSourceCondition.UNSOLVED), false);
        withFlavorWord("To solve &mdash; ");
        setTriggerPhrase(CardUtil.getTextWithFirstCharUpperCase(removeIf(condition.toString())));
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

    private static String removeIf(String text) {
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
