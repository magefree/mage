package mage.cards.t;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.MyriadAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TabaxiToucaneers extends CardImpl {

    public TabaxiToucaneers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}");

        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.RANGER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Myriad
        this.addAbility(new MyriadAbility());
    }

    private TabaxiToucaneers(final TabaxiToucaneers card) {
        super(card);
    }

    @Override
    public TabaxiToucaneers copy() {
        return new TabaxiToucaneers(this);
    }
}
