package mage.cards.b;

import mage.MageInt;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Banehound extends CardImpl {

    public Banehound(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");

        this.subtype.add(SubType.NIGHTMARE);
        this.subtype.add(SubType.DOG);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());
    }

    private Banehound(final Banehound card) {
        super(card);
    }

    @Override
    public Banehound copy() {
        return new Banehound(this);
    }
}
