package mage.cards.s;

import mage.abilities.common.delayed.WhenTargetDiesDelayedTriggeredAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class SearingBlood extends CardImpl {

    public SearingBlood(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}{R}");

        // Searing Blood deals 2 damage to target creature. When that creature dies this turn, Searing Blood deals 3 damage to that creature's controller.
        this.getSpellAbility().addEffect(new DamageTargetEffect(2));
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(
                new WhenTargetDiesDelayedTriggeredAbility(
                        new DamageTargetEffect(3, true, "that creature's controller"),
                        SetTargetPointer.PLAYER
                )
        ));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private SearingBlood(final SearingBlood card) {
        super(card);
    }

    @Override
    public SearingBlood copy() {
        return new SearingBlood(this);
    }
}
