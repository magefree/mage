
package mage.cards.j;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.watchers.common.CastSpellLastTurnWatcher;

/**
 *
 * @author fireshoes
 */
public final class JoriEnRuinDiver extends CardImpl {

    public JoriEnRuinDiver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}{R}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever you cast your second spell each turn, draw a card.
        this.addAbility(new JoriEnTriggeredAbility(), new CastSpellLastTurnWatcher());
    }

    public JoriEnRuinDiver(final JoriEnRuinDiver card) {
        super(card);
    }

    @Override
    public JoriEnRuinDiver copy() {
        return new JoriEnRuinDiver(this);
    }
}

class JoriEnTriggeredAbility extends TriggeredAbilityImpl {

    public JoriEnTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1));
    }

    public JoriEnTriggeredAbility(final JoriEnTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public JoriEnTriggeredAbility copy() {
        return new JoriEnTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getPlayerId().equals(controllerId)) {
            CastSpellLastTurnWatcher watcher = (CastSpellLastTurnWatcher) game.getState().getWatchers().get(CastSpellLastTurnWatcher.class.getSimpleName());
            if (watcher != null && watcher.getAmountOfSpellsPlayerCastOnCurrentTurn(event.getPlayerId()) == 2) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever you cast your second spell each turn, draw a card.";
    }
}
