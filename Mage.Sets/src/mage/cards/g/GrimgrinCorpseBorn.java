
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.DontUntapInControllersUntapStepSourceEffect;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public final class GrimgrinCorpseBorn extends CardImpl {

    public GrimgrinCorpseBorn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{B}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Grimgrin, Corpse-Born enters the battlefield tapped and doesn't untap during your untap step.
        Ability ability = new EntersBattlefieldTappedAbility(
                "{this} enters the battlefield tapped and doesn't untap during your untap step.");
        ability.addEffect(new DontUntapInControllersUntapStepSourceEffect());
        this.addAbility(ability);

        // Sacrifice another creature: Untap Grimgrin and put a +1/+1 counter on it.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new UntapSourceEffect(),
                new SacrificeTargetCost(new TargetControlledCreaturePermanent(1, 1, StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE, false)));
        ability.addEffect(new AddCountersSourceEffect(CounterType.P1P1.createInstance()));
        this.addAbility(ability);
        // Whenever Grimgrin attacks, destroy target creature defending player controls, then put a +1/+1 counter on Grimgrin.
        this.addAbility(new GrimgrinCorpseBornAbility());
    }

    private GrimgrinCorpseBorn(final GrimgrinCorpseBorn card) {
        super(card);
    }

    @Override
    public GrimgrinCorpseBorn copy() {
        return new GrimgrinCorpseBorn(this);
    }
}

class GrimgrinCorpseBornAbility extends TriggeredAbilityImpl {

    public GrimgrinCorpseBornAbility() {
        super(Zone.BATTLEFIELD, new DestroyTargetEffect());
        this.addEffect(new AddCountersSourceEffect(CounterType.P1P1.createInstance()));
    }

    public GrimgrinCorpseBornAbility(final GrimgrinCorpseBornAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ATTACKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getSourceId().equals(this.getSourceId())) {
            FilterCreaturePermanent filter = new FilterCreaturePermanent("creature defending player controls");
            UUID defenderId = game.getCombat().getDefenderId(sourceId);
            filter.add(new ControllerIdPredicate(defenderId));

            this.getTargets().clear();
            TargetCreaturePermanent target = new TargetCreaturePermanent(filter);
            this.addTarget(target);
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} attacks, destroy target creature defending player controls, then put a +1/+1 counter on {this}.";
    }

    @Override
    public GrimgrinCorpseBornAbility copy() {
        return new GrimgrinCorpseBornAbility(this);
    }
}
