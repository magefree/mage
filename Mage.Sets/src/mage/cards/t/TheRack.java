
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ChooseOpponentEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class TheRack extends CardImpl {

    public TheRack(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{1}");

        // As The Rack enters the battlefield, choose an opponent.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseOpponentEffect(Outcome.Detriment)));
        // At the beginning of the chosen player's upkeep, The Rack deals X damage to that player, where X is 3 minus the number of cards in their hand.
        this.addAbility(new TheRackTriggeredAbility());
    }

    public TheRack(final TheRack card) {
        super(card);
    }

    @Override
    public TheRack copy() {
        return new TheRack(this);
    }
}

class TheRackTriggeredAbility extends TriggeredAbilityImpl {

    public TheRackTriggeredAbility() {
        super(Zone.BATTLEFIELD, new TheRackEffect(), false);
    }

    public TheRackTriggeredAbility(final TheRackTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public TheRackTriggeredAbility copy() {
        return new TheRackTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.UPKEEP_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getPlayerId().equals((UUID) game.getState().getValue(this.getSourceId().toString() + ChooseOpponentEffect.VALUE_KEY));
    }

    @Override
    public String getRule() {
        return "At the beginning of the chosen player's upkeep, " + super.getRule();
    }

}

class TheRackEffect extends OneShotEffect {

    public TheRackEffect() {
        super(Outcome.Benefit);
        this.staticText = "{this} deals X damage to that player, where X is 3 minus the number of cards in their hand";
    }

    public TheRackEffect(final TheRackEffect effect) {
        super(effect);
    }

    @Override
    public TheRackEffect copy() {
        return new TheRackEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID playerId = (UUID) game.getState().getValue(source.getSourceId().toString() + ChooseOpponentEffect.VALUE_KEY);
        Player chosenPlayer = game.getPlayer(playerId);
        if (chosenPlayer != null) {
            int damage = 3 - chosenPlayer.getHand().size();
            if (damage > 0) {
                chosenPlayer.damage(damage, source.getSourceId(), game, false, true);
            }
            return true;
        }

        return false;
    }
}
