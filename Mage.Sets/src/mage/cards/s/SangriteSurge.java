package mage.cards.s;

import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author North
 */
public final class SangriteSurge extends CardImpl {

    public SangriteSurge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{R}{G}");

        // Target creature gets +3/+3 and gains double strike until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(
                3, 3, Duration.EndOfTurn
        ).setText("target creature gets +3/+3"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(
                DoubleStrikeAbility.getInstance(), Duration.EndOfTurn
        ).setText("and gains double strike until end of turn"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private SangriteSurge(final SangriteSurge card) {
        super(card);
    }

    @Override
    public SangriteSurge copy() {
        return new SangriteSurge(this);
    }
}
