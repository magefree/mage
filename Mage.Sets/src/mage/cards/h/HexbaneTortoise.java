package mage.cards.h;

import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.EnlistAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HexbaneTortoise extends CardImpl {

    public HexbaneTortoise(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.TURTLE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Ward {2}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{2}")));

        // Enlist
        this.addAbility(new EnlistAbility());
    }

    private HexbaneTortoise(final HexbaneTortoise card) {
        super(card);
    }

    @Override
    public HexbaneTortoise copy() {
        return new HexbaneTortoise(this);
    }
}
