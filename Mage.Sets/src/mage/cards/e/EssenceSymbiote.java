package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.keyword.MutateAbility;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.abilities.Ability;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author gp66
 */
public final class EssenceSymbiote extends CardImpl {



    public EssenceSymbiote(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever a creature you control mutates, put a +1/+1 counter on that creature and you gain 2 life.
        Ability ability = new EssenceSymbioteTriggeredAbility(Zone.BATTLEFIELD,
                new AddCountersTargetEffect(CounterType.P1P1.createInstance()).setText("put a +1/+1 counter on that creature"));
        // You gain 2 life when Essence Symbiote’s ability resolves, even if you can’t put a +1/+1 counter on the mutated creature
        ability.addEffect(new GainLifeEffect(2).concatBy("and"));
        this.addAbility(ability);
    }

    private EssenceSymbiote(final EssenceSymbiote card) {
        super(card);
    }

    @Override
    public EssenceSymbiote copy() {
        return new EssenceSymbiote(this);
    }
}

class EssenceSymbioteTriggeredAbility extends TriggeredAbilityImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("creature you control mutates");

    static {
        filter.add(new AbilityPredicate(MutateAbility.class));
    }

    public EssenceSymbioteTriggeredAbility(Zone zone, Effect effect) {
        super(zone, effect, false);
        setTriggerPhrase("Whenever a creature you control mutates, ");
    }

    public EssenceSymbioteTriggeredAbility(final mage.cards.e.EssenceSymbioteTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public mage.cards.e.EssenceSymbioteTriggeredAbility copy() {
        return new mage.cards.e.EssenceSymbioteTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        // TODO: Implement this
        //return event.getType() == GameEvent.EventType.CREATURE_MUTATED;
        return false;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        // TODO: Implement this
        /*
        Permanent sourcePermanent = game.getPermanent(event.getSourceId());
        Permanent targetPermanent = game.getPermanent(event.getTargetId());
        if (sourcePermanent != null && targetPermanent != null) {
            Player controller = game.getPlayer(targetPermanent.getControllerId());
            if (controller != null
                    && event.getTargetId().equals(targetPermanent.getId())
                    && controller.getId().equals(sourcePermanent.getControllerId())
                    && this.isControlledBy(controller.getId())) {
                for (Effect effect : this.getEffects()) {
                    effect.setValue("targetId", targetPermanent.getId());
                    effect.setTargetPointer(new FixedTarget(targetPermanent.getId(), targetPermanent.getZoneChangeCounter(game)));
                }
                return true;
            }
        }
        */
        return false;
    }
}