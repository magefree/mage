package mage.cards.q;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class QuicksmithGenius extends CardImpl {

    public QuicksmithGenius(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Whenever an artifact enters the battlefield under your control, you may discard a card. If you do, draw a card.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(Zone.BATTLEFIELD,
                new DoIfCostPaid(new DrawCardSourceControllerEffect(1), new DiscardCardCost()),
                StaticFilters.FILTER_PERMANENT_ARTIFACT, false));
    }

    private QuicksmithGenius(final QuicksmithGenius card) {
        super(card);
    }

    @Override
    public QuicksmithGenius copy() {
        return new QuicksmithGenius(this);
    }
}
