package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.dynamicvalue.common.CardsDiscardedThisTurnCount;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.watchers.common.DiscardedCardWatcher;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.costs.common.DiscardHandCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class AstonishingSpiderMan extends CardImpl {

    public AstonishingSpiderMan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U/R}{U/R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIDER);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Whenever Astonishing Spider-Man attacks, you may discard your hand. If you do, draw a card for each card you've discarded this turn.
        this.addAbility(new AttacksTriggeredAbility(new DoIfCostPaid(
            new DrawCardSourceControllerEffect(CardsDiscardedThisTurnCount.SINGULAR), new DiscardHandCost()
        )).addHint(CardsDiscardedThisTurnCount.getHint()), new DiscardedCardWatcher());
    }

    private AstonishingSpiderMan(final AstonishingSpiderMan card) {
        super(card);
    }

    @Override
    public AstonishingSpiderMan copy() {
        return new AstonishingSpiderMan(this);
    }
}
