package mage.cards.r;

import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldEffect;
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
public final class ReturnToAction extends CardImpl {

    public ReturnToAction(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}");

        // Until end of turn, target creature gets +1/+0 and gains lifelink and "When this creature dies, return it to the battlefield tapped under its owner's control."
        this.getSpellAbility().addEffect(new BoostTargetEffect(1, 0, Duration.EndOfTurn)
                .setText("Until end of turn, target creature gets +1/+0")
        );
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(
                LifelinkAbility.getInstance(), Duration.EndOfTurn, "and gains lifelink"
        ));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(new DiesSourceTriggeredAbility(
                new ReturnSourceFromGraveyardToBattlefieldEffect(true, true), false
        ), Duration.EndOfTurn, "and \"When this creature dies, return it to the battlefield tapped under its owner's control.\""));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private ReturnToAction(final ReturnToAction card) {
        super(card);
    }

    @Override
    public ReturnToAction copy() {
        return new ReturnToAction(this);
    }
}
