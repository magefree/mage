package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.stack.StackObject;
import mage.target.Target;
import mage.target.TargetSpell;

import java.util.Collection;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MysticalDispute extends CardImpl {

    public MysticalDispute(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}");

        // This spell costs {2} less to cast if it targets a blue spell.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SpellCostReductionSourceEffect(2, MysticalDisputeCondition.instance).setCanWorksOnStackOnly(true)
        ).setRuleAtTheTop(true));

        // Counter target spell unless its controller pays {3}.
        this.getSpellAbility().addEffect(new CounterUnlessPaysEffect(new GenericManaCost(3)));
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    private MysticalDispute(final MysticalDispute card) {
        super(card);
    }

    @Override
    public MysticalDispute copy() {
        return new MysticalDispute(this);
    }
}

enum MysticalDisputeCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        StackObject sourceSpell = game.getStack().getStackObject(source.getSourceId());
        if (sourceSpell == null) {
            return false;
        }
        return sourceSpell
                .getStackAbility()
                .getTargets()
                .stream()
                .map(Target::getTargets)
                .flatMap(Collection::stream)
                .map(game::getSpell)
                .anyMatch(spell -> spell != null && spell.getColor(game).isBlue());
    }

    @Override
    public String toString() {
        return "it targets a blue spell";
    }

}
