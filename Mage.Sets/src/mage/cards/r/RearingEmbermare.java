package mage.cards.r;

import mage.MageInt;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RearingEmbermare extends CardImpl {

    public RearingEmbermare(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");

        this.subtype.add(SubType.HORSE);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());
    }

    private RearingEmbermare(final RearingEmbermare card) {
        super(card);
    }

    @Override
    public RearingEmbermare copy() {
        return new RearingEmbermare(this);
    }
}
