package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.YouControlTwoOrMoreGatesCondition;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.hint.common.GatesYouControlHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.KnightToken;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class SunspireGatekeepers extends CardImpl {

    public SunspireGatekeepers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // When Sunspire Gatekeepers enter the battlefield, if you control two or more Gates, create a 2/2 white Knight creature token with vigilance.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new KnightToken()))
                .withInterveningIf(YouControlTwoOrMoreGatesCondition.instance).addHint(GatesYouControlHint.instance));
    }

    private SunspireGatekeepers(final SunspireGatekeepers card) {
        super(card);
    }

    @Override
    public SunspireGatekeepers copy() {
        return new SunspireGatekeepers(this);
    }

}
