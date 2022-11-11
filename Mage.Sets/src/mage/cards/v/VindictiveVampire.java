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
import mage.filter.StaticFilters;
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

    public VindictiveVampire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever another creature you control dies, Vindictive Vampire deals 1 damage to each opponent and you gain 1 life.
        Ability ability = new VindictiveVampireTriggeredAbility(Zone.ALL, new DamagePlayersEffect(1, TargetController.OPPONENT));
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

    public VindictiveVampireTriggeredAbility(Zone zone, Effect effect) {
        super(zone, effect, false);
        setTriggerPhrase("Whenever another creature you control dies, ");
    }

    public VindictiveVampireTriggeredAbility(final VindictiveVampireTriggeredAbility ability) {
        super(ability);
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
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (!zEvent.isDiesEvent()
                || zEvent.getTarget() == null
                || game.getPermanentOrLKIBattlefield(getSourceId()) == null) {
            return false;
        }

        if (!StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE.match(zEvent.getTarget(), getControllerId(), this, game)) {
            return false;
        }

        for (Effect effect : this.getEffects()) {
            effect.setTargetPointer(new FixedTarget(event.getTargetId(), game));
        }
        return true;
    }
}
