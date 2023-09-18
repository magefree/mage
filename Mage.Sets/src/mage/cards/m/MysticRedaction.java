package mage.cards.m;

import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.common.DiscardCardControllerTriggeredAbility;
import mage.abilities.effects.common.MillCardsEachPlayerEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MysticRedaction extends CardImpl {

    public MysticRedaction(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");

        // At the beginning of your upkeep, scry 1.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new ScryEffect(1, false), TargetController.YOU, false
        ));

        // Whenever you discard a card, each opponent mills two cards.
        this.addAbility(new DiscardCardControllerTriggeredAbility(
                new MillCardsEachPlayerEffect(2, TargetController.OPPONENT), false
        ));
    }

    private MysticRedaction(final MysticRedaction card) {
        super(card);
    }

    @Override
    public MysticRedaction copy() {
        return new MysticRedaction(this);
    }
}
