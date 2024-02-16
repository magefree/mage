package mage.cards.t;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.CompoundCondition;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.condition.common.MeldCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.MeldEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeGroupEvent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TitaniaVoiceOfGaea extends CardImpl {

    private static final Condition condition = new CompoundCondition(
            new CardsInControllerGraveyardCondition(4, StaticFilters.FILTER_CARD_LAND),
            new MeldCondition("Argoth, Sanctum of Nature", CardType.LAND)
    );

    public TitaniaVoiceOfGaea(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        this.meldsWithClazz = mage.cards.a.ArgothSanctumOfNature.class;
        this.meldsToClazz = mage.cards.t.TitaniaGaeaIncarnate.class;

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Whenever one or more land cards are put into your graveyard from anywhere, you gain 2 life.
        this.addAbility(new TitaniaVoiceOfGaeaTriggeredAbility());

        // At the beginning of your upkeep, if there are four or more land cards in your graveyard and you both own and control Titania, Voice of Gaea and a land named Argoth, Sanctum of Nature, exile them, then meld them into Titania, Gaea Incarnate.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfUpkeepTriggeredAbility(new MeldEffect(
                        "Argoth, Sanctum of Nature", "Titania, Gaea Incarnate"
                ), TargetController.YOU, false), condition, "At the beginning of your upkeep, " +
                "if there are four or more land cards in your graveyard and you both own and control {this} " +
                "and a land named Argoth, Sanctum of Nature, exile them, then meld them into Titania, Gaea Incarnate."
        ));
    }

    private TitaniaVoiceOfGaea(final TitaniaVoiceOfGaea card) {
        super(card);
    }

    @Override
    public TitaniaVoiceOfGaea copy() {
        return new TitaniaVoiceOfGaea(this);
    }
}

class TitaniaVoiceOfGaeaTriggeredAbility extends TriggeredAbilityImpl {

    public TitaniaVoiceOfGaeaTriggeredAbility() {
        super(Zone.BATTLEFIELD, new GainLifeEffect(2));
    }

    private TitaniaVoiceOfGaeaTriggeredAbility(final TitaniaVoiceOfGaeaTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public TitaniaVoiceOfGaeaTriggeredAbility copy() {
        return new TitaniaVoiceOfGaeaTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE_GROUP;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeGroupEvent zEvent = (ZoneChangeGroupEvent) event;
        return zEvent != null
                && zEvent.getToZone() == Zone.GRAVEYARD
                && zEvent.getCards() != null
                && zEvent.getCards()
                .stream()
                .filter(card -> card.isLand(game))
                .map(Card::getOwnerId)
                .anyMatch(this::isControlledBy);
    }

    @Override
    public String getRule() {
        return "Whenever one or more land cards are put into your graveyard from anywhere, you gain 2 life.";
    }
}
