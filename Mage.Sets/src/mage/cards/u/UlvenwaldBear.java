
package mage.cards.u;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.MorbidCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.hint.common.MorbidHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Loki
 */
public final class UlvenwaldBear extends CardImpl {

    public UlvenwaldBear(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.BEAR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // <i>Morbid</i> &mdash; When Ulvenwald Bear enters the battlefield, if a creature died this turn, put two +1/+1 counters on target creature.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(new EntersBattlefieldTriggeredAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance(2), Outcome.BoostCreature)),
                MorbidCondition.instance, "When {this} enters the battlefield, if a creature died this turn, put two +1/+1 counters on target creature.");
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability.addHint(MorbidHint.instance).setAbilityWord(AbilityWord.MORBID));
    }

    private UlvenwaldBear(final UlvenwaldBear card) {
        super(card);
    }

    @Override
    public UlvenwaldBear copy() {
        return new UlvenwaldBear(this);
    }
}
