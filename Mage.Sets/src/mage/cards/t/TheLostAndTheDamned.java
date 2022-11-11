package mage.cards.t;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.token.SpawnToken;
import mage.game.stack.Spell;

import java.util.Optional;
import java.util.UUID;

public final class TheLostAndTheDamned extends CardImpl {

    public TheLostAndTheDamned(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}{R}");

        // Whenever a land enters the battlefield under your control from anywhere other than your hand or you cast a spell from anywhere other than your hand, create 3/3 red Spawn creature token.
        this.addAbility(new TheLostAndTheDamnedTriggeredAbility());
    }

    private TheLostAndTheDamned(final TheLostAndTheDamned card) {
        super(card);
    }

    @Override
    public TheLostAndTheDamned copy() {
        return new TheLostAndTheDamned(this);
    }
}

class TheLostAndTheDamnedTriggeredAbility extends TriggeredAbilityImpl {

    TheLostAndTheDamnedTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CreateTokenEffect(new SpawnToken()));
        setTriggerPhrase("Whenever a land enters the battlefield under your control from anywhere other than your hand or you cast a spell from anywhere other than your hand, ");
    }

    private TheLostAndTheDamnedTriggeredAbility(final TheLostAndTheDamnedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public TheLostAndTheDamnedTriggeredAbility copy() {
        return new TheLostAndTheDamnedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD
                || event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!this.isControlledBy(event.getPlayerId())) {
            return false;
        }
        switch (event.getType()) {
            case ENTERS_THE_BATTLEFIELD:
                EntersTheBattlefieldEvent eEvent = (EntersTheBattlefieldEvent) event;
                return (!eEvent.getFromZone().match(Zone.HAND) || !(eEvent.getTarget().isOwnedBy(this.controllerId))) // if it's someone else's land, you can't have played it from your hand. This handles playing lands from opponent's hands.
                        && eEvent.getTarget().isLand(game);
            case SPELL_CAST:
                return (!Optional
                        .ofNullable(game.getSpell(event.getTargetId()))
                        .map(Spell::getFromZone)
                        .orElse(Zone.ALL)
                        .equals(Zone.HAND) || !Optional
                        .ofNullable(game.getSpell(event.getTargetId()))
                        .map(Spell::getOwnerId)
                        .orElse(this.controllerId)
                        .equals(this.controllerId)); // if it's someone else's spell, it can't have been in your hand. This handles playing spells from opponent's hands.
        }
        return false;
    }
}
