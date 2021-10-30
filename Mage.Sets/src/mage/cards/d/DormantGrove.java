package mage.cards.d;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author weirddan455
 */
public final class DormantGrove extends CardImpl {

    public DormantGrove(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{G}");

        this.transformable = true;
        this.secondSideCardClazz = mage.cards.g.GnarledGrovestrider.class;

        // At the beginning of combat on your turn, put a +1/+1 counter on target creature you control.
        // Then if that creature has toughness 6 or greater, transform Dormant Grove.
        this.addAbility(new TransformAbility());

        Ability ability = new BeginningOfCombatTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance()),
                TargetController.YOU, false
        );
        ability.addEffect(new ConditionalOneShotEffect(
                new TransformSourceEffect(true, true),
                DormatGroveCondition.instance,
                "Then if that creature has toughness 6 or greater, transform {this}"
        ));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private DormantGrove(final DormantGrove card) {
        super(card);
    }

    @Override
    public DormantGrove copy() {
        return new DormantGrove(this);
    }
}

enum DormatGroveCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent != null) {
            return permanent.getToughness().getValue() >= 6;
        }
        return false;
    }

    @Override
    public String toString() {
        return "that creature has toughness 6 or greater";
    }
}
