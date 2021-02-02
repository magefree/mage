package mage.cards.g;

import java.util.UUID;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class GirdForBattle extends CardImpl {

    public GirdForBattle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{W}");

        // Put a +1/+1 counter on each of up to two target creatures.
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(
                CounterType.P1P1.createInstance()
        ).setText("Put a +1/+1 counter on each of up to two target creatures"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 2));
    }

    private GirdForBattle(final GirdForBattle card) {
        super(card);
    }

    @Override
    public GirdForBattle copy() {
        return new GirdForBattle(this);
    }
}
