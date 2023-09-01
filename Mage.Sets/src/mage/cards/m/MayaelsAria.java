
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public final class MayaelsAria extends CardImpl {

    public MayaelsAria(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{R}{G}{W}");

        // At the beginning of your upkeep, put a +1/+1 counter on each creature you control if you control a creature with power 5 or greater.
        // Then you gain 10 life if you control a creature with power 10 or greater.
        // Then you win the game if you control a creature with power 20 or greater.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new MayaelsAriaEffect(), TargetController.YOU, false));
    }

    private MayaelsAria(final MayaelsAria card) {
        super(card);
    }

    @Override
    public MayaelsAria copy() {
        return new MayaelsAria(this);
    }
}

class MayaelsAriaEffect extends OneShotEffect {

    public MayaelsAriaEffect() {
        super(Outcome.Benefit);
        this.staticText = "put a +1/+1 counter on each creature you control if you control a creature with power 5 or greater. Then you gain 10 life if you control a creature with power 10 or greater. Then you win the game if you control a creature with power 20 or greater";
    }

    private MayaelsAriaEffect(final MayaelsAriaEffect effect) {
        super(effect);
    }

    @Override
    public MayaelsAriaEffect copy() {
        return new MayaelsAriaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        // put a +1/+1 counter on each creature you control if you control a creature with power 5 or greater.
        FilterCreaturePermanent filter = new FilterCreaturePermanent();
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN, 4));
        if (game.getState().getBattlefield().countAll(filter, controller.getId(), game) > 0) {
            for (Permanent creature : game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, source.getControllerId(), game)) {
                creature.addCounters(CounterType.P1P1.createInstance(), source.getControllerId(), source, game);
            }
        }
        game.getState().processAction(game); // needed because otehrwise the +1/+1 counters wouldn't be taken into account

        // Then you gain 10 life if you control a creature with power 10 or greater.
        filter = new FilterCreaturePermanent();
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN, 9));
        if (game.getState().getBattlefield().countAll(filter, controller.getId(), game) > 0) {
            controller.gainLife(10, game, source);
        }

        // Then you win the game if you control a creature with power 20 or greater.
        filter = new FilterCreaturePermanent();
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN, 19));
        if (game.getState().getBattlefield().countAll(filter, controller.getId(), game) > 0) {
            controller.won(game);
        }
        return true;
    }
}
