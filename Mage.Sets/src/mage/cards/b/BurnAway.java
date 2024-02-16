package mage.cards.b;

import java.util.UUID;
import mage.abilities.common.delayed.WhenTargetDiesDelayedTriggeredAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.ExileGraveyardAllTargetPlayerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class BurnAway extends CardImpl {

    public BurnAway(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{4}{R}");

        // Burn Away deals 6 damage to target creature. When that creature dies this turn, exile its controller's graveyard.
        this.getSpellAbility().addEffect(new DamageTargetEffect(6));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(new WhenTargetDiesDelayedTriggeredAbility(
                new ExileGraveyardAllTargetPlayerEffect().setText("exile its controller's graveyard"), SetTargetPointer.PLAYER
        )));
    }

    private BurnAway(final BurnAway card) {
        super(card);
    }

    @Override
    public BurnAway copy() {
        return new BurnAway(this);
    }
}
