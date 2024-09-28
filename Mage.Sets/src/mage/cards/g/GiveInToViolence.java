package mage.cards.g;

import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GiveInToViolence extends CardImpl {

    public GiveInToViolence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}");

        // Target creature gets +2/+2 and gains lifelink until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(
                2, 2, Duration.EndOfTurn
        ).setText("Target creature gets +2/+2"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(
                LifelinkAbility.getInstance(), Duration.EndOfTurn
        ).setText("and gains lifelink until end of turn"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private GiveInToViolence(final GiveInToViolence card) {
        super(card);
    }

    @Override
    public GiveInToViolence copy() {
        return new GiveInToViolence(this);
    }
}
