package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TestamentBearer extends CardImpl {

    public TestamentBearer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(1);

        // When Testament Bearer dies, look at the top three cards of your library. Put one of them into your hand and the rest into your graveyard.
        this.addAbility(new DiesSourceTriggeredAbility(new LookLibraryAndPickControllerEffect(
                3, 1, PutCards.HAND, PutCards.GRAVEYARD
        )));
    }

    private TestamentBearer(final TestamentBearer card) {
        super(card);
    }

    @Override
    public TestamentBearer copy() {
        return new TestamentBearer(this);
    }
}
