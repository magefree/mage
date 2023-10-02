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
import mage.abilities.effects.common.LoseLifeTargetEffect;
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
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth
 */
public final class HollowbornBarghest extends CardImpl {

    private static final String rule = "At the beginning of your upkeep, if you have no cards in hand, each opponent loses 2 life.";

    public HollowbornBarghest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{B}{B}");
        this.subtype.add(SubType.DEMON);
        this.subtype.add(SubType.DOG);

        this.power = new MageInt(7);
        this.toughness = new MageInt(6);

        // At the beginning of your upkeep, if you have no cards in hand, each opponent loses 2 life.
        Condition condition = new CardsInHandCondition(ComparisonType.EQUAL_TO, 0);
        TriggeredAbility ability = new BeginningOfUpkeepTriggeredAbility(
                new HollowbornBarghestEffect(),
                TargetController.YOU,
                false);
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                ability,
                condition,
                rule));

        // At the beginning of each opponent's upkeep, if that player has no cards in hand, they lose 2 life.
        this.addAbility(new HollowbornBarghestTriggeredAbility());
    }

    private HollowbornBarghest(final HollowbornBarghest card) {
        super(card);
    }

    @Override
    public HollowbornBarghest copy() {
        return new HollowbornBarghest(this);
    }
}

class HollowbornBarghestEffect extends OneShotEffect {

    public HollowbornBarghestEffect() {
        super(Outcome.Benefit);
        staticText = "Each opponent loses 2 life";
    }

    private HollowbornBarghestEffect(final HollowbornBarghestEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            Player opponent = game.getPlayer(opponentId);
            if (opponent != null) {
                game.getPlayer(opponentId).loseLife(2, game, source, false);
            }
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
        super(Zone.BATTLEFIELD, new LoseLifeTargetEffect(2));
    }

    private HollowbornBarghestTriggeredAbility(final HollowbornBarghestTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public HollowbornBarghestTriggeredAbility copy() {
        return new HollowbornBarghestTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.UPKEEP_STEP_PRE
                && game.getOpponents(controllerId).contains(event.getPlayerId());
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Player opponent = game.getPlayer(event.getPlayerId());
        if (opponent != null
                && opponent.getHand().isEmpty()) {
            this.getEffects().get(0).setTargetPointer(new FixedTarget(opponent.getId()));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "At the beginning of each opponent's upkeep, if that player has no cards in hand, they lose 2 life.";
    }
}
