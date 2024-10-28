package mage.cards.w;

import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WorryBeads extends CardImpl {

    public WorryBeads(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // At the beginning of each player's upkeep, that player puts the top card of their library into their graveyard.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                TargetController.EACH_PLAYER, new MillCardsTargetEffect(1).setText("that player mills a card"),
                false
        ));
    }

    private WorryBeads(final WorryBeads card) {
        super(card);
    }

    @Override
    public WorryBeads copy() {
        return new WorryBeads(this);
    }
}
