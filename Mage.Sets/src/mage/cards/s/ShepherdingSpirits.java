package mage.cards.s;

import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.PlainscyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ShepherdingSpirits extends CardImpl {

    public ShepherdingSpirits(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}{W}");

        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Plainscycling {2}
        this.addAbility(new PlainscyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    private ShepherdingSpirits(final ShepherdingSpirits card) {
        super(card);
    }

    @Override
    public ShepherdingSpirits copy() {
        return new ShepherdingSpirits(this);
    }
}
