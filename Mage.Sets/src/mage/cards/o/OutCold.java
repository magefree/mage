package mage.cards.o;

import mage.abilities.common.CantBeCounteredSourceAbility;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OutCold extends CardImpl {

    public OutCold(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{U}");

        // This spell can't be countered.
        this.addAbility(new CantBeCounteredSourceAbility());

        // Tap up to two target creatures and put a stun counter on each of them. Investigate.
        this.getSpellAbility().addEffect(new TapTargetEffect());
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.STUN.createInstance()).setText("and put a stun counter on each of them"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 2));
        this.getSpellAbility().addEffect(new InvestigateEffect());
    }

    private OutCold(final OutCold card) {
        super(card);
    }

    @Override
    public OutCold copy() {
        return new OutCold(this);
    }
}
