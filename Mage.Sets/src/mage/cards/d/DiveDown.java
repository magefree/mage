package mage.cards.d;

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
public final class DiveDown extends CardImpl {

    public DiveDown(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}");

        // Target creature you control gets +0/+3 and gains hexproof until end of turn. 
        this.getSpellAbility().addEffect(new BoostTargetEffect(
                0, 3, Duration.EndOfTurn
        ).setText("Target creature you control gets +0/+3"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(
                HexproofAbility.getInstance(), Duration.EndOfTurn
        ).setText("and gains hexproof until end of turn"));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
    }

    private DiveDown(final DiveDown card) {
        super(card);
    }

    @Override
    public DiveDown copy() {
        return new DiveDown(this);
    }
}
