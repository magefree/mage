package mage.cards.h;

import java.util.UUID;

import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author muz
 */
public final class HamatoGuardianStance extends CardImpl {

    public HamatoGuardianStance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}");

        // Target creature gets +1/+3 and gains flying until end of turn. Scry 1.
        this.getSpellAbility().addEffect(new BoostTargetEffect(1, 3)
            .setText("target creature gets +1/+3"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(FlyingAbility.getInstance())
            .setText("and gains flying until end of turn"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new ScryEffect(1).setText("Scry 1"));
    }

    private HamatoGuardianStance(final HamatoGuardianStance card) {
        super(card);
    }

    @Override
    public HamatoGuardianStance copy() {
        return new HamatoGuardianStance(this);
    }
}
