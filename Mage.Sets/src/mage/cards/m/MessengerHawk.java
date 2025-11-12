package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.DrewTwoOrMoreCardsCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.dynamicvalue.common.CardsDrawnThisTurnDynamicValue;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.game.permanent.token.ClueArtifactToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MessengerHawk extends CardImpl {

    public MessengerHawk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U/B}");

        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When this creature enters, create a Clue token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new ClueArtifactToken())));

        // This creature gets +2/+0 as long as you've drawn two or more cards this turn.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostSourceEffect(2, 0, Duration.WhileOnBattlefield),
                DrewTwoOrMoreCardsCondition.instance, "{this} gets +2/+0 as long as you've drawn two or more cards this turn"
        )).addHint(CardsDrawnThisTurnDynamicValue.getHint()));
    }

    private MessengerHawk(final MessengerHawk card) {
        super(card);
    }

    @Override
    public MessengerHawk copy() {
        return new MessengerHawk(this);
    }
}
