
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.PreventAllDamageByAllPermanentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;

/**
 *
 * @author shieldal
 */
public final class AngusMackenzie extends CardImpl {

    public AngusMackenzie(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}{W}{U}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        Effect effect = new PreventAllDamageByAllPermanentsEffect(Duration.EndOfTurn, true);
        effect.setText("Prevent all combat damage that would be dealt this turn");
        // {G}{W}{U}, {tap}: Prevent all combat damage that would be dealt this turn. Activate this ability only before the combat damage step.
        Ability ability = new ActivateIfConditionActivatedAbility(
                Zone.BATTLEFIELD, 
                effect,
                new ManaCostsImpl<>("{G}{W}{U}"), 
                BeforeCombatDamageCondition.getInstance()
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
            if(phaseStep.getIndex() < PhaseStep.FIRST_COMBAT_DAMAGE.getIndex()) {
                return true;
            }
        return false;
    }

    @Override
    public String toString() {
	return "before the combat damage step";
    }
}