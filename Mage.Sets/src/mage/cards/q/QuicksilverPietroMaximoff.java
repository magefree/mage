package mage.cards.q;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class QuicksilverPietroMaximoff extends CardImpl {

    public QuicksilverPietroMaximoff(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Haste
        this.addAbility(HasteAbility.getInstance());
    }

    private QuicksilverPietroMaximoff(final QuicksilverPietroMaximoff card) {
        super(card);
    }

    @Override
    public QuicksilverPietroMaximoff copy() {
        return new QuicksilverPietroMaximoff(this);
    }
}
