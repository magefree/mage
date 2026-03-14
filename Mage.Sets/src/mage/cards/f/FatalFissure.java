package mage.cards.f;

import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.delayed.WhenTargetDiesDelayedTriggeredAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.keyword.EarthbendTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetControlledLandPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FatalFissure extends CardImpl {

    public FatalFissure(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}");

        // Choose target creature. When that creature dies this turn, you earthbend 4.
        DelayedTriggeredAbility ability = new WhenTargetDiesDelayedTriggeredAbility(new EarthbendTargetEffect(4).setText("you earthbend 4"));
        ability.addTarget(new TargetControlledLandPermanent());
        this.getSpellAbility().addEffect(new InfoEffect("choose target creature"));
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(ability, false));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private FatalFissure(final FatalFissure card) {
        super(card);
    }

    @Override
    public FatalFissure copy() {
        return new FatalFissure(this);
    }
}
