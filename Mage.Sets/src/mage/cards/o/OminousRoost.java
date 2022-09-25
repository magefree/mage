package mage.cards.o;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.token.OminousRoostBirdToken;

import java.util.UUID;

/**
 * @author ciaccona007
 */
public final class OminousRoost extends CardImpl {

    public OminousRoost(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");

        // When Ominous Roost enters the battlefield or whenever you cast a spell from your graveyard, create a 1/1 blue Bird creature token with flying and "This creature can block only creatures with flying."
        this.addAbility(new OminousRoostTriggeredAbility());
    }

    private OminousRoost(final OminousRoost card) {
        super(card);
    }

    @Override
    public OminousRoost copy() {
        return new OminousRoost(this);
    }
}

class OminousRoostTriggeredAbility extends TriggeredAbilityImpl {

    OminousRoostTriggeredAbility() {
	    super(Zone.BATTLEFIELD, new CreateTokenEffect(new OminousRoostBirdToken()));
    }

    private OminousRoostTriggeredAbility(final OminousRoostTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST
                || event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        switch (event.getType()) {
            case SPELL_CAST:
                if (event.getPlayerId().equals(controllerId) && event.getZone() == Zone.GRAVEYARD) {
                    return true;
                }
            case ENTERS_THE_BATTLEFIELD:
                return event.getTargetId().equals(getSourceId());
            default:
                return false;
        }
    }

    @Override
    public String getRule() {
        return "When {this} enters the battlefield or whenever you cast a spell from your graveyard, create a " +
                "1/1 blue Bird creature token with flying and \"This creature can block only creatures with flying.\"";
    }

    @Override
    public OminousRoostTriggeredAbility copy() {
        return new OminousRoostTriggeredAbility(this);
    }
}
