
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.CardsInHandCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public final class HollowbornBarghest extends CardImpl {

    private static final String rule = "At the beginning of your upkeep, if you have no cards in hand, each opponent loses 2 life.";

    public HollowbornBarghest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{B}{B}");
        this.subtype.add(SubType.DEMON);
        this.subtype.add(SubType.HOUND);

        this.power = new MageInt(7);
        this.toughness = new MageInt(6);

        // At the beginning of your upkeep, if you have no cards in hand, each opponent loses 2 life.
        Condition condition = new CardsInHandCondition(ComparisonType.EQUAL_TO, 0);
        TriggeredAbility ability = new BeginningOfUpkeepTriggeredAbility(new HollowbornBarghestEffect(), TargetController.YOU, false);
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability, condition, rule));

        // At the beginning of each opponent's upkeep, if that player has no cards in hand, he or she loses 2 life.
        this.addAbility(new HollowbornBarghestTriggeredAbility());
    }

    public HollowbornBarghest(final HollowbornBarghest card) {
        super(card);
    }

    @Override
    public HollowbornBarghest copy() {
        return new HollowbornBarghest(this);
    }
}

class HollowbornBarghestEffect extends OneShotEffect {

    public HollowbornBarghestEffect() {
        super(Outcome.Damage);
        staticText = "Each opponent loses 2 life";
    }

    public HollowbornBarghestEffect(final HollowbornBarghestEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            game.getPlayer(opponentId).loseLife(2, game, false);
        }
        return true;
    }

    @Override
    public HollowbornBarghestEffect copy() {
        return new HollowbornBarghestEffect(this);
    }

}

class HollowbornBarghestTriggeredAbility extends TriggeredAbilityImpl {

    public HollowbornBarghestTriggeredAbility() {
        super(Zone.BATTLEFIELD, null);
    }

    public HollowbornBarghestTriggeredAbility(final HollowbornBarghestTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public HollowbornBarghestTriggeredAbility copy() {
        return new HollowbornBarghestTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.UPKEEP_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (game.getOpponents(controllerId).contains(event.getPlayerId())) {
            Player opponent = game.getPlayer(event.getPlayerId());
            if (opponent != null && opponent.getHand().isEmpty()) {
                opponent.loseLife(2, game, false);
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "At the beginning of each opponent's upkeep, if that player has no cards in hand, he or she loses 2 life.";
    }
}
