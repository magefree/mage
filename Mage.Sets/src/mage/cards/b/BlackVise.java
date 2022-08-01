package mage.cards.b;

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

import java.util.UUID;

/**
 * @author dustinconrad
 */
public final class BlackVise extends CardImpl {

    public BlackVise(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        // As Black Vise enters the battlefield, choose an opponent.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseOpponentEffect(Outcome.Detriment)));
        // At the beginning of the chosen player's upkeep, Black Vise deals X damage to that player, where X is the number of cards in their hand minus 4.
        this.addAbility(new BlackViseTriggeredAbility());
    }

    private BlackVise(final BlackVise card) {
        super(card);
    }

    @Override
    public BlackVise copy() {
        return new BlackVise(this);
    }
}

class BlackViseTriggeredAbility extends TriggeredAbilityImpl {

    public BlackViseTriggeredAbility() {
        super(Zone.BATTLEFIELD, new BlackViseEffect(), false);
        setTriggerPhrase("At the beginning of the chosen player's upkeep, ");
    }

    public BlackViseTriggeredAbility(final BlackViseTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public BlackViseTriggeredAbility copy() {
        return new BlackViseTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.UPKEEP_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getPlayerId().equals(game.getState().getValue(getSourceId().toString() + ChooseOpponentEffect.VALUE_KEY));
    }
}

class BlackViseEffect extends OneShotEffect {

    public BlackViseEffect() {
        super(Outcome.Detriment);
        this.staticText = "{this} deals X damage to that player, where X is the number of cards in their hand minus 4";
    }

    public BlackViseEffect(final BlackViseEffect effect) {
        super(effect);
    }

    @Override
    public BlackViseEffect copy() {
        return new BlackViseEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID playerId = (UUID) game.getState().getValue(source.getSourceId().toString() + ChooseOpponentEffect.VALUE_KEY);
        Player chosenPlayer = game.getPlayer(playerId);
        if (chosenPlayer != null) {
            int damage = chosenPlayer.getHand().size() - 4;
            if (damage > 0) {
                chosenPlayer.damage(damage, source.getSourceId(), source, game);
            }
            return true;
        }
        return false;
    }
}
