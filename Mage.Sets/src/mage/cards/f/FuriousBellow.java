package mage.cards.f;

import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FuriousBellow extends CardImpl {

    public FuriousBellow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");

        // Target creature gets +3/+0 and gains first strike until end of turn. Scry 1.
        this.getSpellAbility().addEffect(new BoostTargetEffect(
                3, 0, Duration.EndOfTurn
        ).setText("Target creature gets +3/+0"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(
                FirstStrikeAbility.getInstance(), Duration.EndOfTurn
        ).setText("and gains first strike until end of turn"));
        this.getSpellAbility().addEffect(new ScryEffect(1));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private FuriousBellow(final FuriousBellow card) {
        super(card);
    }

    @Override
    public FuriousBellow copy() {
        return new FuriousBellow(this);
    }
}
