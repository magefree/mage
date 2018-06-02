
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.keyword.CumulativeUpkeepAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author emerald000
 */
public final class ShelteringAncient extends CardImpl {

    public ShelteringAncient(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");
        this.subtype.add(SubType.TREEFOLK);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Cumulative upkeep-Put a +1/+1 counter on a creature an opponent controls.
        this.addAbility(new CumulativeUpkeepAbility(new ShelteringAncientCost()));
    }

    public ShelteringAncient(final ShelteringAncient card) {
        super(card);
    }

    @Override
    public ShelteringAncient copy() {
        return new ShelteringAncient(this);
    }
}

class ShelteringAncientCost extends CostImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(new ControllerPredicate(TargetController.OPPONENT));
    }

    ShelteringAncientCost() {
        this.text = "Put a +1/+1 counter on a creature an opponent controls";
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana, Cost costToPay) {
        Player controller = game.getPlayer(controllerId);
        if (controller != null) {
            Target target = new TargetCreaturePermanent(1, 1, filter, true);
            if (target.choose(Outcome.BoostCreature, controllerId, sourceId, game)) {
                Permanent permanent = game.getPermanent(target.getFirstTarget());
                if (permanent != null) {
                    permanent.addCounters(CounterType.P1P1.createInstance(), ability, game);
                    this.paid = true;
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean canPay(Ability ability, UUID sourceId, UUID controllerId, Game game) {
        return game.getBattlefield().contains(filter, sourceId, game, 1);
    }

    @Override
    public ShelteringAncientCost copy() {
        return new ShelteringAncientCost();
    }
}
