package mage.cards.m;

import mage.MageInt;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.TurnedFaceUpSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.abilities.keyword.DisguiseAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MistwaySpy extends CardImpl {

    public MistwaySpy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.DETECTIVE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Disguise {1}{U}
        this.addAbility(new DisguiseAbility(this, new ManaCostsImpl<>("{1}{U}")));

        // When Mistway Spy is turned face up, until end of turn, whenever a creature you control deals combat damage to a player, investigate.
        this.addAbility(new TurnedFaceUpSourceTriggeredAbility(
                new CreateDelayedTriggeredAbilityEffect(new MistwaySpyTriggeredAbility())
                        .setText("until end of turn, whenever a creature you control deals combat damage to a player, investigate")
        ));
    }

    private MistwaySpy(final MistwaySpy card) {
        super(card);
    }

    @Override
    public MistwaySpy copy() {
        return new MistwaySpy(this);
    }
}

class MistwaySpyTriggeredAbility extends DelayedTriggeredAbility {

    MistwaySpyTriggeredAbility() {
        super(new InvestigateEffect(), Duration.EndOfTurn, false, false);
        setTriggerPhrase("Whenever a creature you control deals combat damage to a player, ");
    }

    private MistwaySpyTriggeredAbility(final MistwaySpyTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public MistwaySpyTriggeredAbility copy() {
        return new MistwaySpyTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return ((DamagedEvent) event).isCombatDamage() && isControlledBy(game.getControllerId(event.getSourceId()));
    }
}
