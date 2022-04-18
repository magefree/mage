package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToHandEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class ErebossTitan extends CardImpl {

    public ErebossTitan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}{B}{B}");
        this.subtype.add(SubType.GIANT);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // As long as your opponents control no creatures, Erebos's Titan has indestructible.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(
                        IndestructibleAbility.getInstance(), Duration.WhileOnBattlefield),
                new PermanentsOnTheBattlefieldCondition(
                        StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE, ComparisonType.EQUAL_TO, 0, false),
                "As long as your opponents control no creatures, {this} has indestructible")));

        // Whenever a creature card leaves an opponent's graveyard, you may discard a card. If you do, return Erebos's Titan from your graveyard to your hand.
        this.addAbility(new ErebossTitanTriggeredAbility());

    }

    private ErebossTitan(final ErebossTitan card) {
        super(card);
    }

    @Override
    public ErebossTitan copy() {
        return new ErebossTitan(this);
    }
}

class ErebossTitanTriggeredAbility extends TriggeredAbilityImpl {

    public ErebossTitanTriggeredAbility() {
        super(Zone.GRAVEYARD, new DoIfCostPaid(new ReturnSourceFromGraveyardToHandEffect(), new DiscardCardCost()));
    }

    public ErebossTitanTriggeredAbility(final ErebossTitanTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ErebossTitanTriggeredAbility copy() {
        return new ErebossTitanTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (zEvent.getFromZone() == Zone.GRAVEYARD) {
            Card card = game.getCard(zEvent.getTargetId());
            Player controller = game.getPlayer(getControllerId());
            return card != null
                    && card.isCreature(game)
                    && controller != null
                    && controller.hasOpponent(card.getOwnerId(), game);
        }
        return false;
    }

    @Override
    public String getTriggerPhrase() {
        return "Whenever a creature card leaves an opponent's graveyard, " ;
    }
}
