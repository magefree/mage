
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.SacrificeControllerEffect;
import mage.abilities.keyword.DevoidAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author fireshoes
 */
public final class SmotheringAbomination extends CardImpl {

    public SmotheringAbomination(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}{B}");
        this.subtype.add(SubType.ELDRAZI);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Devoid
        this.addAbility(new DevoidAbility(this.color));

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // At the beginning of your upkeep, sacrifice a creature
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new SacrificeControllerEffect(
                StaticFilters.FILTER_PERMANENT_CREATURE, 1, null), TargetController.YOU, false));

        // Whenever you sacrifice a creature, draw a card.
        this.addAbility(new SmotheringAbominationTriggeredAbility());
    }

    private SmotheringAbomination(final SmotheringAbomination card) {
        super(card);
    }

    @Override
    public SmotheringAbomination copy() {
        return new SmotheringAbomination(this);
    }
}

class SmotheringAbominationTriggeredAbility extends TriggeredAbilityImpl {

    public SmotheringAbominationTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1));
        setLeavesTheBattlefieldTrigger(true);
        setTriggerPhrase("Whenever you sacrifice a creature, ");
    }

    public SmotheringAbominationTriggeredAbility(final SmotheringAbominationTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SmotheringAbominationTriggeredAbility copy() {
        return new SmotheringAbominationTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SACRIFICED_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getPlayerId().equals(this.getControllerId())
                && game.getLastKnownInformation(event.getTargetId(), Zone.BATTLEFIELD).isCreature(game);
    }
}
