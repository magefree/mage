package mage.cards.u;

import mage.abilities.Mode;
import mage.abilities.effects.common.GainLifeEffect;
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
public final class UmezawasCharm extends CardImpl {

    public UmezawasCharm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}");

        // Choose one—
        // • Target creature gets +2/+2 until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(2, 2, Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // • Target creature gets -1/-1 until end of turn.
        Mode mode = new Mode(new BoostTargetEffect(-1, -1, Duration.EndOfTurn));
        mode.addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addMode(mode);

        // • You gain 2 life.
        this.getSpellAbility().addMode(new Mode(new GainLifeEffect(2)));
    }

    private UmezawasCharm(final UmezawasCharm card) {
        super(card);
    }

    @Override
    public UmezawasCharm copy() {
        return new UmezawasCharm(this);
    }
}
