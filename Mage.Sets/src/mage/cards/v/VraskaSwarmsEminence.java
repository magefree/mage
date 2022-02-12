package mage.cards.v;

import mage.abilities.LoyaltyAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.AssassinToken2;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VraskaSwarmsEminence extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("creature you control with deathtouch");

    static {
        filter.add(new AbilityPredicate(DeathtouchAbility.class));
    }

    public VraskaSwarmsEminence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{B/G}{B/G}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.VRASKA);
        this.setStartingLoyalty(5);

        // Whenever a creature you control with deathtouch deals damage to a player or planeswalker, put a +1/+1 counter on that creature.
        this.addAbility(new VraskaSwarmsEminenceTriggeredAbility(Zone.BATTLEFIELD,
                new AddCountersTargetEffect(CounterType.P1P1.createInstance()).setText("put a +1/+1 counter on that creature"),
                filter)
        );

        // -2: Create a 1/1 black Assassin creature token with deathtouch and "Whenever this creature deals damage to a planeswalker, destroy that planeswalker."
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(new AssassinToken2()), -2));
    }

    private VraskaSwarmsEminence(final VraskaSwarmsEminence card) {
        super(card);
    }

    @Override
    public VraskaSwarmsEminence copy() {
        return new VraskaSwarmsEminence(this);
    }
}

class VraskaSwarmsEminenceTriggeredAbility extends TriggeredAbilityImpl {

    private final FilterPermanent filter;

    public VraskaSwarmsEminenceTriggeredAbility(Zone zone, Effect effect, FilterPermanent filter) {
        super(zone, effect, false);
        this.filter = filter;
    }

    public VraskaSwarmsEminenceTriggeredAbility(final VraskaSwarmsEminenceTriggeredAbility ability) {
        super(ability);
        this.filter = ability.filter;
    }

    @Override
    public VraskaSwarmsEminenceTriggeredAbility copy() {
        return new VraskaSwarmsEminenceTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER
                || event.getType() == GameEvent.EventType.DAMAGED_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(event.getSourceId());
        if (permanent == null || !filter.match(permanent, getSourceId(), getControllerId(), game)) {
            return false;
        }
        Permanent damaged = game.getPermanentOrLKIBattlefield(event.getTargetId());
        if (damaged != null && !damaged.isPlaneswalker(game)) {
            return false;
        }
        getEffects().setValue("damage", event.getAmount());
        getEffects().setValue("sourceId", event.getSourceId());
        getEffects().setTargetPointer(new FixedTarget(permanent.getId(), permanent.getZoneChangeCounter(game)));
        return true;
    }

    @Override
    public String getTriggerPhrase() {
        return "Whenever a creature you control with deathtouch deals damage to a player or planeswalker, ";
    }

}
