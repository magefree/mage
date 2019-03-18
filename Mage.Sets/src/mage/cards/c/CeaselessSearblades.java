
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author Will
 */
public final class CeaselessSearblades extends CardImpl {
   
    public CeaselessSearblades(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}");
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Whenever you activate an ability of an Elemental, Ceaseless Searblades gets +1/+0 until end of turn.
        this.addAbility(new CeaselessSearbladesTriggeredAbility());
        
    }
    
    public CeaselessSearblades(final CeaselessSearblades card) {
        super(card);
    }

    @Override
    public CeaselessSearblades copy() {
        return new CeaselessSearblades(this);
    }
}

class CeaselessSearbladesTriggeredAbility extends TriggeredAbilityImpl {

    private static final FilterCard filter = new FilterCard("an Elemental");

    static {
        filter.add(new SubtypePredicate(SubType.ELEMENTAL));
    }

    CeaselessSearbladesTriggeredAbility() {
        super(Zone.BATTLEFIELD, new BoostSourceEffect(1, 0, Duration.EndOfTurn), false);
    }

    CeaselessSearbladesTriggeredAbility(final CeaselessSearbladesTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public CeaselessSearbladesTriggeredAbility copy() {
        return new CeaselessSearbladesTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ACTIVATED_ABILITY;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Card source = game.getPermanentOrLKIBattlefield(event.getSourceId());
        if (event.getPlayerId().equals(getControllerId())
            && source != null
            && filter.match(source, game)) {
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever you activate an ability of an Elemental, " + super.getRule();
    }
}
