package mage.cards.r;

import mage.MageInt;
import mage.abilities.keyword.DauntAbility;
import mage.abilities.keyword.OffspringAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RustShieldRampager extends CardImpl {

    public RustShieldRampager(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.RACCOON);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Offspring {2}
        this.addAbility(new OffspringAbility("{2}"));

        // This creature can't be blocked by creatures with power 2 or less.
        this.addAbility(new DauntAbility());
    }

    private RustShieldRampager(final RustShieldRampager card) {
        super(card);
    }

    @Override
    public RustShieldRampager copy() {
        return new RustShieldRampager(this);
    }
}
