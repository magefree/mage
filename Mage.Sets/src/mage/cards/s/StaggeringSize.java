package mage.cards.s;

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
public final class StaggeringSize extends CardImpl {

    public StaggeringSize(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");

        // Target creature gets +3/+3 and gains trample until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(
                3, 3, Duration.EndOfTurn
        ).setText("Target creature gets +3/+3"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(
                TrampleAbility.getInstance(), Duration.EndOfTurn
        ).setText("and gains trample until end of turn"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private StaggeringSize(final StaggeringSize card) {
        super(card);
    }

    @Override
    public StaggeringSize copy() {
        return new StaggeringSize(this);
    }
}
