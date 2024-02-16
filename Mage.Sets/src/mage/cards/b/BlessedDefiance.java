package mage.cards.b;

import mage.abilities.common.delayed.WhenTargetDiesDelayedTriggeredAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.SpiritWhiteToken;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author ciaccona007
 */
public class BlessedDefiance extends CardImpl {

    public BlessedDefiance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}");

        // Target creature you control gets +2/+0 and gains lifelink until end of turn.
        // When that creature dies this turn, create a 1/1 white Spirit creature token with flying.
        this.getSpellAbility().addEffect(new BoostTargetEffect(2, 0)
                .setText("Target creature you control gets +2/+0"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(LifelinkAbility.getInstance())
                .setText("and gains lifelink until end of turn"));
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(
                new WhenTargetDiesDelayedTriggeredAbility(new CreateTokenEffect(new SpiritWhiteToken()))
        ));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
    }

    private BlessedDefiance(final BlessedDefiance card) {
        super(card);
    }

    @Override
    public BlessedDefiance copy() {
        return new BlessedDefiance(this);
    }
}
