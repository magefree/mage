package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.EntersBattlefieldWithCountersAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FlitterwingNuisance extends CardImpl {

    public FlitterwingNuisance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}");

        this.subtype.add(SubType.FAERIE);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // This creature enters with a -1/-1 counter on it.
        this.addAbility(new EntersBattlefieldWithCountersAbility(CounterType.M1M1.createInstance(1)));

        // {2}{U}, Remove a counter from this creature: Whenever a creature you control deals combat damage to a player or planeswalker this turn, draw a card.
        Ability ability = new SimpleActivatedAbility(
                new CreateDelayedTriggeredAbilityEffect(new FlitterwingNuisanceTriggeredAbility()),
                new ManaCostsImpl<>("{2}{U}")
        );
        ability.addCost(new RemoveCountersSourceCost(1));
        this.addAbility(ability);
    }

    private FlitterwingNuisance(final FlitterwingNuisance card) {
        super(card);
    }

    @Override
    public FlitterwingNuisance copy() {
        return new FlitterwingNuisance(this);
    }
}

class FlitterwingNuisanceTriggeredAbility extends DelayedTriggeredAbility {

    FlitterwingNuisanceTriggeredAbility() {
        super(new DrawCardSourceControllerEffect(1), Duration.EndOfTurn, false, false);
        setTriggerPhrase("Whenever a creature you control deals combat damage to a player or planeswalker this turn, ");
    }

    private FlitterwingNuisanceTriggeredAbility(final FlitterwingNuisanceTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public FlitterwingNuisanceTriggeredAbility copy() {
        return new FlitterwingNuisanceTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER
                || event.getType() == GameEvent.EventType.DAMAGED_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!((DamagedEvent) event).isCombatDamage()) {
            return false;
        }
        Permanent damagingPermanent = game.getPermanent(event.getSourceId());
        if (damagingPermanent == null
                || !damagingPermanent.isCreature(game)
                || !damagingPermanent.isControlledBy(getControllerId())) {
            return false;
        }
        if (event.getType() == GameEvent.EventType.DAMAGED_PLAYER) {
            return true;
        }
        Permanent damagedPermanent = game.getPermanent(event.getTargetId());
        return damagedPermanent != null && damagedPermanent.isPlaneswalker(game);
    }
}
