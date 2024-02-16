package mage.cards.s;

import java.util.*;

import mage.MageObjectReference;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.counter.AddCountersAttachedEffect;
import mage.abilities.effects.common.counter.RemoveCountersAttachedEffect;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.abilities.Ability;
import mage.abilities.effects.common.AttachEffect;
import mage.target.TargetPermanent;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.watchers.Watcher;

/**
 *
 * @author noahg
 */
public final class ShapeOfTheWiitigo extends CardImpl {

    public ShapeOfTheWiitigo(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{G}{G}{G}");
        
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // When Shape of the Wiitigo enters the battlefield, put six +1/+1 counters on enchanted creature.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new AddCountersAttachedEffect(CounterType.P1P1.createInstance(6), "enchanted creature")));

        // At the beginning of your upkeep, put a +1/+1 counter on enchanted creature if it attacked or blocked since your last upkeep. Otherwise, remove a +1/+1 counter from it.
        Ability triggeredAbility = new BeginningOfUpkeepTriggeredAbility(
                new ConditionalOneShotEffect(new AddCountersAttachedEffect(CounterType.P1P1.createInstance(1), "enchanted creature"),
                        new RemoveCountersAttachedEffect(CounterType.P1P1.createInstance(1), "it"),
                        new AttachedAttackedOrBlockedSinceYourLastUpkeepCondition(),
                        "put a +1/+1 counter on enchanted creature if it attacked or blocked since your last " +
                                "upkeep. Otherwise, remove a +1/+1 counter from it"), TargetController.YOU, false);
        triggeredAbility.addWatcher(new AttackedOrBlockedSinceYourLastUpkeepWatcher());
        this.addAbility(triggeredAbility);
    }

    private ShapeOfTheWiitigo(final ShapeOfTheWiitigo card) {
        super(card);
    }

    @Override
    public ShapeOfTheWiitigo copy() {
        return new ShapeOfTheWiitigo(this);
    }
}

class AttachedAttackedOrBlockedSinceYourLastUpkeepCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getBattlefield().getPermanent(source.getSourceId());
        AttackedOrBlockedSinceYourLastUpkeepWatcher watcher = game.getState().getWatcher(AttackedOrBlockedSinceYourLastUpkeepWatcher.class);
        if (permanent != null && permanent.getAttachedTo() != null && watcher != null) {
            Permanent attachedTo = game.getBattlefield().getPermanent(permanent.getAttachedTo());
            if (attachedTo == null) {
                attachedTo = (Permanent) game.getLastKnownInformation(permanent.getAttachedTo(), Zone.BATTLEFIELD);
            }
            if (attachedTo != null) {
                return watcher.attackedSinceLastUpkeep(new MageObjectReference(attachedTo.getId(), attachedTo.getZoneChangeCounter(game), game), source.getControllerId());
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "it attacked or blocked since your last upkeep";
    }
}

class AttackedOrBlockedSinceYourLastUpkeepWatcher extends Watcher{

    //Map of each player to the creatures that attacked or blocked since their last upkeep
    private final Map<UUID, Set<MageObjectReference>> attackedOrBlockedCreatures = new HashMap<>();

    public AttackedOrBlockedSinceYourLastUpkeepWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.UPKEEP_STEP_POST){
            //Clear
            attackedOrBlockedCreatures.put(event.getPlayerId(), new HashSet<>());
        } else if (event.getType() == GameEvent.EventType.ATTACKER_DECLARED || event.getType() == GameEvent.EventType.BLOCKER_DECLARED) {
            MageObjectReference mor = new MageObjectReference(event.getSourceId(), game);
            for (UUID player : game.getPlayerList()){
                if (!attackedOrBlockedCreatures.containsKey(player)) {
                    attackedOrBlockedCreatures.put(player, new HashSet<>());
                }
                attackedOrBlockedCreatures.get(player).add(mor);
            }
        }
    }

    public boolean attackedSinceLastUpkeep(MageObjectReference mor, UUID upkeepPlayer){
        return attackedOrBlockedCreatures.get(upkeepPlayer).contains(mor);
    }
}
