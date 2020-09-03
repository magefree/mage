package mage.cards.f;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 * @author TheElk801
 */
public final class FissureWizard extends CardImpl {

    public FissureWizard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // When Fissure Wizard enters the battlefield, you may discard a card. If you do, draw a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DoIfCostPaid(
                new DrawCardSourceControllerEffect(1), new DiscardCardCost()
        )));
    }

    private FissureWizard(final FissureWizard card) {
        super(card);
    }

    @Override
    public FissureWizard copy() {
        return new FissureWizard(this);
    }
}
