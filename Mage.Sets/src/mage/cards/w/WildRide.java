package mage.cards.w;

import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HarmonizeAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WildRide extends CardImpl {

    public WildRide(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{R}");

        // Target creature gets +3/+0 and gains haste until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(
                3, 0, Duration.EndOfTurn
        ).setText("Target creature gets +3/+0"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(
                HasteAbility.getInstance(), Duration.EndOfTurn
        ).setText("and gains haste until end of turn"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Harmonize {4}{R}
        this.addAbility(new HarmonizeAbility(this, "{4}{R}"));
    }

    private WildRide(final WildRide card) {
        super(card);
    }

    @Override
    public WildRide copy() {
        return new WildRide(this);
    }
}
