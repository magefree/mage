package mage.cards.s;

import mage.abilities.dynamicvalue.common.PartyCount;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.hint.common.PartyCountHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StrengthOfSolidarity extends CardImpl {

    public StrengthOfSolidarity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{G}");

        // Choose target creature you control. Put a +1/+1 counter on it for each creature in your party.
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(
                CounterType.P1P1.createInstance(0), PartyCount.instance
        ).setText("Choose target creature you control. Put a +1/+1 counter on it for each creature in your party. " + PartyCount.getReminder()));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addHint(PartyCountHint.instance);
    }

    private StrengthOfSolidarity(final StrengthOfSolidarity card) {
        super(card);
    }

    @Override
    public StrengthOfSolidarity copy() {
        return new StrengthOfSolidarity(this);
    }
}
