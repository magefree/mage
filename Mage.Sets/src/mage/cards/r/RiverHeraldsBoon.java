
package mage.cards.r;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.SecondTargetPointer;

/**
 *
 * @author LevelX2
 */
public final class RiverHeraldsBoon extends CardImpl {

    public RiverHeraldsBoon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");

        // Put a +1/+1 counter on target creature and a +1/+1 counter on up to one target Merfolk.
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        Effect effect = new AddCountersTargetEffect(CounterType.P1P1.createInstance());
        effect.setTargetPointer(new SecondTargetPointer());
        effect.setText("and a +1/+1 counter on up to one target Merfolk");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 1, new FilterCreaturePermanent(SubType.MERFOLK, "Merfolk"), false));

    }

    private RiverHeraldsBoon(final RiverHeraldsBoon card) {
        super(card);
    }

    @Override
    public RiverHeraldsBoon copy() {
        return new RiverHeraldsBoon(this);
    }
}
