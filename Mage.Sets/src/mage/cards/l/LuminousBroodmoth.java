package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.MutateAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.counters.Counters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTargets;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class LuminousBroodmoth extends CardImpl {

    public LuminousBroodmoth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{W}");

        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever a creature you control without flying dies, return it to the battlefield under its owner's control with a flying counter on it.
        this.addAbility(new LuminousBroodmothTriggeredAbility());
    }

    private LuminousBroodmoth(final LuminousBroodmoth card) {
        super(card);
    }

    @Override
    public LuminousBroodmoth copy() {
        return new LuminousBroodmoth(this);
    }
}

class LuminousBroodmothTriggeredAbility extends TriggeredAbilityImpl {

    LuminousBroodmothTriggeredAbility() {
        super(Zone.BATTLEFIELD, new LuminousBroodmothEffect(), false);
    }

    private LuminousBroodmothTriggeredAbility(final LuminousBroodmothTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public LuminousBroodmothTriggeredAbility copy() {
        return new LuminousBroodmothTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        Permanent permanent = zEvent.getTarget();
        if (permanent != null
                && zEvent.isDiesEvent()
                && permanent.isCreature(game)
                && !permanent.getAbilities().containsKey(FlyingAbility.getInstance().getId())
                && permanent.isControlledBy(this.controllerId)) {
            this.getEffects().setTargetPointer(new FixedTargets(MutateAbility.getAllCardsFromPermanentLeftBattlefield(permanent), game));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature you control without flying dies, "
                + "return it to the battlefield under its owner's control with a flying counter on it.";
    }
}

class LuminousBroodmothEffect extends OneShotEffect {

    LuminousBroodmothEffect() {
        super(Outcome.PutCardInPlay);
    }

    private LuminousBroodmothEffect(final LuminousBroodmothEffect effect) {
        super(effect);
    }

    @Override
    public LuminousBroodmothEffect copy() {
        return new LuminousBroodmothEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Set<Card> toReturn = new CardsImpl(getTargetPointer().getTargets(game, source)).getCards(game)
                .stream()
                .filter(card -> game.getState().getZone(card.getId()) == Zone.GRAVEYARD)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        for (Card card: toReturn) {
            game.setEnterWithCounters(card.getId(), new Counters().addCounter(CounterType.FLYING.createInstance()));
        }
        return controller.moveCards(toReturn, Zone.BATTLEFIELD, source, game, false, false, true, null);
    }
}
