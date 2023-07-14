package mage.cards.k;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 *
 * @author Susucr
 */
public final class KingOfTheOathbreakers extends CardImpl {

    public KingOfTheOathbreakers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{B}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever King of the Oathbreakers or another Spirit you control becomes the target of a spell, it phases out.
        // Whenever King of the Oathbreakers or another Spirit you control phases in, create a tapped 1/1 white Spirit creature token with flying.
    }

    private KingOfTheOathbreakers(final KingOfTheOathbreakers card) {
        super(card);
    }

    @Override
    public KingOfTheOathbreakers copy() {
        return new KingOfTheOathbreakers(this);
    }
}
