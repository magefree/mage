
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;

/**
 *
 * @author Plopman
 */
public final class MindlockOrb extends CardImpl {

    public MindlockOrb(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}{U}");


        // Players can't search libraries.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new MindlockRuleModifyingEffect()));

    }

    private MindlockOrb(final MindlockOrb card) {
        super(card);
    }

    @Override
    public MindlockOrb copy() {
        return new MindlockOrb(this);
    }
}

class MindlockRuleModifyingEffect extends ContinuousRuleModifyingEffectImpl {
    
    MindlockRuleModifyingEffect ( ) {
        super(Duration.WhileOnBattlefield, Outcome.Neutral, true, false);
        staticText = "Players can't search libraries";
    }

    MindlockRuleModifyingEffect ( MindlockRuleModifyingEffect effect ) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == EventType.SEARCH_LIBRARY;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return true;
    }

    @Override
    public MindlockRuleModifyingEffect copy() {
        return new MindlockRuleModifyingEffect(this);
    }

}
