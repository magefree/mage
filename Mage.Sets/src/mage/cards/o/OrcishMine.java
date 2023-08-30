package mage.cards.o;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.BecomesTappedAttachedTriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DamageAttachedControllerEffect;
import mage.abilities.effects.common.DestroyAttachedToEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.RemoveCounterSourceEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.meta.OrTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetLandPermanent;

import java.util.UUID;

/**
 *
 * @author noahg
 */
public final class OrcishMine extends CardImpl {

    public OrcishMine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}{R}");
        
        this.subtype.add(SubType.AURA);

        // Enchant land
        TargetPermanent auraTarget = new TargetLandPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.DestroyPermanent));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Orcish Mine enters the battlefield with three ore counters on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.ORE.createInstance(3)), "with three ore counters on it"));

        // At the beginning of your upkeep or whenever enchanted land becomes tapped, remove an ore counter from Orcish Mine.
        this.addAbility(new OrTriggeredAbility(Zone.BATTLEFIELD, new RemoveCounterSourceEffect(CounterType.ORE.createInstance()),
                new BeginningOfUpkeepTriggeredAbility(null, TargetController.YOU, false),
                new BecomesTappedAttachedTriggeredAbility(null, "enchanted land")));

        // When the last ore counter is removed from Orcish Mine, destroy enchanted land and Orcish Mine deals 2 damage to that land's controller.
        this.addAbility(new OrcishMineAbility());
    }

    private OrcishMine(final OrcishMine card) {
        super(card);
    }

    @Override
    public OrcishMine copy() {
        return new OrcishMine(this);
    }
}

class OrcishMineAbility extends TriggeredAbilityImpl {

    public OrcishMineAbility() {
        super(Zone.BATTLEFIELD, new DestroyAttachedToEffect("enchanted land"));
        this.addEffect(new DamageAttachedControllerEffect(2));
    }

    public OrcishMineAbility(final OrcishMineAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COUNTER_REMOVED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getData().equals(CounterType.ORE.getName()) && event.getTargetId().equals(this.getSourceId())) {
            Permanent p = game.getPermanent(this.getSourceId());
            if (p != null) {
                return p.getCounters(game).getCount(CounterType.ORE) == 0;
            }
        }
        return false;
    }

    @Override
    public OrcishMineAbility copy() {
        return new OrcishMineAbility(this);
    }

    @Override
    public String getRule() {
        return "When the last ore counter is removed from {this}, destroy enchanted land and {this} deals 2 damage to that land's controller.";
    }
}
