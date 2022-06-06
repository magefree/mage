package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GrizzledWolverine extends CardImpl {

    public GrizzledWolverine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{R}");

        this.subtype.add(SubType.WOLVERINE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {R}: Grizzled Wolverine gets +2/+0 until end of turn. Activate this ability only during the declare blockers step, only if at least one creature is blocking Grizzled Wolverine, and only once each turn.
        this.addAbility(new LimitedTimesPerTurnActivatedAbility(
                Zone.BATTLEFIELD, new BoostSourceEffect(2, 0, Duration.EndOfTurn),
                new ManaCostsImpl<>("{R}"), 1, GrizzledWolverineCondition.instance
        ));
    }

    private GrizzledWolverine(final GrizzledWolverine card) {
        super(card);
    }

    @Override
    public GrizzledWolverine copy() {
        return new GrizzledWolverine(this);
    }
}

enum GrizzledWolverineCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        if (game.getPhase().getStep().getType() != PhaseStep.DECLARE_BLOCKERS) {
            return false;
        }
        return game
                .getCombat()
                .getGroups()
                .stream()
                .anyMatch(combatGroup -> combatGroup.getAttackers().contains(source.getSourceId())
                        && !combatGroup.getBlockers().isEmpty());
    }

    @Override
    public String toString() {
        return "during the declare blockers step, only if at least one creature is blocking {this},";
    }
}