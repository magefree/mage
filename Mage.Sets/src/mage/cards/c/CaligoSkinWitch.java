
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.discard.DiscardEachPlayerEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

/**
 *
 * @author JRHerlehy
 */
public final class CaligoSkinWitch extends CardImpl {

    public CaligoSkinWitch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.HUMAN, SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Kicker {3}{B}
        this.addAbility(new KickerAbility("{3}{B}"));

        // When Caligo Skin-Witch enters the battlefield, if it was kicked, each opponent discards two cards.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new DiscardEachPlayerEffect(
                        StaticValue.get(2),
                        false,
                        TargetController.OPPONENT
                )),
                KickedCondition.ONCE,
                "When {this} enters the battlefield, if it was kicked, each opponent discards two cards."
        ));
    }

    private CaligoSkinWitch(final CaligoSkinWitch card) {
        super(card);
    }

    @Override
    public CaligoSkinWitch copy() {
        return new CaligoSkinWitch(this);
    }
}
