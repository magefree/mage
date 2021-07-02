package mage.cards.r;

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
public final class RimeshieldFrostGiant extends CardImpl {

    public RimeshieldFrostGiant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");

        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Ward {3}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{3}")));
    }

    private RimeshieldFrostGiant(final RimeshieldFrostGiant card) {
        super(card);
    }

    @Override
    public RimeshieldFrostGiant copy() {
        return new RimeshieldFrostGiant(this);
    }
}
