package mage.cards.p;

import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.OffspringAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.MageObjectReferencePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.DefineByTriggerTargetAdjuster;

import java.util.UUID;

/**
 *
 * @author Grath
 */
public final class PawpatchRecruit extends CardImpl {

    public PawpatchRecruit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}");
        
        this.subtype.add(SubType.RABBIT);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Offspring {2}
        this.addAbility(new OffspringAbility("{2}"));

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever a creature you control becomes the target of a spell or ability an opponent controls, put a +1/+1
        // counter on target creature you control other than that creature.
        TriggeredAbility ability = new PawpatchRecruitTriggeredAbility();
        this.addAbility(ability);
    }

    private PawpatchRecruit(final PawpatchRecruit card) {
        super(card);
    }

    @Override
    public PawpatchRecruit copy() {
        return new PawpatchRecruit(this);
    }
}

class PawpatchRecruitTriggeredAbility extends TriggeredAbilityImpl {

    public PawpatchRecruitTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersTargetEffect(CounterType.P1P1.createInstance()), false);
        this.setTargetAdjuster(DefineByTriggerTargetAdjuster.instance);
    }

    private PawpatchRecruitTriggeredAbility(final PawpatchRecruitTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public PawpatchRecruitTriggeredAbility copy() {
        return new PawpatchRecruitTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever a creature you control becomes the target of a spell or ability an opponent controls, put a" +
                " +1/+1 counter on target creature you control other than that creature.";
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TARGETED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(event.getTargetId());
        if (permanent == null || !StaticFilters.FILTER_CONTROLLED_A_CREATURE.match(permanent, getControllerId(), this, game)) {
            return false;
        }
        StackObject targetingObject = game.findTargetingStackObject(this.getId().toString(), event);
        if (targetingObject == null || !StaticFilters.FILTER_SPELL_OR_ABILITY_OPPONENTS.match(targetingObject, getControllerId(), this, game)) {
            return false;
        }
        this.getTargets().clear();
        FilterControlledPermanent filter = new FilterControlledCreaturePermanent();
        filter.add(Predicates.not(new MageObjectReferencePredicate(event.getTargetId(), game)));
        this.addTarget(new TargetPermanent(filter).withChooseHint("other than that creature, for +1/+1 counter"));
        return true;
    }
}
