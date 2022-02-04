package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.common.FilterControlledPermanent;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GoShintaiOfLostWisdom extends CardImpl {

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(
            new FilterControlledPermanent(SubType.SHRINE)
    );
    private static final Hint hint = new ValueHint("Shrines you control", xValue);

    public GoShintaiOfLostWisdom(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{1}{U}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.SHRINE);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // At the beginning of your end step, you may pay {1}. When you do, target player mills X cards, where X is the number of Shrines you control.
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                new MillCardsTargetEffect(xValue), false,
                "target player mills X cards, where X is the number of Shrines you control"
        );
        ability.addTarget(new TargetPlayer());
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new DoWhenCostPaid(
                ability, new GenericManaCost(1), "Pay {1}?"
        ), TargetController.YOU, false).addHint(hint));
    }

    private GoShintaiOfLostWisdom(final GoShintaiOfLostWisdom card) {
        super(card);
    }

    @Override
    public GoShintaiOfLostWisdom copy() {
        return new GoShintaiOfLostWisdom(this);
    }
}
