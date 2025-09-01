package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.PreventAllDamageByAllPermanentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;

import java.util.UUID;

/**
 * @author shieldal
 */
public final class AngusMackenzie extends CardImpl {

    public AngusMackenzie(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{W}{U}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {G}{W}{U}, {tap}: Prevent all combat damage that would be dealt this turn. Activate this ability only before the combat damage step.
        Ability ability = new ActivateIfConditionActivatedAbility(
                new PreventAllDamageByAllPermanentsEffect(Duration.EndOfTurn, true)
                        .setText("Prevent all combat damage that would be dealt this turn"),
                new ManaCostsImpl<>("{G}{W}{U}"), BeforeCombatDamageCondition.getInstance()
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private AngusMackenzie(final AngusMackenzie card) {
        super(card);
    }

    @Override
    public AngusMackenzie copy() {
        return new AngusMackenzie(this);
    }
}

class BeforeCombatDamageCondition implements Condition {
    private static final BeforeCombatDamageCondition instance = new BeforeCombatDamageCondition();

    public static Condition getInstance() {
        return instance;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        PhaseStep phaseStep = game.getTurnStepType();
        if (phaseStep.getIndex() < PhaseStep.FIRST_COMBAT_DAMAGE.getIndex()) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "before the combat damage step";
    }
}
