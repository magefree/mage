package mage.cards.d;

import mage.MageInt;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.keyword.ProwessAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DwarvenForgeChanter extends CardImpl {

    public DwarvenForgeChanter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Ward--Pay 2 life.
        this.addAbility(new WardAbility(new PayLifeCost(2)));

        // Prowess
        this.addAbility(new ProwessAbility());
    }

    private DwarvenForgeChanter(final DwarvenForgeChanter card) {
        super(card);
    }

    @Override
    public DwarvenForgeChanter copy() {
        return new DwarvenForgeChanter(this);
    }
}
