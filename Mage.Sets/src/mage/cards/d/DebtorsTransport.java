package mage.cards.d;

import mage.MageInt;
import mage.abilities.keyword.AfterlifeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DebtorsTransport extends CardImpl {

    public DebtorsTransport(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{B}");

        this.subtype.add(SubType.THRULL);
        this.power = new MageInt(5);
        this.toughness = new MageInt(3);

        // Afterlife 2
        this.addAbility(new AfterlifeAbility(2));
    }

    private DebtorsTransport(final DebtorsTransport card) {
        super(card);
    }

    @Override
    public DebtorsTransport copy() {
        return new DebtorsTransport(this);
    }
}
