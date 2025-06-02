package mage.cards.w;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.token.ZombieWhiteToken;
import mage.game.stack.StackAbility;

import java.util.UUID;

/**
 * @author Grath
 */
public final class WizenedMentor extends CardImpl {

    public WizenedMentor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever an opponent activates an ability of a permanent that isn't a mana ability, you create a 1/1 white Zombie creature token. This ability triggers only once each turn.
        this.addAbility(new WizenedMentorTriggeredAbility());
    }

    private WizenedMentor(final WizenedMentor card) {
        super(card);
    }

    @Override
    public WizenedMentor copy() {
        return new WizenedMentor(this);
    }
}

class WizenedMentorTriggeredAbility extends TriggeredAbilityImpl {

    WizenedMentorTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CreateTokenEffect(new ZombieWhiteToken()));
        setTriggerPhrase("Whenever an opponent activates an ability of a permanent that isn't a mana ability, you ");
        setTriggersLimitEachTurn(1);
    }

    private WizenedMentorTriggeredAbility(final WizenedMentorTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public WizenedMentorTriggeredAbility copy() {
        return new WizenedMentorTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ACTIVATED_ABILITY;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!event.getPlayerId().equals(getControllerId())) {
            Card source = game.getPermanentOrLKIBattlefield(event.getSourceId());
            if (source != null) {
                StackAbility stackAbility = (StackAbility) game.getStack().getStackObject(event.getSourceId());
                return !stackAbility.getStackAbility().isManaActivatedAbility();
            }
        }
        return false;
    }
}
