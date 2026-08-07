package mage.cards.x;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.mana.AnyColorManaAbility;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class Xenobotanist extends CardImpl {

    public Xenobotanist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.VULCAN);
        this.subtype.add(SubType.SCIENTIST);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // {T}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());
    }

    private Xenobotanist(final Xenobotanist card) {
        super(card);
    }

    @Override
    public Xenobotanist copy() {
        return new Xenobotanist(this);
    }
}
