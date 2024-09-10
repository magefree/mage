package mage.cards.a;

import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AuspiciousArrival extends CardImpl {

    public AuspiciousArrival(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Target creature gets +2/+2 until end of turn. Investigate.
        this.getSpellAbility().addEffect(new BoostTargetEffect(2, 2));
        this.getSpellAbility().addEffect(new InvestigateEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private AuspiciousArrival(final AuspiciousArrival card) {
        super(card);
    }

    @Override
    public AuspiciousArrival copy() {
        return new AuspiciousArrival(this);
    }
}
