
package mage.abilities.keyword;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.costs.Cost;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.TimingRule;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author Styxo
 */
public class MeditateAbility extends ActivatedAbilityImpl {

    public MeditateAbility(Cost cost) {
        super(Zone.BATTLEFIELD, new ReturnToHandEffect(), cost);
        this.timing = TimingRule.SORCERY;
    }

    public MeditateAbility(final MeditateAbility ability) {
        super(ability);
    }

    @Override
    public MeditateAbility copy() {
        return new MeditateAbility(this);
    }

    @Override
    public String getRule() {
        StringBuilder sb = new StringBuilder("Meditate ").append(manaCosts.getText());
        sb.append(" <i>(Return this creature to its owner's hand. Meditate only as a sorcery.)</i>");
        return sb.toString();
    }

}

class ReturnToHandEffect extends OneShotEffect {

    public ReturnToHandEffect() {
        super(Outcome.ReturnToHand);
    }

    public ReturnToHandEffect(final ReturnToHandEffect effect) {
        super(effect);
    }

    @Override
    public ReturnToHandEffect copy() {
        return new ReturnToHandEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            MageObject mageObject = source.getSourceObjectIfItStillExists(game);
            if (mageObject != null) {
                Permanent permanent = game.getPermanent(source.getSourceId());
                if (permanent != null) {
                    boolean ret = controller.moveCards(permanent, Zone.HAND, source, game);
                    if (ret) {
                        game.fireEvent(new GameEvent(EventType.MEDITATED, source.getSourceId(), source.getSourceId(), controller.getId()));
                    }
                    return ret;
                }
            }
        }
        return false;
    }
}
