package mage.cards.o;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.common.CounterTargetEffect;
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
 * @author xenohedron
 */
public final class OutOfAir extends CardImpl {

    public OutOfAir(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}{U}");

        // This spell costs {2} less to cast if it targets a creature spell.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SpellCostReductionSourceEffect(2, OutOfAirCondition.instance).setCanWorksOnStackOnly(true)
        ).setRuleAtTheTop(true));

        // Counter target spell.
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetSpell());

    }

    private OutOfAir(final OutOfAir card) {
        super(card);
    }

    @Override
    public OutOfAir copy() {
        return new OutOfAir(this);
    }
}

enum OutOfAirCondition implements Condition {
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
                .anyMatch(spell -> spell != null && spell.isCreature(game));
    }

    @Override
    public String toString() {
        return "it targets a creature spell";
    }

}
