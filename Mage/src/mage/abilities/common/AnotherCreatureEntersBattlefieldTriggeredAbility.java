package mage.abilities.common;

import mage.Constants;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

public class AnotherCreatureEntersBattlefieldTriggeredAbility extends TriggeredAbilityImpl<AnotherCreatureEntersBattlefieldTriggeredAbility> {

    public AnotherCreatureEntersBattlefieldTriggeredAbility(Effect effect) {
        this(effect, false);
    }

    public AnotherCreatureEntersBattlefieldTriggeredAbility(Effect effect, boolean optional) {
        super(Constants.Zone.BATTLEFIELD, effect, optional);
    }

    public AnotherCreatureEntersBattlefieldTriggeredAbility(AnotherCreatureEntersBattlefieldTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD && event.getTargetId() != this.getSourceId()) {
            Permanent permanent = game.getPermanent(event.getTargetId());
            if (permanent.getCardType().contains(Constants.CardType.CREATURE)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever another creature enters the battlefield, " + super.getRule();
    }

    @Override
    public AnotherCreatureEntersBattlefieldTriggeredAbility copy() {
        return new AnotherCreatureEntersBattlefieldTriggeredAbility(this);
    }
}
