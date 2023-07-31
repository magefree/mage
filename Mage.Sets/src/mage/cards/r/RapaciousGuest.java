package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.DealCombatDamageControlledTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.token.FoodToken;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class RapaciousGuest extends CardImpl {

    private static final DynamicValue xValue = new SourcePermanentPowerCount(false);

    public RapaciousGuest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.HALFLING);
        this.subtype.add(SubType.CITIZEN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Menace
        this.addAbility(new MenaceAbility());

        // Whenever one or more creatures you control deal combat damage to a player, create a Food token.
        this.addAbility(new DealCombatDamageControlledTriggeredAbility(
                new CreateTokenEffect(new FoodToken())
        ));

        // Whenever you sacrifice a Food, put a +1/+1 counter on Rapacious Guest.
        this.addAbility(new RapaciousGuestFoodTriggeredAbility());

        // When Rapacious Guest leaves the battlefield, target opponent loses life equal to its power.
        Ability ability = new LeavesBattlefieldTriggeredAbility(
                new LoseLifeTargetEffect(xValue).setText("target opponent loses life equal to its power."),
                false
        );
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private RapaciousGuest(final RapaciousGuest card) {
        super(card);
    }

    @Override
    public RapaciousGuest copy() {
        return new RapaciousGuest(this);
    }
}

class RapaciousGuestFoodTriggeredAbility extends TriggeredAbilityImpl {

    RapaciousGuestFoodTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P1.createInstance()));
        setLeavesTheBattlefieldTrigger(true);
        setTriggerPhrase("Whenever you sacrifice a Food, ");
    }

    private RapaciousGuestFoodTriggeredAbility(final RapaciousGuestFoodTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public RapaciousGuestFoodTriggeredAbility copy() {
        return new RapaciousGuestFoodTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SACRIFICED_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getPlayerId().equals(this.getControllerId())
                && game.getLastKnownInformation(event.getTargetId(), Zone.BATTLEFIELD).hasSubtype(SubType.FOOD, game);
    }
}