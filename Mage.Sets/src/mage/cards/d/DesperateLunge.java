package mage.cards.d;

import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DesperateLunge extends CardImpl {

    public DesperateLunge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Target creature gets +2/+2 and gains flying until end of turn. You gain 2 life.
        this.getSpellAbility().addEffect(new BoostTargetEffect(
                2, 2, Duration.EndOfTurn
        ).setText("Target creature gets +2/+2"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(
                FlyingAbility.getInstance(), Duration.EndOfTurn
        ).setText("and gains flying until end of turn"));
        this.getSpellAbility().addEffect(new GainLifeEffect(2));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private DesperateLunge(final DesperateLunge card) {
        super(card);
    }

    @Override
    public DesperateLunge copy() {
        return new DesperateLunge(this);
    }
}
