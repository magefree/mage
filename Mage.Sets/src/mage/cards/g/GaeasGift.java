package mage.cards.g;

import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GaeasGift extends CardImpl {

    public GaeasGift(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");

        // Put a +1/+1 counter on target creature you control. It gains reach, trample, hexproof, and indestructible until end of turn.
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(ReachAbility.getInstance()).setText("it gains reach"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(TrampleAbility.getInstance()).setText(", trample"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(HexproofAbility.getInstance()).setText(", hexproof"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(IndestructibleAbility.getInstance()).setText(", and indestructible until end of turn"));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
    }

    private GaeasGift(final GaeasGift card) {
        super(card);
    }

    @Override
    public GaeasGift copy() {
        return new GaeasGift(this);
    }
}
