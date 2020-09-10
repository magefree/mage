package mage.cards.g;

import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.MillCardsEachPlayerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author North
 */
public final class GhoulcallersBell extends CardImpl {

    public GhoulcallersBell(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        // {T}: Each player puts the top card of their library into their graveyard.
        this.addAbility(new SimpleActivatedAbility(new MillCardsEachPlayerEffect(1, TargetController.ANY), new TapSourceCost()));
    }

    private GhoulcallersBell(final GhoulcallersBell card) {
        super(card);
    }

    @Override
    public GhoulcallersBell copy() {
        return new GhoulcallersBell(this);
    }
}
