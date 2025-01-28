package mage.cards.b;

import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BestowGreatness extends CardImpl {

    public BestowGreatness(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{G}");

        // Target creature gets +4/+4 and gains trample until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(
                4, 4, Duration.EndOfTurn
        ).setText("Target creature gets +4/+4"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(
                TrampleAbility.getInstance(), Duration.EndOfTurn
        ).setText("and gains trample until end of turn"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private BestowGreatness(final BestowGreatness card) {
        super(card);
    }

    @Override
    public BestowGreatness copy() {
        return new BestowGreatness(this);
    }
}
