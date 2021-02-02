package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldOrDiesSourceTriggeredAbility;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author TheElk801
 */
public final class StitchersSupplier extends CardImpl {

    public StitchersSupplier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Stitcher's Supplier enters the battlefield or dies, put the top three cards of your library into your graveyard.
        this.addAbility(new EntersBattlefieldOrDiesSourceTriggeredAbility(
                new MillCardsControllerEffect(3), false)
        );
    }

    private StitchersSupplier(final StitchersSupplier card) {
        super(card);
    }

    @Override
    public StitchersSupplier copy() {
        return new StitchersSupplier(this);
    }
}
