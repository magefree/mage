package mage.cards.h;

import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HeartsOnFire extends CardImpl {

    public HeartsOnFire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");

        // One or two target creatures each get +2/+1 until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(2, 1));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(1, 2));
    }

    private HeartsOnFire(final HeartsOnFire card) {
        super(card);
    }

    @Override
    public HeartsOnFire copy() {
        return new HeartsOnFire(this);
    }
}
