
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ChoosePlayerEffect;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public final class SewerNemesis extends CardImpl {

    public SewerNemesis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add(SubType.HORROR);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // As Sewer Nemesis enters the battlefield, choose a player.
        this.addAbility(new AsEntersBattlefieldAbility(new ChoosePlayerEffect(Outcome.Detriment)));
        // Sewer Nemesis's power and toughness are each equal to the number of cards in the chosen player's graveyard.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetBasePowerToughnessSourceEffect(new CardsInTargetOpponentsGraveyardCount())));
        // Whenever the chosen player casts a spell, that player puts the top card of their library into their graveyard.
        this.addAbility(new SewerNemesisTriggeredAbility());

    }

    private SewerNemesis(final SewerNemesis card) {
        super(card);
    }

    @Override
    public SewerNemesis copy() {
        return new SewerNemesis(this);
    }
}

class CardsInTargetOpponentsGraveyardCount implements DynamicValue {

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        if (sourceAbility != null) {
            UUID playerId = (UUID) game.getState().getValue(sourceAbility.getSourceId() + "_player");
            Player chosenPlayer = game.getPlayer(playerId);
            if (chosenPlayer != null) {
                return chosenPlayer.getGraveyard().size();
            }
        }
        return 0;
    }

    @Override
    public CardsInTargetOpponentsGraveyardCount copy() {
        return new CardsInTargetOpponentsGraveyardCount();
    }

    @Override
    public String getMessage() {
        return "cards in the chosen player's graveyard";
    }

    @Override
    public String toString() {
        return "1";
    }
}

class SewerNemesisTriggeredAbility extends TriggeredAbilityImpl {

    public SewerNemesisTriggeredAbility() {
        super(Zone.BATTLEFIELD, new MillCardsTargetEffect(1), false);
    }

    public SewerNemesisTriggeredAbility(final SewerNemesisTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        UUID playerId = (UUID) game.getState().getValue(getSourceId() + "_player");
        if (playerId.equals(event.getPlayerId())) {
            this.getEffects().get(0).setTargetPointer(new FixedTarget(playerId));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever the chosen player casts a spell, that player mills a card.";
    }

    @Override
    public SewerNemesisTriggeredAbility copy() {
        return new SewerNemesisTriggeredAbility(this);
    }
}
