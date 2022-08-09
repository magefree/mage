package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.Card;
import mage.cards.Cards;
import mage.constants.SubType;
import mage.abilities.keyword.DelveAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.util.CardUtil;

/**
 *
 * @author weirddan455
 */
public final class MurktideRegent extends CardImpl {

    public MurktideRegent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{U}{U}");

        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Delve
        this.addAbility(new DelveAbility());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Murktide Regent enters the battlefield with a +1/+1 counter on it for each instant and sorcery card exiled with it.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(), MurktideRegentValue.instance, false),
                "with a +1/+1 counter on it for each instant and sorcery card exiled with it"
        ));

        // Whenever an instant or sorcery card leaves your graveyard, put a +1/+1 counter on Murktide Regent.
        this.addAbility(new MurktideRegentTriggeredAbility());
    }

    private MurktideRegent(final MurktideRegent card) {
        super(card);
    }

    @Override
    public MurktideRegent copy() {
        return new MurktideRegent(this);
    }
}

enum MurktideRegentValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int amount = 0;
        Cards delvedCards = (Cards) game.getState().getValue(CardUtil.getCardZoneString("delvedCards", sourceAbility.getSourceId(), game));
        if (delvedCards != null) {
            for (UUID cardId : delvedCards) {
                Card card = game.getCard(cardId);
                if (card != null && card.isInstantOrSorcery(game)) {
                    amount++;
                }
            }
        }
        return amount;
    }

    @Override
    public MurktideRegentValue copy() {
        return instance;
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "instant and sorcery card exiled with it";
    }
}

class MurktideRegentTriggeredAbility extends TriggeredAbilityImpl {

    public MurktideRegentTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P1.createInstance()));
        setTriggerPhrase("Whenever an instant or sorcery card leaves your graveyard, ");
    }

    private MurktideRegentTriggeredAbility(final MurktideRegentTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public MurktideRegentTriggeredAbility copy() {
        return new MurktideRegentTriggeredAbility(this);
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
            return card != null && card.isInstantOrSorcery(game) && card.getOwnerId().equals(getControllerId());
        }
        return false;
    }
}
