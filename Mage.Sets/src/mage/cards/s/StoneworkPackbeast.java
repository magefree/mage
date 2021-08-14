package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.HasSubtypesSourceEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StoneworkPackbeast extends CardImpl {

    public StoneworkPackbeast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}");

        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Stonework Packbeast is also a Cleric, Rogue, Warrior, and Wizard.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new HasSubtypesSourceEffect(
                SubType.CLERIC, SubType.ROGUE, SubType.WARRIOR, SubType.WIZARD
        )));

        // {2}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility(new GenericManaCost(2)));
    }

    private StoneworkPackbeast(final StoneworkPackbeast card) {
        super(card);
    }

    @Override
    public StoneworkPackbeast copy() {
        return new StoneworkPackbeast(this);
    }
}
