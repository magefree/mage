
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public final class CallToHeel extends CardImpl {

    public CallToHeel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{U}");


        // Return target creature to its owner's hand. Its controller draws a card.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellAbility().addEffect(new CallToHeelEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private CallToHeel(final CallToHeel card) {
        super(card);
    }

    @Override
    public CallToHeel copy() {
        return new CallToHeel(this);
    }
}

class CallToHeelEffect extends OneShotEffect {

    public CallToHeelEffect() {
        super(Outcome.Neutral);
        this.staticText = "Its controller draws a card";
    }

    private CallToHeelEffect(final CallToHeelEffect effect) {
        super(effect);
    }

    @Override
    public CallToHeelEffect copy() {
        return new CallToHeelEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            permanent = (Permanent) game.getLastKnownInformation(source.getFirstTarget(), Zone.BATTLEFIELD);
        }

        if (permanent != null) {
            Player controller = game.getPlayer(permanent.getControllerId());
            if (controller != null) {
                controller.drawCards(1, source, game);
                return true;
            }
        }
        return false;
    }
}
