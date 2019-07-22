package mage.cards.m;

import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author nantuko
 */
public final class MomentOfHeroism extends CardImpl {

    public MomentOfHeroism(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Target creature gets +2/+2 and gains lifelink until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(
                2, 2, Duration.EndOfTurn
        ).setText("target creature gets +2/+2"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(
                LifelinkAbility.getInstance(), Duration.EndOfTurn
        ).setText("and gains lifelink until end of turn"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    public MomentOfHeroism(final MomentOfHeroism card) {
        super(card);
    }

    @Override
    public MomentOfHeroism copy() {
        return new MomentOfHeroism(this);
    }
}
