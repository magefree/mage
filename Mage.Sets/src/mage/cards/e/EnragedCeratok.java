package mage.cards.e;

import mage.MageInt;
import mage.abilities.keyword.DauntAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EnragedCeratok extends CardImpl {

    public EnragedCeratok(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");

        this.subtype.add(SubType.RHINO);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Enraged Ceratok can't be blocked by creatures with power 2 or less.
        this.addAbility(new DauntAbility());
    }

    private EnragedCeratok(final EnragedCeratok card) {
        super(card);
    }

    @Override
    public EnragedCeratok copy() {
        return new EnragedCeratok(this);
    }
}
