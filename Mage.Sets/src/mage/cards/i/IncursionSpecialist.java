
package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.watchers.common.CastSpellLastTurnWatcher;

/**
 *
 * @author jeffwadsworth
 */
public final class IncursionSpecialist extends CardImpl {

    public IncursionSpecialist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Whenever you cast your second spell each turn, Incursion Specialist gets +2/+0 until end of turn and can't be blocked this turn.
        this.addAbility(new IncursionTriggeredAbility(), new CastSpellLastTurnWatcher());
    }

    public IncursionSpecialist(final IncursionSpecialist card) {
        super(card);
    }

    @Override
    public IncursionSpecialist copy() {
        return new IncursionSpecialist(this);
    }
}

class IncursionTriggeredAbility extends TriggeredAbilityImpl {

    public IncursionTriggeredAbility() {
        super(Zone.BATTLEFIELD, new BoostSourceEffect(2, 0, Duration.EndOfTurn));
        this.addEffect(new CantBeBlockedSourceEffect(Duration.EndOfTurn));
    }

    public IncursionTriggeredAbility(final IncursionTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public IncursionTriggeredAbility copy() {
        return new IncursionTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getPlayerId().equals(controllerId)) {
            CastSpellLastTurnWatcher watcher = game.getState().getWatcher(CastSpellLastTurnWatcher.class);
            if (watcher != null && watcher.getAmountOfSpellsPlayerCastOnCurrentTurn(event.getPlayerId()) == 2) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever you cast your second spell each turn, Incursion Specialist gets +2/+0 until end of turn and can't be blocked this turn.";
    }
}
