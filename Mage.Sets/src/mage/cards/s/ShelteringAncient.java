package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.keyword.CumulativeUpkeepAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
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

    private ShelteringAncient(final ShelteringAncient card) {
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
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    ShelteringAncientCost() {
        this.text = "Put a +1/+1 counter on a creature an opponent controls";
    }

    private ShelteringAncientCost(final ShelteringAncientCost cost) {
        super(cost);
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        Player controller = game.getPlayer(controllerId);
        if (controller != null) {
            Target target = new TargetCreaturePermanent(1, 1, filter, true);
            if (target.choose(Outcome.BoostCreature, controllerId, source.getSourceId(), source, game)) {
                Permanent permanent = game.getPermanent(target.getFirstTarget());
                if (permanent != null) {
                    permanent.addCounters(CounterType.P1P1.createInstance(), controllerId, ability, game);
                    this.paid = true;
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        return game.getBattlefield().contains(filter, source, game, 1);
    }

    @Override
    public ShelteringAncientCost copy() {
        return new ShelteringAncientCost(this);
    }
}
