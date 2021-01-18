package mage.cards.b;

import mage.MageInt;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BreakneckBerserker extends CardImpl {

    public BreakneckBerserker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.BERSERKER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Haste
        this.addAbility(HasteAbility.getInstance());
    }

    private BreakneckBerserker(final BreakneckBerserker card) {
        super(card);
    }

    @Override
    public BreakneckBerserker copy() {
        return new BreakneckBerserker(this);
    }
}
