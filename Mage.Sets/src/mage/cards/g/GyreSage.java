package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.keyword.EvolveAbility;
import mage.abilities.mana.DynamicManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

/**
 *
 * @author Plopman
 */
public final class GyreSage extends CardImpl {

    public GyreSage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Evolve (Whenever a creature enters the battlefield under your control, if that creature
        // has greater power or toughness than this creature, put a +1/+1 counter on this creature.)
        this.addAbility(new EvolveAbility());

        //{T} : Add {G} for each +1/+1 counter on Gyre Sage.
        this.addAbility(new DynamicManaAbility(Mana.GreenMana(1), new CountersSourceCount(CounterType.P1P1)));
    }

    private GyreSage(final GyreSage card) {
        super(card);
    }

    @Override
    public GyreSage copy() {
        return new GyreSage(this);
    }
}
