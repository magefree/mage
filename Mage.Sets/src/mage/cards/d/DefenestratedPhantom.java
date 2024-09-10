package mage.cards.d;

import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.DisguiseAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DefenestratedPhantom extends CardImpl {

    public DefenestratedPhantom(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}{W}");

        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Disguise {4}{W}
        this.addAbility(new DisguiseAbility(this, new ManaCostsImpl<>("{4}{W}")));
    }

    private DefenestratedPhantom(final DefenestratedPhantom card) {
        super(card);
    }

    @Override
    public DefenestratedPhantom copy() {
        return new DefenestratedPhantom(this);
    }
}
