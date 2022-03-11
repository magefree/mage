package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.EscalateAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.FilterPlayer;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterEnchantmentPermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetEnchantmentPermanent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class CollectiveEffort extends CardImpl {

    private static final FilterControlledCreaturePermanent filterUntapped = new FilterControlledCreaturePermanent("untapped creature you control");
    private static final FilterCreaturePermanent filterDestroyCreature = new FilterCreaturePermanent("creature with power 4 or greater");
    private static final FilterEnchantmentPermanent filterDestroyEnchantment = new FilterEnchantmentPermanent("enchantment to destroy");
    private static final FilterPlayer filterPlayer = new FilterPlayer("player whose creatures get +1/+1 counters");

    static {
        filterUntapped.add(TappedPredicate.UNTAPPED);
        filterDestroyCreature.add(new PowerPredicate(ComparisonType.MORE_THAN, 3));
    }

    public CollectiveEffort(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{W}{W}");

        // Escalate &mdash; Tap an untapped creature you control.
        Cost cost = new TapTargetCost(new TargetControlledCreaturePermanent(filterUntapped));
        cost.setText("&mdash; Tap an untapped creature you control");
        this.addAbility(new EscalateAbility(cost));

        // Choose one or more &mdash;
        this.getSpellAbility().getModes().setMinModes(1);
        this.getSpellAbility().getModes().setMaxModes(3);

        // Destroy target creature with power 4 or greater.;
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filterDestroyCreature).withChooseHint("destroy"));

        // Destroy target enchantment.;
        Effect effect = new DestroyTargetEffect();
        effect.setText("Destroy target enchantment");
        Mode mode = new Mode(effect);
        mode.addTarget(new TargetEnchantmentPermanent(filterDestroyEnchantment).withChooseHint("destroy"));
        this.getSpellAbility().addMode(mode);

        // Put a +1/+1 counter on each creature target player controls.
        effect = new CollectiveEffortEffect();
        effect.setText("Put a +1/+1 counter on each creature target player controls");
        mode = new Mode(effect);
        mode.addTarget(new TargetPlayer(1, 1, false, filterPlayer).withChooseHint("put +1/+1 counter on each creature"));
        this.getSpellAbility().addMode(mode);
    }

    private CollectiveEffort(final CollectiveEffort card) {
        super(card);
    }

    @Override
    public CollectiveEffort copy() {
        return new CollectiveEffort(this);
    }
}

class CollectiveEffortEffect extends OneShotEffect {

    CollectiveEffortEffect() {
        super(Outcome.UnboostCreature);
        staticText = "Put a +1/+1 counter on each creature target player controls";
    }

    CollectiveEffortEffect(final CollectiveEffortEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player target = game.getPlayer(source.getFirstTarget());
        if (target != null) {
            for (Permanent p : game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, target.getId(), game)) {
                p.addCounters(CounterType.P1P1.createInstance(), source.getControllerId(), source, game);
            }
            return true;
        }
        return false;
    }

    @Override
    public CollectiveEffortEffect copy() {
        return new CollectiveEffortEffect(this);
    }

}
