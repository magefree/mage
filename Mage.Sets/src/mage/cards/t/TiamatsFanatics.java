package mage.cards.t;

import mage.MageInt;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.MyriadAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TiamatsFanatics extends CardImpl {

    public TiamatsFanatics(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");

        this.subtype.add(SubType.DRAGON);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Myriad
        this.addAbility(new MyriadAbility());
    }

    private TiamatsFanatics(final TiamatsFanatics card) {
        super(card);
    }

    @Override
    public TiamatsFanatics copy() {
        return new TiamatsFanatics(this);
    }
}
