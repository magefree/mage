
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterCreatureCard;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author jeffwadsworth
 */
public final class LotlethTroll extends CardImpl {

    private static final FilterCreatureCard filter = new FilterCreatureCard("creature card in your hand");

    public LotlethTroll(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B}{G}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.TROLL);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Discard a creature card: Put a +1/+1 counter on Lotleth Troll.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P1.createInstance()), new DiscardTargetCost(new TargetCardInHand(filter))));

        // {B}: Regenerate Lotleth Troll.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateSourceEffect(), new ManaCostsImpl<>("{B}")));
    }

    private LotlethTroll(final LotlethTroll card) {
        super(card);
    }

    @Override
    public LotlethTroll copy() {
        return new LotlethTroll(this);
    }
}
