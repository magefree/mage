package mage.cards.n;

import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NahirisStoneblades extends CardImpl {

    public NahirisStoneblades(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");

        // Up to two target creatures each get +2/+0 until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(
                2, 0, Duration.EndOfTurn
        ).setText("Up to two target creatures each get +2/+0 until end of turn"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 2));
    }

    private NahirisStoneblades(final NahirisStoneblades card) {
        super(card);
    }

    @Override
    public NahirisStoneblades copy() {
        return new NahirisStoneblades(this);
    }
}
