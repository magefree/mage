package mage.abilities.common;

import mage.abilities.effects.Effect;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

/**
 * @author xenohedron
 */
public class EntersBattlefieldOpponentTriggeredAbility extends EntersBattlefieldAllTriggeredAbility {

    public EntersBattlefieldOpponentTriggeredAbility(Effect effect, FilterPermanent filter, boolean optional) {
        this(Zone.BATTLEFIELD, effect, filter, optional, SetTargetPointer.NONE);
    }

    public EntersBattlefieldOpponentTriggeredAbility(Zone zone, Effect effect, FilterPermanent filter, boolean optional, SetTargetPointer setTargetPointer) {
        super(zone, effect, filter, optional, setTargetPointer);
        setTriggerPhrase(getWhen() + CardUtil.addArticle(filter.getMessage()) + " an opponent controls enters, ");
    }

    protected EntersBattlefieldOpponentTriggeredAbility(final EntersBattlefieldOpponentTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent == null || !game.getOpponents(this.getControllerId()).contains(permanent.getControllerId())) {
            return false;
        }
        return super.checkTrigger(event, game);
    }

    @Override
    public EntersBattlefieldOpponentTriggeredAbility copy() {
        return new EntersBattlefieldOpponentTriggeredAbility(this);
    }
}
