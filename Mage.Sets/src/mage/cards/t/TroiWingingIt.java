package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.DrawCardOpponentTriggeredAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.permanent.token.FaerieToken;

import java.util.UUID;

/**
 * @author muz
 */
public final class TroiWingingIt extends CardImpl {

    public TroiWingingIt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.BETAZOID);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Whenever an opponent draws a card during your turn, create a 1/1 blue Faerie creature token with flying.
        this.addAbility(new DrawCardOpponentTriggeredAbility(new CreateTokenEffect(new FaerieToken()), false, false)
            .withTriggerCondition(MyTurnCondition.instance));
    }

    private TroiWingingIt(final TroiWingingIt card) {
        super(card);
    }

    @Override
    public TroiWingingIt copy() {
        return new TroiWingingIt(this);
    }
}
