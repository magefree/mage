package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.DisguiseAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author Grath
 */
public final class AvelineDeGrandpre extends CardImpl {

    public AvelineDeGrandpre(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ASSASSIN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Whenever a creature you control with deathtouch deals combat damage to a player, put that many +1/+1 counters on that creature.
        this.addAbility(new AvelineDeGrandpreTriggeredAbility());

        // Disguise {B}{G}
        this.addAbility(new DisguiseAbility(this, new ManaCostsImpl<>("{B}{G}")));

    }

    private AvelineDeGrandpre(final AvelineDeGrandpre card) {
        super(card);
    }

    @Override
    public AvelineDeGrandpre copy() {
        return new AvelineDeGrandpre(this);
    }
}

class AvelineDeGrandpreTriggeredAbility extends TriggeredAbilityImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("a creature you control with deathtouch");

    static {
        filter.add(new AbilityPredicate(DeathtouchAbility.class));
    }

    public AvelineDeGrandpreTriggeredAbility() {
        // Copied from Necropolis Regent, I don't know why QUEST counters.
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.QUEST.createInstance()), false);
    }

    private AvelineDeGrandpreTriggeredAbility(final AvelineDeGrandpreTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public AvelineDeGrandpreTriggeredAbility copy() {
        return new AvelineDeGrandpreTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (((DamagedPlayerEvent) event).isCombatDamage()) {
            Permanent creature = game.getPermanent(event.getSourceId());
            if (creature != null && creature.isControlledBy(controllerId) && filter.match(creature, game)) {
                this.getEffects().clear();
                Effect effect = new AddCountersTargetEffect(CounterType.P1P1.createInstance(event.getAmount()));
                effect.setTargetPointer(new FixedTarget(creature.getId(), game));
                this.addEffect(effect);
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature you control with deathtouch deals combat damage to a player, put that many +1/+1 counters on it.";
    }
}