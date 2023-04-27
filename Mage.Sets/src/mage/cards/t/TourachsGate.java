
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.AttachedToMatchesFilterCondition;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.TapAttachedCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author L_J
 */
public final class TourachsGate extends CardImpl {

    private static final FilterControlledPermanent filterLand = new FilterControlledPermanent("land you control");
    static {
        filterLand.add(CardType.LAND.getPredicate());
    }

    private static final FilterPermanent filterUntapped = new FilterPermanent("enchanted land is untapped");
    static {
        filterUntapped.add(TappedPredicate.UNTAPPED);
    }

    private static final FilterCreaturePermanent filterAttackingCreatures = new FilterCreaturePermanent("attacking creatures you control");
    static {
        filterAttackingCreatures.add(AttackingPredicate.instance);
        filterAttackingCreatures.add(TargetController.YOU.getControllerPredicate());
    }

    private static final FilterControlledCreaturePermanent filterThrull = new FilterControlledCreaturePermanent("a Thrull");
    static {
        filterThrull.add(SubType.THRULL.getPredicate());
    }

    public TourachsGate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}{B}");
        this.subtype.add(SubType.AURA);

        // Enchant land you control
        TargetPermanent auraTarget = new TargetControlledPermanent(filterLand);
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Sacrifice a Thrull: Put three time counters on Tourach's Gate.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.TIME.createInstance(3)), 
                new SacrificeTargetCost(new TargetControlledCreaturePermanent(1,1, filterThrull, true))));
        
        // At the beginning of your upkeep, remove a time counter from Tourach's Gate. If there are no time counters on Tourach's Gate, sacrifice it.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new TourachsGateUpkeepEffect(), TargetController.YOU, false));

        // Tap enchanted land: Attacking creatures you control get +2/-1 until end of turn. Activate this ability only if enchanted land is untapped.
        Cost cost = new TapAttachedCost();
        cost.setText("Tap enchanted land");
        this.addAbility(new ActivateIfConditionActivatedAbility(Zone.BATTLEFIELD, new BoostAllEffect(2, -1, Duration.EndOfTurn, filterAttackingCreatures, false), 
                cost, new AttachedToMatchesFilterCondition(filterUntapped)));
    }

    private TourachsGate(final TourachsGate card) {
        super(card);
    }

    @Override
    public TourachsGate copy() {
        return new TourachsGate(this);
    }
}

class TourachsGateUpkeepEffect extends OneShotEffect {

    TourachsGateUpkeepEffect() {
        super(Outcome.Sacrifice);
        staticText = "remove a time counter from {this}. If there are no time counters on {this}, sacrifice it";
    }

    TourachsGateUpkeepEffect(final TourachsGateUpkeepEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            int amount = permanent.getCounters(game).getCount(CounterType.TIME);
            if (amount > 0) {
                permanent.removeCounters(CounterType.TIME.createInstance(), source, game);
            }
            // is supposed to function similar to Vanishing
            amount = permanent.getCounters(game).getCount(CounterType.TIME);
            if (amount == 0) {
                permanent.sacrifice(source, game);
            }
            return true;
        }
        return false;
    }

    @Override
    public TourachsGateUpkeepEffect copy() {
        return new TourachsGateUpkeepEffect(this);
    }
}
