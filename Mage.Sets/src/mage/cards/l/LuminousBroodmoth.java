package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
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
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

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
        if (zEvent.getTarget() == null
                || zEvent.getTarget().getId().equals(this.getSourceId())) {
            return false;
        }
        Permanent permanent = game.getPermanentOrLKIBattlefield(zEvent.getTarget().getId());

        if (permanent != null
                && zEvent.isDiesEvent()
                && permanent.isCreature(game)
                && !permanent.getAbilities().containsKey(FlyingAbility.getInstance().getId())
                && permanent.isControlledBy(this.controllerId)) {
            this.getEffects().setTargetPointer(new FixedTarget(zEvent.getTargetId()));
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
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (card == null || game.getState().getZone(card.getId()) != Zone.GRAVEYARD) {
            return false;
        }
        Player player = game.getPlayer(card.getOwnerId());
        if (player == null) {
            return false;
        }
        Counters countersToAdd = new Counters();
        countersToAdd.addCounter(CounterType.FLYING.createInstance());
        game.setEnterWithCounters(card.getId(), countersToAdd);
        return player.moveCards(card, Zone.BATTLEFIELD, source, game);
    }
}
