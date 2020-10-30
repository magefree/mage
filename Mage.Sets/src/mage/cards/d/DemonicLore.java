package mage.cards.d;

import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.MultipliedValue;
import mage.abilities.dynamicvalue.common.CardsInControllerHandCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DemonicLore extends CardImpl {

    private static final DynamicValue xValue = new MultipliedValue(CardsInControllerHandCount.instance, 2);

    public DemonicLore(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}");

        // When Demonic Lore enters the battlefield, draw three cards.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(3)));

        // At the beginning of your end step, you lose 2 life for each card in your hand.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new LoseLifeSourceControllerEffect(xValue)
                        .setText("you lose 2 life for each card in your hand"),
                TargetController.YOU, false
        ));
    }

    private DemonicLore(final DemonicLore card) {
        super(card);
    }

    @Override
    public DemonicLore copy() {
        return new DemonicLore(this);
    }
}
