package mage.cards.b;

import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.keyword.TheRingTemptsYouEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BombadilsSong extends CardImpl {

    public BombadilsSong(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");

        // Target creature you control gets +1/+1 and gains hexproof until the end of turn. The Ring tempts you.
        this.getSpellAbility().addEffect(new BoostTargetEffect(1, 1)
                .setText("target creature you control gets +1/+1"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(HexproofAbility.getInstance())
                .setText("and gains hexproof until end of turn"));
        this.getSpellAbility().addEffect(new TheRingTemptsYouEffect());
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
    }

    private BombadilsSong(final BombadilsSong card) {
        super(card);
    }

    @Override
    public BombadilsSong copy() {
        return new BombadilsSong(this);
    }
}
