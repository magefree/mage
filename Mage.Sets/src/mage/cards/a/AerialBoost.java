package mage.cards.a;

import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.ConvokeAbility;
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
public final class AerialBoost extends CardImpl {

    public AerialBoost(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Convoke
        this.addAbility(new ConvokeAbility());

        // Target creature gets +2/+2 and gains flying until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(2, 2)
                .setText("target creature gets +2/+2"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(
                FlyingAbility.getInstance(), Duration.EndOfTurn
        ).setText("and gains flying until end of turn"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private AerialBoost(final AerialBoost card) {
        super(card);
    }

    @Override
    public AerialBoost copy() {
        return new AerialBoost(this);
    }
}
