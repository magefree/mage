
package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.cards.Card;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.StackObject;

/**
 * @author LevelX2
 */
public class CantBeTargetedCardsGraveyardsEffect extends ContinuousRuleModifyingEffectImpl {

    public CantBeTargetedCardsGraveyardsEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "Cards in graveyards can't be the targets of spells or abilities";
    }

    protected CantBeTargetedCardsGraveyardsEffect(final CantBeTargetedCardsGraveyardsEffect effect) {
        super(effect);
    }

    @Override
    public CantBeTargetedCardsGraveyardsEffect copy() {
        return new CantBeTargetedCardsGraveyardsEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TARGET;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Card targetCard = game.getCard(event.getTargetId());
        StackObject stackObject = game.getStack().getStackObject(event.getSourceId());
        if (targetCard != null && stackObject != null) {
            Zone zone = game.getState().getZone(targetCard.getId());
            if (zone == Zone.GRAVEYARD) {
                return true;
            }
        }
        return false;
    }
}
