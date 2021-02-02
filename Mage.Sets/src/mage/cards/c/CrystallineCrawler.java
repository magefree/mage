package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.common.ColorsOfManaSpentToCastCount;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.mana.AnyColorManaAbility;
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
public final class CrystallineCrawler extends CardImpl {

    public CrystallineCrawler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}");

        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Converge - Crystalline Crawler enters the battlefield with a +1/+1 counter on it for each color of mana spent to cast it.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(), ColorsOfManaSpentToCastCount.getInstance(), true),
                null, "<i>Converge</i> &mdash; {this} enters the battlefield with a +1/+1 counter on it for each color of mana spent to cast it.", null));

        // Remove a +1/+1 counter from Crystalline Crawler: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility(new RemoveCountersSourceCost(CounterType.P1P1.createInstance(1)),
                new CountersSourceCount(CounterType.P1P1), false));

        // {T}: Put a +1/+1 counter on Crystalline Crawler.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P1.createInstance(1)), new TapSourceCost()));
    }

    private CrystallineCrawler(final CrystallineCrawler card) {
        super(card);
    }

    @Override
    public CrystallineCrawler copy() {
        return new CrystallineCrawler(this);
    }
}
