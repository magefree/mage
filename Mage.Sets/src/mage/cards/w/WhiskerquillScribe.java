package mage.cards.w;

import mage.MageInt;
import mage.abilities.common.ValiantTriggeredAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WhiskerquillScribe extends CardImpl {

    public WhiskerquillScribe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.MOUSE);
        this.subtype.add(SubType.CITIZEN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Valiant -- Whenever Whiskerquill Scribe becomes the target of a spell or ability you control for the first time each turn, you may discard a card. If you do, draw a card.
        this.addAbility(new ValiantTriggeredAbility(new DoIfCostPaid(new DrawCardSourceControllerEffect(1), new DiscardCardCost())));
    }

    private WhiskerquillScribe(final WhiskerquillScribe card) {
        super(card);
    }

    @Override
    public WhiskerquillScribe copy() {
        return new WhiskerquillScribe(this);
    }
}
