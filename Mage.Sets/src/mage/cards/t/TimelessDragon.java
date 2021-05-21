package mage.cards.t;

import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.EternalizeAbility;
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
public final class TimelessDragon extends CardImpl {

    public TimelessDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");

        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Plainscycling {2}
        this.addAbility(new PlainscyclingAbility(new ManaCostsImpl<>("{2}")));

        // Eternalize {2}{W}{W}
        this.addAbility(new EternalizeAbility(new ManaCostsImpl<>("{2}{W}{W}"), this));
    }

    private TimelessDragon(final TimelessDragon card) {
        super(card);
    }

    @Override
    public TimelessDragon copy() {
        return new TimelessDragon(this);
    }
}
