package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.OrCost;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CryptLurker extends CardImpl {

    public CryptLurker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // When Crypt Lurker enters the battlefield, you may sacrifice a creature or discard a creature card. If you do, draw a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DoIfCostPaid(
                new DrawCardSourceControllerEffect(1),
                new OrCost(
                        "sacrifice a creature or discard a creature card", new SacrificeTargetCost(new TargetControlledPermanent(
                                StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT
                        )), new DiscardTargetCost(new TargetCardInHand(StaticFilters.FILTER_CARD_CREATURE_A))
                )
        )));
    }

    private CryptLurker(final CryptLurker card) {
        super(card);
    }

    @Override
    public CryptLurker copy() {
        return new CryptLurker(this);
    }
}
