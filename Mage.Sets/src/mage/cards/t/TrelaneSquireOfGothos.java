package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.keyword.CantBeBlockedSourceAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class TrelaneSquireOfGothos extends CardImpl {

    public TrelaneSquireOfGothos(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.Q);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Ward {2}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{2}")));

        // Trelane can't be blocked.
        this.addAbility(new CantBeBlockedSourceAbility());
    }

    private TrelaneSquireOfGothos(final TrelaneSquireOfGothos card) {
        super(card);
    }

    @Override
    public TrelaneSquireOfGothos copy() {
        return new TrelaneSquireOfGothos(this);
    }
}
