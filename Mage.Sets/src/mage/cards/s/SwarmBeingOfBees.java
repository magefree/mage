package mage.cards.s;

import mage.MageInt;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.MayhemAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SwarmBeingOfBees extends CardImpl {

    public SwarmBeingOfBees(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.INSECT);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Mayhem {B}
        this.addAbility(new MayhemAbility(this, "{B}"));
    }

    private SwarmBeingOfBees(final SwarmBeingOfBees card) {
        super(card);
    }

    @Override
    public SwarmBeingOfBees copy() {
        return new SwarmBeingOfBees(this);
    }
}
