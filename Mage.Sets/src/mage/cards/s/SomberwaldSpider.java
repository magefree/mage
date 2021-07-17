
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.common.MorbidCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.hint.common.MorbidHint;
import mage.abilities.keyword.ReachAbility;
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
public final class SomberwaldSpider extends CardImpl {

    public SomberwaldSpider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}");
        this.subtype.add(SubType.SPIDER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        this.addAbility(ReachAbility.getInstance());
        // <i>Morbid</i> &mdash; Somberwald Spider enters the battlefield with two +1/+1 counters on it if a creature died this turn.
        this.addAbility(new EntersBattlefieldAbility(
                new ConditionalOneShotEffect(new AddCountersSourceEffect(CounterType.P1P1.createInstance(2)), MorbidCondition.instance, ""),
                "with two +1/+1 counters on it if a creature died this turn").addHint(MorbidHint.instance).setAbilityWord(AbilityWord.MORBID));
    }

    private SomberwaldSpider(final SomberwaldSpider card) {
        super(card);
    }

    @Override
    public SomberwaldSpider copy() {
        return new SomberwaldSpider(this);
    }
}
