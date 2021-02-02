package mage.cards.s;

import mage.MageInt;
import mage.abilities.keyword.DauntAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SteelLeafChampion extends CardImpl {

    public SteelLeafChampion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{G}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Steel Leaf Champion can't be blocked by creatures with power 2 or less.
        this.addAbility(new DauntAbility());
    }

    private SteelLeafChampion(final SteelLeafChampion card) {
        super(card);
    }

    @Override
    public SteelLeafChampion copy() {
        return new SteelLeafChampion(this);
    }
}
