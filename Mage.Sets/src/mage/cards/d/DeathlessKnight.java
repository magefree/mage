package mage.cards.d;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToHandEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DeathlessKnight extends CardImpl {

    public DeathlessKnight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B/G}{B/G}{B/G}{B/G}");

        this.subtype.add(SubType.SKELETON);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // When you gain life for the first time each turn, return Deathless Knight from your graveyard to your hand.
        this.addAbility(new DeathlessKnightTriggeredAbility());
    }

    private DeathlessKnight(final DeathlessKnight card) {
        super(card);
    }

    @Override
    public DeathlessKnight copy() {
        return new DeathlessKnight(this);
    }
}

class DeathlessKnightTriggeredAbility extends TriggeredAbilityImpl {

    private boolean triggeredOnce = false;

    DeathlessKnightTriggeredAbility() {
        super(Zone.ALL, new ReturnSourceFromGraveyardToHandEffect(), false);
    }

    private DeathlessKnightTriggeredAbility(final DeathlessKnightTriggeredAbility ability) {
        super(ability);
        this.triggeredOnce = ability.triggeredOnce;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.GAINED_LIFE
                || event.getType() == GameEvent.EventType.END_PHASE_POST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.END_PHASE_POST) {
            triggeredOnce = false;
            return false;
        }
        if (event.getType() != GameEvent.EventType.GAINED_LIFE
                || !event.getPlayerId().equals(controllerId)
                || game.getState().getZone(this.getSourceId()) == Zone.GRAVEYARD) {
            return false;
        }
        if (triggeredOnce) {
            return false;
        }
        triggeredOnce = true;
        return true;
    }

    @Override
    public String getRule() {
        return "When you gain life for the first time each turn, return {this} from your graveyard to your hand.";
    }

    @Override
    public DeathlessKnightTriggeredAbility copy() {
        return new DeathlessKnightTriggeredAbility(this);
    }
}
