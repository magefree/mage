package mage.cards.t;

import mage.abilities.Mode;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.SwitchPowerToughnessTargetEffect;
import mage.abilities.keyword.EntwineAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TwistedReflection extends CardImpl {

    public TwistedReflection(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Choose one—
        // • Target creature gets -6/-0 until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(-6, 0, Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // • Switch target creature's power and toughness until end of turn.
        Mode mode = new Mode(new SwitchPowerToughnessTargetEffect(Duration.EndOfTurn));
        mode.addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addMode(mode);

        // Entwine {B}
        this.addAbility(new EntwineAbility("{B}"));
    }

    private TwistedReflection(final TwistedReflection card) {
        super(card);
    }

    @Override
    public TwistedReflection copy() {
        return new TwistedReflection(this);
    }
}
