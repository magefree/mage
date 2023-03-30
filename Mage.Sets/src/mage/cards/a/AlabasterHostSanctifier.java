package mage.cards.a;

import mage.MageInt;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AlabasterHostSanctifier extends CardImpl {

    public AlabasterHostSanctifier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());
    }

    private AlabasterHostSanctifier(final AlabasterHostSanctifier card) {
        super(card);
    }

    @Override
    public AlabasterHostSanctifier copy() {
        return new AlabasterHostSanctifier(this);
    }
}
