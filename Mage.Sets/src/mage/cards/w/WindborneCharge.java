
package mage.cards.w;

import java.util.UUID;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author North
 */
public final class WindborneCharge extends CardImpl {

    public WindborneCharge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{W}{W}");


        // Two target creatures you control each get +2/+2 and gain flying until end of turn.
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent(2));
        this.getSpellAbility().addEffect(new BoostTargetEffect(2, 2, Duration.EndOfTurn));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(FlyingAbility.getInstance(), Duration.EndOfTurn));
    }

    private WindborneCharge(final WindborneCharge card) {
        super(card);
    }

    @Override
    public WindborneCharge copy() {
        return new WindborneCharge(this);
    }
}
