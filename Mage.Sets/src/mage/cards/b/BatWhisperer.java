package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.OpponentsLostLifeCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.hint.common.OpponentsLostLifeHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.BatToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BatWhisperer extends CardImpl {

    public BatWhisperer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // When Bat Whisperer enters the battlefield, if an opponent lost life this turn, create a 1/1 black Bat creature token with flying.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new BatToken())),
                OpponentsLostLifeCondition.instance, "When {this} enters the battlefield, " +
                "if an opponent lost life this turn, create a 1/1 black Bat creature token with flying."
        ).addHint(OpponentsLostLifeHint.instance));
    }

    private BatWhisperer(final BatWhisperer card) {
        super(card);
    }

    @Override
    public BatWhisperer copy() {
        return new BatWhisperer(this);
    }
}
