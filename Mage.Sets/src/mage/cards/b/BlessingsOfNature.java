package mage.cards.b;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.counter.DistributeCountersEffect;
import mage.abilities.keyword.MiracleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanentAmount;

import java.util.UUID;

/**
 * @author North
 */
public final class BlessingsOfNature extends CardImpl {

    public BlessingsOfNature(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{G}");

        // Distribute four +1/+1 counters among any number of target creatures.
        this.getSpellAbility().addEffect(new DistributeCountersEffect(CounterType.P1P1, 4, false, "any number of target creatures"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanentAmount(4));

        this.addAbility(new MiracleAbility(this, new ManaCostsImpl<>("{G}")));
    }

    private BlessingsOfNature(final BlessingsOfNature card) {
        super(card);
    }

    @Override
    public BlessingsOfNature copy() {
        return new BlessingsOfNature(this);
    }
}
