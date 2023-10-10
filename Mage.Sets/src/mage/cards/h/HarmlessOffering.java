
package mage.cards.h;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetOpponent;

/**
 *
 * @author fireshoes
 */
public final class HarmlessOffering extends CardImpl {

    public HarmlessOffering(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{R}");

        // Target opponent gains control of target permanent you control.
        this.getSpellAbility().addEffect(new HarmlessOfferingEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
        this.getSpellAbility().addTarget(new TargetControlledPermanent());
    }

    private HarmlessOffering(final HarmlessOffering card) {
        super(card);
    }

    @Override
    public HarmlessOffering copy() {
        return new HarmlessOffering(this);
    }
}

class HarmlessOfferingEffect extends ContinuousEffectImpl {

    public HarmlessOfferingEffect() {
        super(Duration.EndOfGame, Layer.ControlChangingEffects_2, SubLayer.NA, Outcome.Benefit);
        this.staticText = "Target opponent gains control of target permanent you control";
    }

    private HarmlessOfferingEffect(final HarmlessOfferingEffect effect) {
        super(effect);
    }

    @Override
    public HarmlessOfferingEffect copy() {
        return new HarmlessOfferingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID controllerId = source.getTargets().get(0).getFirstTarget();
        Player controller = game.getPlayer(controllerId);
        Permanent permanent = game.getPermanent(source.getTargets().get(1).getFirstTarget());
        if (controller != null && permanent != null) {
            permanent.changeControllerId(controllerId, game, source);
        } else {
            this.discard();
        }
        return true;
    }

}
