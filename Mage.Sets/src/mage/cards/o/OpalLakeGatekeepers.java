package mage.cards.o;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.YouControlTwoOrMoreGatesCondition;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.hint.common.GatesYouControlHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class OpalLakeGatekeepers extends CardImpl {

    public OpalLakeGatekeepers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");
        this.subtype.add(SubType.VEDALKEN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // When Opal Lake Gatekeepers enters the battlefield, if you control two or more Gates, you may draw a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(1), true)
                .withInterveningIf(YouControlTwoOrMoreGatesCondition.instance).addHint(GatesYouControlHint.instance));
    }

    private OpalLakeGatekeepers(final OpalLakeGatekeepers card) {
        super(card);
    }

    @Override
    public OpalLakeGatekeepers copy() {
        return new OpalLakeGatekeepers(this);
    }

}
