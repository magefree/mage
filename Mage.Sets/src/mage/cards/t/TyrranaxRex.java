package mage.cards.t;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.common.CantBeCounteredSourceAbility;
import mage.constants.SubType;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.WardAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.ToxicAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 * @author TheElk801
 */
public final class TyrranaxRex extends CardImpl {

    public TyrranaxRex(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{G}{G}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(8);
        this.toughness = new MageInt(8);

        // This spell can't be countered.
        this.addAbility(new CantBeCounteredSourceAbility());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Ward {4}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{4}"), false));

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Toxic 4
        this.addAbility(new ToxicAbility(4));
    }

    private TyrranaxRex(final TyrranaxRex card) {
        super(card);
    }

    @Override
    public TyrranaxRex copy() {
        return new TyrranaxRex(this);
    }
}
