package mage.cards.m;

import mage.Mana;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.abilities.mana.ActivatedManaAbilityImpl;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author L_J
 */
public final class ManaCache extends CardImpl {

    private static final FilterPermanent filter = new FilterLandPermanent("untapped land that player controls");

    static {
        filter.add(TappedPredicate.UNTAPPED);
        filter.add(TargetController.ACTIVE.getControllerPredicate());
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter);

    public ManaCache(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}{R}");

        // At the beginning of each player's end step, put a charge counter on Mana Cache for each untapped land that player controls.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                TargetController.EACH_PLAYER,
                new AddCountersSourceEffect(CounterType.CHARGE.createInstance(), xValue),
                false
        ));

        // Remove a charge counter from Mana Cache: Add {C}. Any player may activate this ability but only during their turn before the end step.
        this.addAbility(new ManaCacheManaAbility());
    }

    private ManaCache(final ManaCache card) {
        super(card);
    }

    @Override
    public ManaCache copy() {
        return new ManaCache(this);
    }
}

class ManaCacheManaAbility extends ActivatedManaAbilityImpl {

    public ManaCacheManaAbility() {
        super(Zone.BATTLEFIELD, new BasicManaEffect(Mana.ColorlessMana(1), new CountersSourceCount(CounterType.CHARGE)),
                new RemoveCountersSourceCost(CounterType.CHARGE.createInstance(1)));
        this.netMana.add(new Mana(0, 0, 0, 0, 0, 0, 0, 1));
        this.setMayActivate(TargetController.ANY);
    }

    private ManaCacheManaAbility(final ManaCacheManaAbility ability) {
        super(ability);
    }

    @Override
    public ActivationStatus canActivate(UUID playerId, Game game) {
        // any player, but only during their turn before the end step
        Player player = game.getPlayer(playerId);
        if (player == null
                || !playerId.equals(game.getActivePlayerId())
                || !game.getTurnStepType().isBefore(PhaseStep.END_TURN)) {
            return ActivationStatus.getFalse();
        }

        return super.canActivate(playerId, game);
    }

    @Override
    public ManaCacheManaAbility copy() {
        return new ManaCacheManaAbility(this);
    }

    @Override
    public String getRule() {
        return super.getRule() + " Any player may activate this ability but only during their turn before the end step.";
    }
}
