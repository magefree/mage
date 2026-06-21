package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author muz
 */
public final class CaptainMarvelApexAvenger extends CardImpl {

    public CaptainMarvelApexAvenger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KREE);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Double strike
        this.addAbility(DoubleStrikeAbility.getInstance());

        // Indestructible
        this.addAbility(IndestructibleAbility.getInstance());

        // Whenever you put one or more counters on another creature, if it's not a Kree, you may put the same number and kind of counters on Captain Marvel.
        this.addAbility(new CaptainMarvelApexAvengerTriggeredAbility());
    }

    private CaptainMarvelApexAvenger(final CaptainMarvelApexAvenger card) {
        super(card);
    }

    @Override
    public CaptainMarvelApexAvenger copy() {
        return new CaptainMarvelApexAvenger(this);
    }
}

class CaptainMarvelApexAvengerTriggeredAbility extends TriggeredAbilityImpl {

    CaptainMarvelApexAvengerTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CaptainMarvelApexAvengerEffect(), true);
    }

    private CaptainMarvelApexAvengerTriggeredAbility(final CaptainMarvelApexAvengerTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COUNTERS_ADDED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!isControlledBy(event.getPlayerId()) || event.getTargetId().equals(getSourceId())) {
            return false;
        }

        Permanent permanent = game.getPermanentOrLKIBattlefield(event.getTargetId());
        if (permanent == null) {
            permanent = game.getPermanentEntering(event.getTargetId());
        }
        if (permanent == null || !permanent.isCreature(game) || permanent.hasSubtype(SubType.KREE, game)) {
            return false;
        }

        getEffects().setValue("counterType", event.getData());
        getEffects().setValue("counterAmount", event.getAmount());
        return true;
    }

    @Override
    public CaptainMarvelApexAvengerTriggeredAbility copy() {
        return new CaptainMarvelApexAvengerTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever you put one or more counters on another creature, if it's not a Kree, " +
                "you may put the same number and kind of counters on {this}.";
    }
}

class CaptainMarvelApexAvengerEffect extends OneShotEffect {

    CaptainMarvelApexAvengerEffect() {
        super(Outcome.Benefit);
        staticText = "put the same number and kind of counters on {this}";
    }

    private CaptainMarvelApexAvengerEffect(final CaptainMarvelApexAvengerEffect effect) {
        super(effect);
    }

    @Override
    public CaptainMarvelApexAvengerEffect copy() {
        return new CaptainMarvelApexAvengerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        CounterType counterType = CounterType.findByName((String) getValue("counterType"));
        Integer amount = (Integer) getValue("counterAmount");
        return permanent != null
                && counterType != null
                && amount != null
                && permanent.addCounters(counterType.createInstance(amount), source.getControllerId(), source, game);
    }
}
