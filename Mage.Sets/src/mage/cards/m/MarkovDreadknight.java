
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;

/**
 *
 * @author fireshoes
 */
public final class MarkovDreadknight extends CardImpl {

    public MarkovDreadknight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}{B}");
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {2}{B}, Discard a card: Put two +1/+1 counters on Markhov Dreadknight.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P1.createInstance(2)), new ManaCostsImpl<>("{2}{B}"));
        ability.addCost(new DiscardCardCost());
        this.addAbility(ability);
    }

    private MarkovDreadknight(final MarkovDreadknight card) {
        super(card);
    }

    @Override
    public MarkovDreadknight copy() {
        return new MarkovDreadknight(this);
    }
}
