package mage.cards.n;

import mage.abilities.common.EntersBattlefieldOrDiesSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.ConstructToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NimblewrightSchematic extends CardImpl {

    public NimblewrightSchematic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // When Nimblewright Schematic enters the battlefield or is put into a graveyard from the battlefield, create a 1/1 colorless Construct artifact creature token.
        this.addAbility(new EntersBattlefieldOrDiesSourceTriggeredAbility(
                new CreateTokenEffect(new ConstructToken()), false, false
        ));
    }

    private NimblewrightSchematic(final NimblewrightSchematic card) {
        super(card);
    }

    @Override
    public NimblewrightSchematic copy() {
        return new NimblewrightSchematic(this);
    }
}
