package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.OrCost;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class KunLunWarrior extends CardImpl {

    public KunLunWarrior(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When this creature enters, you may sacrifice an artifact or discard a card. If you do, draw a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DoIfCostPaid(
            new DrawCardSourceControllerEffect(1),
            new OrCost(
                "sacrifice an artifact or discard a card",
                new SacrificeTargetCost(StaticFilters.FILTER_PERMANENT_ARTIFACT),
                new DiscardCardCost()
            )
        )));

    }

    private KunLunWarrior(final KunLunWarrior card) {
        super(card);
    }

    @Override
    public KunLunWarrior copy() {
        return new KunLunWarrior(this);
    }
}
