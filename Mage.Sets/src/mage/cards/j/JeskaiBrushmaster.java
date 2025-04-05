package mage.cards.j;

import mage.MageInt;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.ProwessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class JeskaiBrushmaster extends CardImpl {

    public JeskaiBrushmaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{R}{W}");

        this.subtype.add(SubType.ORC);
        this.subtype.add(SubType.MONK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Double strike
        this.addAbility(DoubleStrikeAbility.getInstance());

        // Prowess
        this.addAbility(new ProwessAbility());
    }

    private JeskaiBrushmaster(final JeskaiBrushmaster card) {
        super(card);
    }

    @Override
    public JeskaiBrushmaster copy() {
        return new JeskaiBrushmaster(this);
    }
}
