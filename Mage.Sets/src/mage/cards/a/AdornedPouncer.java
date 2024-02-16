
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.EternalizeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

public final class AdornedPouncer extends CardImpl {

    public AdornedPouncer(UUID ownerId, CardSetInfo cardSetInfo) {
        super(ownerId, cardSetInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        subtype.add(SubType.CAT);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Double strike
        addAbility(DoubleStrikeAbility.getInstance());

        // Eternalize 3WW
        addAbility(new EternalizeAbility(new ManaCostsImpl<>("{3}{W}{W}"), this));
    }

    private AdornedPouncer(final AdornedPouncer card) {
        super(card);
    }

    @Override
    public AdornedPouncer copy() {
        return new AdornedPouncer(this);
    }
}
