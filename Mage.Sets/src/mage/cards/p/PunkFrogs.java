package mage.cards.p;

import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PunkFrogs extends CardImpl {

    public PunkFrogs(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G/U}{G/U}");

        this.subtype.add(SubType.FROG);
        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.REBEL);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Ward {3}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{3}")));
    }

    private PunkFrogs(final PunkFrogs card) {
        super(card);
    }

    @Override
    public PunkFrogs copy() {
        return new PunkFrogs(this);
    }
}
