package mage.cards.f;

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
public final class FireNationSoldier extends CardImpl {

    public FireNationSoldier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Haste
        this.addAbility(HasteAbility.getInstance());
    }

    private FireNationSoldier(final FireNationSoldier card) {
        super(card);
    }

    @Override
    public FireNationSoldier copy() {
        return new FireNationSoldier(this);
    }
}
