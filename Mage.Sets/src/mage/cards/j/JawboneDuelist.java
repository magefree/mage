package mage.cards.j;

import java.util.UUID;

import mage.MageInt;
import mage.constants.SubType;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.ToxicAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 * @author TheElk801
 */
public final class JawboneDuelist extends CardImpl {

    public JawboneDuelist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Double strike
        this.addAbility(DoubleStrikeAbility.getInstance());

        // Toxic 1
        this.addAbility(new ToxicAbility(1));
    }

    private JawboneDuelist(final JawboneDuelist card) {
        super(card);
    }

    @Override
    public JawboneDuelist copy() {
        return new JawboneDuelist(this);
    }
}
