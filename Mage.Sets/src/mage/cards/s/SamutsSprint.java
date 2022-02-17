package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author TheElk801
 */
public final class SamutsSprint extends CardImpl {

    public SamutsSprint(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}");

        // Target creature gets +2/+1 and gains haste until end of turn. Scry 1.
        this.getSpellAbility().addEffect(new BoostTargetEffect(
                2, 1, Duration.EndOfTurn
        ).setText("target creature gets +2/+1"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(
                HasteAbility.getInstance(), Duration.EndOfTurn
        ).setText("and gains haste until end of turn"));
        this.getSpellAbility().addEffect(new ScryEffect(1, false));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private SamutsSprint(final SamutsSprint card) {
        super(card);
    }

    @Override
    public SamutsSprint copy() {
        return new SamutsSprint(this);
    }
}
