
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.common.MorbidCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.hint.common.MorbidHint;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

/**
 *
 * @author Loki
 */
public final class GravetillerWurm extends CardImpl {

    public GravetillerWurm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{G}");
        this.subtype.add(SubType.WURM);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        this.addAbility(TrampleAbility.getInstance());
        // <i>Morbid</i> &mdash; Gravetiller Wurm enters the battlefield with four +1/+1 counters on it if a creature died this turn.
        this.addAbility(new EntersBattlefieldAbility(new ConditionalOneShotEffect(new AddCountersSourceEffect(CounterType.P1P1.createInstance(4)),
                MorbidCondition.instance, ""), "with four +1/+1 counters on it if a creature died this turn").addHint(MorbidHint.instance).setAbilityWord(AbilityWord.MORBID));
    }

    private GravetillerWurm(final GravetillerWurm card) {
        super(card);
    }

    @Override
    public GravetillerWurm copy() {
        return new GravetillerWurm(this);
    }
}
