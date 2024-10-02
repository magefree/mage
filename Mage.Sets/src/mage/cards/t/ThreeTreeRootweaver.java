package mage.cards.t;

import mage.MageInt;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ThreeTreeRootweaver extends CardImpl {

    public ThreeTreeRootweaver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.MOLE);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // {T}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());
    }

    private ThreeTreeRootweaver(final ThreeTreeRootweaver card) {
        super(card);
    }

    @Override
    public ThreeTreeRootweaver copy() {
        return new ThreeTreeRootweaver(this);
    }
}
