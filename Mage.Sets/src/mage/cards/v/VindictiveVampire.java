package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 * @author TheElk801
 */
public final class VindictiveVampire extends CardImpl {

    private static FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("another creature you control");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public VindictiveVampire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever another creature you control dies, Vindictive Vampire deals 1 damage to each opponent and you gain 1 life.
        Ability ability = new VindictiveVampireTriggeredAbility(Zone.ALL, new DamagePlayersEffect(1, TargetController.OPPONENT),
                false,
                filter,
                true
        );
        ability.addEffect(new GainLifeEffect(1).concatBy("and"));
        this.addAbility(ability);
    }

    private VindictiveVampire(final VindictiveVampire card) {
        super(card);
    }

    @Override
    public VindictiveVampire copy() {
        return new VindictiveVampire(this);
    }
}

class VindictiveVampireTriggeredAbility extends TriggeredAbilityImpl {

    protected FilterPermanent filter;
    private final boolean setTargetPointer;

    public VindictiveVampireTriggeredAbility(Zone zone, Effect effect,
            boolean optional, FilterPermanent filter, boolean setTargetPointer) {
        super(zone, effect, optional);
        this.filter = filter;
        this.setTargetPointer = setTargetPointer;
    }

    public VindictiveVampireTriggeredAbility(final VindictiveVampireTriggeredAbility ability) {
        super(ability);
        this.filter = ability.filter;
        this.setTargetPointer = ability.setTargetPointer;
    }

    @Override
    public VindictiveVampireTriggeredAbility copy() {
        return new VindictiveVampireTriggeredAbility(this);
    }

    @Override
    public boolean isInUseableZone(Game game, MageObject source, GameEvent event) {
        Permanent sourcePermanent;
        if (game.getState().getZone(getSourceId()) == Zone.BATTLEFIELD) {
            sourcePermanent = game.getPermanent(getSourceId());
        } else {
            sourcePermanent = (Permanent) game.getPermanentOrLKIBattlefield(getSourceId());
        }
        if (sourcePermanent == null) {
            return false;
        }
        return hasSourceObjectAbility(game, sourcePermanent, event);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (game.getPermanentOrLKIBattlefield(getSourceId()) == null) {
            return false;
        }
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (zEvent.isDiesEvent()) {
            if (zEvent.getTarget() != null) {
                if (filter.match(zEvent.getTarget(), getControllerId(), this, game)) {
                    if (setTargetPointer) {
                        for (Effect effect : this.getEffects()) {
                            effect.setTargetPointer(new FixedTarget(event.getTargetId(), game));
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getTriggerPhrase() {
        return "Whenever " + filter.getMessage() + " dies, " ;
    }
}
