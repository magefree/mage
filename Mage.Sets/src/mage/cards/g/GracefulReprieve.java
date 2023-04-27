package mage.cards.g;

import java.util.UUID;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.delayed.WhenTargetDiesDelayedTriggeredAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderOwnerControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class GracefulReprieve extends CardImpl {

    public GracefulReprieve(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // When target creature dies this turn, return that card to the battlefield under its owner's control.
        DelayedTriggeredAbility ability = new WhenTargetDiesDelayedTriggeredAbility(
                new ReturnToBattlefieldUnderOwnerControlTargetEffect(false, false),
                SetTargetPointer.CARD
        );
        ability.setTriggerPhrase("When target creature dies this turn, ");
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(ability));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private GracefulReprieve(final GracefulReprieve card) {
        super(card);
    }

    @Override
    public GracefulReprieve copy() {
        return new GracefulReprieve(this);
    }
}
