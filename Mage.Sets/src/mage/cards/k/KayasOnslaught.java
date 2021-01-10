package mage.cards.k;

import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.ForetellAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KayasOnslaught extends CardImpl {

    public KayasOnslaught(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}");

        // Target creature gets +1/+1 and gains double strike until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(
                1, 1, Duration.EndOfTurn
        ).setText("target creature gets +1/+1"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(
                DoubleStrikeAbility.getInstance(), Duration.EndOfTurn
        ).setText("and gains double strike until end of turn"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Foretell {W}
        this.addAbility(new ForetellAbility(this, "{W}"));
    }

    private KayasOnslaught(final KayasOnslaught card) {
        super(card);
    }

    @Override
    public KayasOnslaught copy() {
        return new KayasOnslaught(this);
    }
}
