package mage.cards.p;

import mage.abilities.common.EntersBattlefieldOrDiesSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PrizedStatue extends CardImpl {

    public PrizedStatue(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // When Prized Statue enters the battlefield or is put into a graveyard from the battlefield, create a Treasure token.
        this.addAbility(new EntersBattlefieldOrDiesSourceTriggeredAbility(
                new CreateTokenEffect(new TreasureToken()), false, false
        ));
    }

    private PrizedStatue(final PrizedStatue card) {
        super(card);
    }

    @Override
    public PrizedStatue copy() {
        return new PrizedStatue(this);
    }
}
