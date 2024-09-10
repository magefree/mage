package mage.cards.b;

import java.util.UUID;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FearAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Loki
 */
public final class Breach extends CardImpl {

    public Breach(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{B}");

        // Target creature gets +2/+0 and gains fear until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(2, 0, Duration.EndOfTurn)
                .setText("target creature gets +2/+0"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(FearAbility.getInstance(), Duration.EndOfTurn)
                .setText("and gains fear until end of turn"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private Breach(final Breach card) {
        super(card);
    }

    @Override
    public Breach copy() {
        return new Breach(this);
    }
}
