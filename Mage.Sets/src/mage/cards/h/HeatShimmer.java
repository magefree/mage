
package mage.cards.h;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author Styxo
 */
public final class HeatShimmer extends CardImpl {

    public HeatShimmer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}");

        //Create a token that's a copy of target creature, except it has haste and "At the beginning of the end step, exile this permanent."
        this.getSpellAbility().addEffect(new HeatShimmerEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private HeatShimmer(final HeatShimmer card) {
        super(card);
    }

    @Override
    public HeatShimmer copy() {
        return new HeatShimmer(this);
    }
}

class HeatShimmerEffect extends OneShotEffect {

    public HeatShimmerEffect() {
        super(Outcome.Copy);
        this.staticText = "Create a token that's a copy of target creature, except it has haste and \"At the beginning of the end step, exile this permanent.\"";
    }

    public HeatShimmerEffect(final HeatShimmerEffect effect) {
        super(effect);
    }

    @Override
    public HeatShimmerEffect copy() {
        return new HeatShimmerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        if (controller != null
                && permanent != null) {
            CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect(source.getControllerId(), null, true);
            effect.setTargetPointer(new FixedTarget(permanent, game));
            effect.apply(game, source);
            ExileTargetEffect exileEffect = new ExileTargetEffect();
            exileEffect.setTargetPointer(new FixedTarget(effect.getAddedPermanents().get(0), game));
            DelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(exileEffect);
            game.addDelayedTriggeredAbility(delayedAbility, source);
            return true;
        }
        return false;
    }
}
