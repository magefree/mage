
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author L_J
 */
public final class Necropolis extends CardImpl {

    public Necropolis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{5}");
        this.subtype.add(SubType.WALL);
        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // Exile a creature card from your graveyard: Put X +0/+1 counters on Necropolis, where X is the exiled card's converted mana cost.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, 
                new AddCountersSourceEffect(CounterType.P0P1.createInstance(0), new NecropolisValue(), true).setText("Put X +0/+1 counters on {this}, where X is the exiled card's mana value"),
                new ExileFromGraveCost(new TargetCardInYourGraveyard(new FilterCreatureCard("a creature card")))));
    }

    private Necropolis(final Necropolis card) {
        super(card);
    }

    @Override
    public Necropolis copy() {
        return new Necropolis(this);
    }
}

class NecropolisValue implements DynamicValue {

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        for (Cost cost : sourceAbility.getCosts()) {
            if (cost.isPaid() && cost instanceof ExileFromGraveCost) {
                int xValue = 0;
                for (Card card : ((ExileFromGraveCost) cost).getExiledCards()) {
                    xValue += card.getManaValue();
                }
                return xValue;
            }
        }
        return 0;
    }

    @Override
    public NecropolisValue copy() {
        return new NecropolisValue();
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "";
    }
}
