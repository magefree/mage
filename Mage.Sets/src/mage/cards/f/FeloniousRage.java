package mage.cards.f;

import mage.abilities.common.delayed.WhenTargetDiesDelayedTriggeredAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.DetectiveToken;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class FeloniousRage extends CardImpl {

    public FeloniousRage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}");

        // Target creature you control gets +2/+0 and gains haste until end of turn. When that creature dies this turn, create a 2/2 white and blue Detective creature token.
        this.getSpellAbility().addEffect(new BoostTargetEffect(2, 0)
                .setText("target creature you control gets +2/+0"));
        this.getSpellAbility().addEffect(
                new GainAbilityTargetEffect(HasteAbility.getInstance())
                        .setText("and gains haste until end of turn")
        );
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(
                new WhenTargetDiesDelayedTriggeredAbility(new CreateTokenEffect(new DetectiveToken()))
        ));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
    }

    private FeloniousRage(final FeloniousRage card) {
        super(card);
    }

    @Override
    public FeloniousRage copy() {
        return new FeloniousRage(this);
    }
}
