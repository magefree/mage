package mage.cards.a;

import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.EternalizeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

public class AdornedPouncer extends CardImpl {

    public AdornedPouncer(UUID ownerId, CardSetInfo cardSetInfo) {
        super(ownerId, cardSetInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        subtype.add("Cat");

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        //double strike
        addAbility(DoubleStrikeAbility.getInstance());

        //eternalize 3WW
        addAbility(new EternalizeAbility(new ManaCostsImpl("{3}{W}{W}"), this));
    }

    public AdornedPouncer(AdornedPouncer adornedPouncer) {
        super(adornedPouncer);
    }

    public AdornedPouncer copy() {
        return new AdornedPouncer(this);
    }
}
