package mage.cards.l;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.ProwlCostWasPaidCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.hint.common.ProwlCostWasPaidHint;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ProwlAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class LatchkeyFaerie extends CardImpl {

    public LatchkeyFaerie(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");
        this.subtype.add(SubType.FAERIE);
        this.subtype.add(SubType.ROGUE);

        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Prowl {2}{U}
        this.addAbility(new ProwlAbility(this, "{2}{U}"));

        // When Latchkey Faerie enters the battlefield, if its prowl cost was paid, draw a card.
        EntersBattlefieldTriggeredAbility ability = new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(1), false);
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability, ProwlCostWasPaidCondition.instance,
                "When {this} enters the battlefield, if its prowl cost was paid, draw a card.")
                .addHint(ProwlCostWasPaidHint.instance));

    }

    private LatchkeyFaerie(final LatchkeyFaerie card) {
        super(card);
    }

    @Override
    public LatchkeyFaerie copy() {
        return new LatchkeyFaerie(this);
    }
}
