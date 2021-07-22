package mage.cards.b;

import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BeamingDefiance extends CardImpl {

    public BeamingDefiance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Target creature you control gets +2/+2 and gains hexproof until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(
                2, 2, Duration.EndOfTurn
        ).setText("target creature you control gets +2/+2"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(
                HexproofAbility.getInstance(), Duration.EndOfTurn
        ).setText("and gains hexproof until end of turn"));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
    }

    private BeamingDefiance(final BeamingDefiance card) {
        super(card);
    }

    @Override
    public BeamingDefiance copy() {
        return new BeamingDefiance(this);
    }
}
