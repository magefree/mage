package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.DrawCardTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.RatToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MadRatter extends CardImpl {

    public MadRatter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.GOBLIN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Whenever you draw your second card each turn, create two 1/1 black Rat creature tokens.
        this.addAbility(new DrawCardTriggeredAbility(new CreateTokenEffect(new RatToken(), 2), false, 2));
    }

    private MadRatter(final MadRatter card) {
        super(card);
    }

    @Override
    public MadRatter copy() {
        return new MadRatter(this);
    }
}
