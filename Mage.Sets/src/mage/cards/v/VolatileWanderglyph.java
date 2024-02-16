package mage.cards.v;

import mage.MageInt;
import mage.abilities.common.BecomesTappedSourceTriggeredAbility;
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
public final class VolatileWanderglyph extends CardImpl {

    public VolatileWanderglyph(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Volatile Wanderglyph becomes tapped, you may discard a card. If you do, draw a card.
        this.addAbility(new BecomesTappedSourceTriggeredAbility(
                new DoIfCostPaid(new DrawCardSourceControllerEffect(1), new DiscardCardCost())
        ));
    }

    private VolatileWanderglyph(final VolatileWanderglyph card) {
        super(card);
    }

    @Override
    public VolatileWanderglyph copy() {
        return new VolatileWanderglyph(this);
    }
}
