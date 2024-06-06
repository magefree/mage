package mage.abilities.keyword;

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
 * @author Styxo
 */
public class MeditateAbility extends ActivatedAbilityImpl {

    public MeditateAbility(Cost cost) {
        super(Zone.BATTLEFIELD, new MeditateEffect(), cost);
        this.timing = TimingRule.SORCERY;
    }

    protected MeditateAbility(final MeditateAbility ability) {
        super(ability);
    }

    @Override
    public MeditateAbility copy() {
        return new MeditateAbility(this);
    }

    @Override
    public String getRule() {
        StringBuilder sb = new StringBuilder("Meditate ").append(getManaCosts().getText());
        sb.append(" <i>(Return this creature to its owner's hand. Meditate only as a sorcery.)</i>");
        return sb.toString();
    }

}

class MeditateEffect extends OneShotEffect {

    MeditateEffect() {
        super(Outcome.ReturnToHand);
    }

    protected MeditateEffect(final MeditateEffect effect) {
        super(effect);
    }

    @Override
    public MeditateEffect copy() {
        return new MeditateEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (controller == null || permanent == null) {
            return false;
        }
        boolean ret = controller.moveCards(permanent, Zone.HAND, source, game);
        if (ret) {
            game.fireEvent(new GameEvent(EventType.MEDITATED, source.getSourceId(), source, controller.getId()));
        }
        return ret;
    }

}
