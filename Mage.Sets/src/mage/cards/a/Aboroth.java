
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.keyword.CumulativeUpkeepAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author Styxo
 */
public final class Aboroth extends CardImpl {

    public Aboroth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{G}");

        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(9);
        this.toughness = new MageInt(9);

        // Cumulative upkeep-Put a -1/-1 counter on Aboroth.
        this.addAbility(new CumulativeUpkeepAbility(new AborothCost()));
    }

    private Aboroth(final Aboroth card) {
        super(card);
    }

    @Override
    public Aboroth copy() {
        return new Aboroth(this);
    }
}

class AborothCost extends CostImpl {

    public AborothCost() {
        this.text = "Put a -1/-1 counter on Aboroth";
    }

    private AborothCost(final AborothCost cost) {
        super(cost);
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            permanent.addCounters(CounterType.M1M1.createInstance(), controllerId, ability, game);
            this.paid = true;
            return true;
        }
        return false;
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        return true;
    }

    @Override
    public AborothCost copy() {
        return new AborothCost(this);
    }
}
