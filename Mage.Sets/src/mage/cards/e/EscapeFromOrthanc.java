package mage.cards.e;

import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EscapeFromOrthanc extends CardImpl {

    public EscapeFromOrthanc(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}");

        // Target creature gets +1/+3 and gains flying until end of turn. Untap it.
        this.getSpellAbility().addEffect(new BoostTargetEffect(1, 3)
                .setText("target creature gets +1/+3"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(
                FlyingAbility.getInstance(), Duration.EndOfTurn
        ).setText("and gains flying until end of turn"));
        this.getSpellAbility().addEffect(new UntapTargetEffect().setText("Untap it"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private EscapeFromOrthanc(final EscapeFromOrthanc card) {
        super(card);
    }

    @Override
    public EscapeFromOrthanc copy() {
        return new EscapeFromOrthanc(this);
    }
}
