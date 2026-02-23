package mage.cards.s;

import java.util.UUID;

import mage.abilities.common.delayed.WhenTargetDiesDelayedTriggeredAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.BlackGreenElfToken;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author muz
 */
public final class ScarbladesMalice extends CardImpl {

    public ScarbladesMalice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{B}");

        // Target creature you control gains deathtouch and lifelink until end of turn.
        // When that creature dies this turn, create a 2/2 black and green Elf creature token.
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(DeathtouchAbility.getInstance())
            .setText("target creature you control gains deathtouch"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(LifelinkAbility.getInstance())
            .setText("and lifelink until end of turn"));
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(
                new WhenTargetDiesDelayedTriggeredAbility(new CreateTokenEffect(new BlackGreenElfToken()))
        ));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
    }

    private ScarbladesMalice(final ScarbladesMalice card) {
        super(card);
    }

    @Override
    public ScarbladesMalice copy() {
        return new ScarbladesMalice(this);
    }
}
