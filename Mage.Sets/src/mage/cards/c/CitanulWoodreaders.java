
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class CitanulWoodreaders extends CardImpl {

    public CitanulWoodreaders(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DRUID);

        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Kicker {2}{G}
        this.addAbility(new KickerAbility("{2}{G}"));

        // When Citanul Woodreaders enters the battlefield, if it was kicked, draw two cards.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(2)),
                KickedCondition.ONCE,
                "When {this} enters the battlefield, if it was kicked, draw two cards."
        ));
    }

    private CitanulWoodreaders(final CitanulWoodreaders card) {
        super(card);
    }

    @Override
    public CitanulWoodreaders copy() {
        return new CitanulWoodreaders(this);
    }
}
