package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class AntManScottLang extends CardImpl {

    public AntManScottLang(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {4}: Put a +1/+1 counter on Ant-Man.
        this.addAbility(new SimpleActivatedAbility(
            new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
            new GenericManaCost(4)
        ));
    }

    private AntManScottLang(final AntManScottLang card) {
        super(card);
    }

    @Override
    public AntManScottLang copy() {
        return new AntManScottLang(this);
    }
}
