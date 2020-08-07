package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.MutatesSourceTriggeredAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.MutateAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Cloudpiercer extends CardImpl {

    public Cloudpiercer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");

        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Mutate {3}{R}
        this.addAbility(new MutateAbility(this, "{3}{R}"));

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Whenever this creature mutates, you may discard a card. If you do, draw a card.
        this.addAbility(new MutatesSourceTriggeredAbility(new DoIfCostPaid(
                new DrawCardSourceControllerEffect(1), new DiscardCardCost()
        )));
    }

    private Cloudpiercer(final Cloudpiercer card) {
        super(card);
    }

    @Override
    public Cloudpiercer copy() {
        return new Cloudpiercer(this);
    }
}
