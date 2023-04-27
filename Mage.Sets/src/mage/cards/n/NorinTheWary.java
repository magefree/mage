
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.ExileReturnBattlefieldOwnerNextEndStepSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author LevelX2
 */
public final class NorinTheWary extends CardImpl {

    public NorinTheWary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // When a player casts a spell or a creature attacks, exile Norin the Wary. Return it to the battlefield under its owner's control at the beginning of the next end step.
        this.addAbility(new NorinTheWaryTriggeredAbility());

    }

    private NorinTheWary(final NorinTheWary card) {
        super(card);
    }

    @Override
    public NorinTheWary copy() {
        return new NorinTheWary(this);
    }
}

class NorinTheWaryTriggeredAbility extends TriggeredAbilityImpl {

    public NorinTheWaryTriggeredAbility() {
        super(Zone.BATTLEFIELD, new ExileReturnBattlefieldOwnerNextEndStepSourceEffect(), false);
        setTriggerPhrase("When a player casts a spell or a creature attacks, ");
    }

    public NorinTheWaryTriggeredAbility(final NorinTheWaryTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST
                || event.getType() == GameEvent.EventType.ATTACKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return true;
    }

    @Override
    public NorinTheWaryTriggeredAbility copy() {
        return new NorinTheWaryTriggeredAbility(this);
    }
}
