
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.CumulativeUpkeepAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

/**
 *
 * @author TheElk801
 */
public final class VexingSphinx extends CardImpl {

    public VexingSphinx(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{U}");

        this.subtype.add(SubType.SPHINX);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Cumulative upkeep-Discard a card.
        this.addAbility(new CumulativeUpkeepAbility(new DiscardCardCost()));

        // When Vexing Sphinx dies, draw a card for each age counter on it.
        this.addAbility(new DiesSourceTriggeredAbility(new DrawCardSourceControllerEffect(new CountersSourceCount(CounterType.AGE))));

    }

    private VexingSphinx(final VexingSphinx card) {
        super(card);
    }

    @Override
    public VexingSphinx copy() {
        return new VexingSphinx(this);
    }
}
