package mage.cards.z;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author Merlingilb
 */
public class ZoriiBliss extends CardImpl {
    public ZoriiBliss(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");
        this.addSuperType(SuperType.LEGENDARY);
        this.addSubType(SubType.REBEL);
        this.addSubType(SubType.ROGUE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        //Haste
        this.addAbility(HasteAbility.getInstance());

        //Whenever Zorii Bliss attacks, you may discard a card. If you do, any number of target players each draw a card.
        AttacksTriggeredAbility attacksTriggeredAbility = new AttacksTriggeredAbility(
                new DoIfCostPaid(new DrawCardTargetEffect(1), new DiscardCardCost(),
                        "Discard a card and target players draw a card?"), false,
                "Whenever Zorii Bliss attacks, you may discard a card. If you do, any number of target players each draw a card.");
        attacksTriggeredAbility.addTarget(new TargetPlayer(0, Integer.MAX_VALUE, false));
        this.addAbility(attacksTriggeredAbility);
    }

    public ZoriiBliss(final ZoriiBliss card) {
        super(card);
    }

    @Override
    public ZoriiBliss copy() {
        return new ZoriiBliss(this);
    }
}
