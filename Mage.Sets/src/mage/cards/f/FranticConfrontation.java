package mage.cards.f;

import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FranticConfrontation extends CardImpl {

    public FranticConfrontation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{R}");

        // Target creature you control gets +X/+0 and gains first strike and trample until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(GetXValue.instance, StaticValue.get(0))
                .setText("target creature you control gets +X/+0"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(FirstStrikeAbility.getInstance())
                .setText("and gains first strike"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(TrampleAbility.getInstance())
                .setText("and trample until end of turn"));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
    }

    private FranticConfrontation(final FranticConfrontation card) {
        super(card);
    }

    @Override
    public FranticConfrontation copy() {
        return new FranticConfrontation(this);
    }
}
