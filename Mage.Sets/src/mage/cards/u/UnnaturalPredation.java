package mage.cards.u;

import java.util.UUID;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Loki
 */
public final class UnnaturalPredation extends CardImpl {

    public UnnaturalPredation (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{G}");

        this.getSpellAbility().addEffect(new BoostTargetEffect(1, 1, Duration.EndOfTurn)
                .setText("Target creature gets +1/+1"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(TrampleAbility.getInstance(), Duration.EndOfTurn)
                .setText("and gains trample until end of turn"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private UnnaturalPredation(final UnnaturalPredation card) {
        super(card);
    }

    @Override
    public UnnaturalPredation copy() {
        return new UnnaturalPredation(this);
    }

}
