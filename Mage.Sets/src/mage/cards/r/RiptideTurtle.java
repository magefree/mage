package mage.cards.r;

import mage.MageInt;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RiptideTurtle extends CardImpl {

    public RiptideTurtle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.TURTLE);
        this.power = new MageInt(0);
        this.toughness = new MageInt(5);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Defender
        this.addAbility(DefenderAbility.getInstance());
    }

    private RiptideTurtle(final RiptideTurtle card) {
        super(card);
    }

    @Override
    public RiptideTurtle copy() {
        return new RiptideTurtle(this);
    }
}
