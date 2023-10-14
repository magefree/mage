package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.StackAbility;
import mage.game.stack.StackObject;

/**
 *
 * @author anonymous
 */
public final class ChandrasPhoenix extends CardImpl {

    public ChandrasPhoenix(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{R}");
        this.subtype.add(SubType.PHOENIX);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // Haste (This creature can attack and as soon as it comes under your control.)
        this.addAbility(HasteAbility.getInstance());
        
        // Whenever an opponent is dealt damage by a red instant or sorcery spell 
        // you control or by a red planeswalker you control, return Chandra's 
        // Phoenix from your graveyard to your hand.
        this.addAbility(new ChandrasPhoenixTriggeredAbility());
    }

    private ChandrasPhoenix(final ChandrasPhoenix card) {
        super(card);
    }

    @Override
    public ChandrasPhoenix copy() {
        return new ChandrasPhoenix(this);
    }
}

class ChandrasPhoenixTriggeredAbility extends TriggeredAbilityImpl {

    ChandrasPhoenixTriggeredAbility() {
        super(Zone.GRAVEYARD, new ReturnToHandSourceEffect());
    }

    private ChandrasPhoenixTriggeredAbility(final ChandrasPhoenixTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ChandrasPhoenixTriggeredAbility copy() {
        return new ChandrasPhoenixTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Card chandrasPhoenix = game.getCard(this.getSourceId());
        if (chandrasPhoenix != null
                && game.getOpponents(chandrasPhoenix.getOwnerId()).contains(event.getPlayerId())) {
            StackObject stackObject = game.getStack().getStackObject(event.getSourceId());
            if (stackObject != null) {
                MageObject sourceObjectDamage;
                if (stackObject instanceof StackAbility) {
                    sourceObjectDamage = ((StackAbility) stackObject).getSourceObject(game);
                } else {
                    sourceObjectDamage = stackObject;
                }
                if (sourceObjectDamage != null) {
                    if (sourceObjectDamage.getColor(game).isRed()
                            && (sourceObjectDamage.isPlaneswalker(game)
                            || sourceObjectDamage.isInstant(game)
                            || sourceObjectDamage.isSorcery(game))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever an opponent is dealt damage by a red instant or sorcery spell "
                + "you control or by a red planeswalker you control, return {this} from your graveyard to your hand.";
    }
}
