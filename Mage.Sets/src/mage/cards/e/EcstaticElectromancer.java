package mage.cards.e;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.ProwessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EcstaticElectromancer extends CardImpl {

    public EcstaticElectromancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U/R}{U/R}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Prowess
        this.addAbility(new ProwessAbility());

        // Whenever Ecstatic Electromancer enters the battlefield or attacks, you may discard a card. If you do, draw a card.
        this.addAbility(new EntersBattlefieldOrAttacksSourceTriggeredAbility(
                new DoIfCostPaid(new DrawCardSourceControllerEffect(1), new DiscardCardCost())
        ));
    }

    private EcstaticElectromancer(final EcstaticElectromancer card) {
        super(card);
    }

    @Override
    public EcstaticElectromancer copy() {
        return new EcstaticElectromancer(this);
    }
}
