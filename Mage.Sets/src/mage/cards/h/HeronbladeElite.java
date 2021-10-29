package mage.cards.h;

import mage.MageInt;
import mage.Mana;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerCount;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.mana.DynamicManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;

import java.util.UUID;
import mage.filter.predicate.mageobject.AnotherPredicate;

/**
 * @author TheElk801
 */
public final class HeronbladeElite extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent(SubType.HUMAN, "another Human");

    static {
        filter.add(AnotherPredicate.instance);
    }

    private static final DynamicValue xValue = new SourcePermanentPowerCount();

    public HeronbladeElite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Whenever another Human enters the battlefield under your control, put a +1/+1 counter on Heronblade Elite.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), filter
        ));

        // {T}: Add X mana of any one color, where X is Heronblade Elite's power.
        this.addAbility(new DynamicManaAbility(
                new Mana(0, 0, 0, 0, 0, 0, 1, 0), xValue, new TapSourceCost(), "Add X mana "
                + "of any one color, where X is {this}'s power", true
        ));
    }

    private HeronbladeElite(final HeronbladeElite card) {
        super(card);
    }

    @Override
    public HeronbladeElite copy() {
        return new HeronbladeElite(this);
    }
}
