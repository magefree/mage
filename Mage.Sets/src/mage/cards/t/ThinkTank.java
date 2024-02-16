package mage.cards.t;

import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author cbt33
 */
public final class ThinkTank extends CardImpl {

    public ThinkTank(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");

        // At the beginning of your upkeep, look at the top card of your library. You may put that card into your graveyard.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new SurveilEffect(1), TargetController.YOU, false
        ));
    }

    private ThinkTank(final ThinkTank card) {
        super(card);
    }

    @Override
    public ThinkTank copy() {
        return new ThinkTank(this);
    }
}
