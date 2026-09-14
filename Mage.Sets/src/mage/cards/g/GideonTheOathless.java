package mage.cards.g;

import java.util.UUID;
import mage.abilities.LoyaltyAbility;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.stack.StackAbility;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.WardAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class GideonTheOathless extends CardImpl {

    public GideonTheOathless(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MERCENARY);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Ward -- Discard a card.
        this.addAbility(new WardAbility(new DiscardCardCost()));

        // Whenever a creature an opponent controls enters, Gideon deals 1 damage to that player.
        this.addAbility(new GideonTheOathlessCreatureAbility());

        // Whenever an opponent activates a loyalty ability, Gideon deals 1 damage to that player.
        this.addAbility(new GideonTheOathlessLoyaltyAbility());
    }

    private GideonTheOathless(final GideonTheOathless card) {
        super(card);
    }

    @Override
    public GideonTheOathless copy() {
        return new GideonTheOathless(this);
    }
}


class GideonTheOathlessCreatureAbility extends TriggeredAbilityImpl {

    GideonTheOathlessCreatureAbility() {
        super(Zone.BATTLEFIELD, new DamageTargetEffect(1), true);
    }

    private GideonTheOathlessCreatureAbility(final GideonTheOathlessCreatureAbility ability) {
        super(ability);
    }

    @Override
    public GideonTheOathlessCreatureAbility copy() {
        return new GideonTheOathlessCreatureAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (game.getOpponents(this.controllerId).contains(event.getPlayerId())) {
            EntersTheBattlefieldEvent zEvent = (EntersTheBattlefieldEvent) event;
            Card card = zEvent.getTarget();
            if (card != null && card.isCreature(game)) {
                for (Effect effect : this.getEffects()) {
                    effect.setTargetPointer(new FixedTarget(event.getPlayerId()));
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature an opponent controls enters, {this} deals 1 damage to that player.";
    }
}

class GideonTheOathlessLoyaltyAbility extends TriggeredAbilityImpl {

    GideonTheOathlessLoyaltyAbility() {
        super(Zone.BATTLEFIELD, new DamageTargetEffect(1), true);
    }

    private GideonTheOathlessLoyaltyAbility(final GideonTheOathlessLoyaltyAbility ability) {
        super(ability);
    }

    @Override
    public GideonTheOathlessLoyaltyAbility copy() {
        return new GideonTheOathlessLoyaltyAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ACTIVATED_ABILITY;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Player player = game.getPlayer(event.getPlayerId());
        if (player == null || !game.getOpponents(this.controllerId).contains(event.getPlayerId())) {
            return false;
        }

        StackAbility stackAbility = (StackAbility) game.getStack().getStackObject(event.getSourceId());
        if (stackAbility == null || !(stackAbility.getStackAbility() instanceof LoyaltyAbility)) {
            return false;
        }

        for (Effect effect : this.getEffects()) {
            effect.setTargetPointer(new FixedTarget(event.getPlayerId(), game));
        }
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever an opponent activates a loyalty ability, {this} deals 1 damage to that player.";
    }
}
