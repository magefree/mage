
package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetLandPermanent;
import mage.watchers.common.PermanentsEnteredBattlefieldWatcher;

import java.util.List;
import java.util.UUID;

/**
 *
 * @author jeffwadsworth
 */
public final class LavaballTrap extends CardImpl {

    public LavaballTrap(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{6}{R}{R}");
        this.subtype.add(SubType.TRAP);

        // If an opponent had two or more lands enter the battlefield under their control this turn, you may pay {3}{R}{R} rather than pay Lavaball Trap's mana cost.
        this.addAbility(new AlternativeCostSourceAbility(new ManaCostsImpl<>("{3}{R}{R}"), LavaballTrapCondition.instance), new PermanentsEnteredBattlefieldWatcher());

        // Destroy two target lands. Lavaball Trap deals 4 damage to each creature.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addEffect(new DamageAllEffect(4, new FilterCreaturePermanent()));
        this.getSpellAbility().addTarget(new TargetLandPermanent(2, 2, StaticFilters.FILTER_LANDS, false));

    }

    private LavaballTrap(final LavaballTrap card) {
        super(card);
    }

    @Override
    public LavaballTrap copy() {
        return new LavaballTrap(this);
    }
}

enum LavaballTrapCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        PermanentsEnteredBattlefieldWatcher watcher = game.getState().getWatcher(PermanentsEnteredBattlefieldWatcher.class);
        if (watcher != null) {
            for (UUID opponentId : game.getOpponents(source.getControllerId())) {
                List<Permanent> permanents = watcher.getThisTurnEnteringPermanents(opponentId);
                if (permanents != null) {
                    int count = 0;
                    for (Permanent permanent : permanents) {
                        if (permanent.isLand(game)) {
                            count++;
                            if (count == 2) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "If an opponent had two or more lands enter the battlefield under their control this turn";
    }
}
