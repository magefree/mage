package mage.cards.f;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.HellbentCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.PlayAdditionalLandsControllerEffect;
import mage.abilities.effects.common.discard.DiscardControllerEffect;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 * @author Cguy7777
 */
public final class FlubsTheFool extends CardImpl {

    public FlubsTheFool(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{U}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.FROG);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(0);
        this.toughness = new MageInt(5);

        // You may play an additional land on each of your turns.
        this.addAbility(new SimpleStaticAbility(
                new PlayAdditionalLandsControllerEffect(1, Duration.WhileOnBattlefield)));

        // Whenever you play a land or cast a spell, draw a card if you have no cards in hand. Otherwise, discard a card.
        this.addAbility(new FlubsTheFoolTriggeredAbility());
    }

    private FlubsTheFool(final FlubsTheFool card) {
        super(card);
    }

    @Override
    public FlubsTheFool copy() {
        return new FlubsTheFool(this);
    }
}

class FlubsTheFoolTriggeredAbility extends TriggeredAbilityImpl {

    FlubsTheFoolTriggeredAbility() {
        super(Zone.BATTLEFIELD, new ConditionalOneShotEffect(
                new DrawCardSourceControllerEffect(1),
                new DiscardControllerEffect(1),
                HellbentCondition.instance,
                "draw a card if you have no cards in hand. Otherwise, discard a card"));
        setTriggerPhrase("Whenever you play a land or cast a spell, ");
    }

    private FlubsTheFoolTriggeredAbility(final FlubsTheFoolTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.LAND_PLAYED || event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getPlayerId().equals(controllerId);
    }

    @Override
    public FlubsTheFoolTriggeredAbility copy() {
        return new FlubsTheFoolTriggeredAbility(this);
    }
}
