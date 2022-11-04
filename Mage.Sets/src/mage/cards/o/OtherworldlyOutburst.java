package mage.cards.o;

import java.util.UUID;
import mage.abilities.common.delayed.WhenTargetDiesDelayedTriggeredAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.EldraziHorrorToken;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class OtherworldlyOutburst extends CardImpl {

    public OtherworldlyOutburst(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{R}");

        // Target creature gets +1/+0 until end of turn. When that creature dies this turn, create a 3/2 colorless Eldrazi Horror creature token.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new BoostTargetEffect(1, 0));
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(
                new WhenTargetDiesDelayedTriggeredAbility(new CreateTokenEffect(new EldraziHorrorToken()))
        ));
    }

    private OtherworldlyOutburst(final OtherworldlyOutburst card) {
        super(card);
    }

    @Override
    public OtherworldlyOutburst copy() {
        return new OtherworldlyOutburst(this);
    }
}
