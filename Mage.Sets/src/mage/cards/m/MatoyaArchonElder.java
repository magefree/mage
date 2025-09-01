package mage.cards.m;

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

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MatoyaArchonElder extends CardImpl {

    public MatoyaArchonElder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Whenever you scry or surveil, draw a card.
        this.addAbility(new MatoyaArchonElderTriggeredAbility());
    }

    private MatoyaArchonElder(final MatoyaArchonElder card) {
        super(card);
    }

    @Override
    public MatoyaArchonElder copy() {
        return new MatoyaArchonElder(this);
    }
}

class MatoyaArchonElderTriggeredAbility extends TriggeredAbilityImpl {

    MatoyaArchonElderTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1));
        setTriggerPhrase("Whenever you scry or surveil, ");
    }

    private MatoyaArchonElderTriggeredAbility(final MatoyaArchonElderTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public MatoyaArchonElderTriggeredAbility copy() {
        return new MatoyaArchonElderTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        switch (event.getType()) {
            case SCRIED:
            case SURVEILED:
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return isControlledBy(event.getPlayerId());
    }
}
