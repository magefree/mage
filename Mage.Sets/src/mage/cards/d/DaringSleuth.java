
package mage.cards.d;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class DaringSleuth extends CardImpl {

    public DaringSleuth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        this.secondSideCardClazz = mage.cards.b.BearerOfOverwhelmingTruths.class;

        // When you sacrifice a Clue, transform Daring Sleuth.
        this.addAbility(new TransformAbility());
        this.addAbility(new DaringSleuthTriggeredAbility());
    }

    private DaringSleuth(final DaringSleuth card) {
        super(card);
    }

    @Override
    public DaringSleuth copy() {
        return new DaringSleuth(this);
    }
}

class DaringSleuthTriggeredAbility extends TriggeredAbilityImpl {

    public DaringSleuthTriggeredAbility() {
        super(Zone.BATTLEFIELD, new TransformSourceEffect());
        setTriggerPhrase("When you sacrifice a Clue, ");
    }

    public DaringSleuthTriggeredAbility(final DaringSleuthTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public DaringSleuthTriggeredAbility copy() {
        return new DaringSleuthTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SACRIFICED_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getPlayerId().equals(this.getControllerId())
                && game.getLastKnownInformation(event.getTargetId(), Zone.BATTLEFIELD).hasSubtype(SubType.CLUE, game);
    }
}
