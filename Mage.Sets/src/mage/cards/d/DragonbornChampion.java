package mage.cards.d;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DragonbornChampion extends CardImpl {

    public DragonbornChampion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{G}");

        this.subtype.add(SubType.DRAGON);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(3);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever a source you control deals 5 or more damage to a player, draw a card.
        this.addAbility(new DragonbornChampionTriggeredAbility());
    }

    private DragonbornChampion(final DragonbornChampion card) {
        super(card);
    }

    @Override
    public DragonbornChampion copy() {
        return new DragonbornChampion(this);
    }
}

class DragonbornChampionTriggeredAbility extends TriggeredAbilityImpl {

    DragonbornChampionTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1));
    }

    private DragonbornChampionTriggeredAbility(final DragonbornChampionTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return isControlledBy(game.getControllerId(event.getSourceId()))
                && event.getAmount() >= 5;
    }

    @Override
    public DragonbornChampionTriggeredAbility copy() {
        return new DragonbornChampionTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever a source you control deals 5 or more damage to a player, draw a card.";
    }
}
