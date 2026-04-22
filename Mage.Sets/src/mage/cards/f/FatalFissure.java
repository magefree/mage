package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.delayed.WhenTargetDiesDelayedTriggeredAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.keyword.EarthbendTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetControlledLandPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FatalFissure extends CardImpl {

    public FatalFissure(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}");

        // Choose target creature. When that creature dies this turn, you earthbend 4.
        this.getSpellAbility().addEffect(new InfoEffect("choose target creature"));
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(new WhenTargetDiesDelayedTriggeredAbility(new FatalFissureEffect())));
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

class FatalFissureEffect extends EarthbendTargetEffect {

    FatalFissureEffect() {
        super(4);
    }

    private FatalFissureEffect(final FatalFissureEffect effect) {
        super(effect);
    }

    @Override
    public FatalFissureEffect copy() {
        return new FatalFissureEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        final Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
	final Target target = new TargetControlledLandPermanent();
        if (target.canChoose(controller.getId(), source, game)
                && controller.chooseTarget(this.outcome, target, source, game)) {
	    this.setTargetPointer(new FixedTarget(target.getFirstTarget()));
	    return super.apply(game, source);
        }
        return false;
    }
}
