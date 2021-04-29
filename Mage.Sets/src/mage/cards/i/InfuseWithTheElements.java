
package mage.cards.i;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.ColorsOfManaSpentToCastCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class InfuseWithTheElements extends CardImpl {

    public InfuseWithTheElements(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{G}");

        // <i>Converge</i> &mdash; Put X +1/+1 counters on target creature, where X is the number of colors of mana spent to cast Infuse with the Elements.
        this.getSpellAbility().setAbilityWord(AbilityWord.CONVERGE);
        Effect effect = new AddCountersTargetEffect(CounterType.P1P1.createInstance(0), ColorsOfManaSpentToCastCount.getInstance());
        effect.setText("Put X +1/+1 counters on target creature, where X is the number of colors of mana spent to cast this spell");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        // That creature gains trample until end of turn.
        effect = new GainAbilityTargetEffect(TrampleAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("That creature gains trample until end of turn");
        this.getSpellAbility().addEffect(effect);
    }

    private InfuseWithTheElements(final InfuseWithTheElements card) {
        super(card);
    }

    @Override
    public InfuseWithTheElements copy() {
        return new InfuseWithTheElements(this);
    }
}
