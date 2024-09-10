package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PiggyBank extends CardImpl {

    public PiggyBank(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.BOAR);
        this.subtype.add(SubType.TOY);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Piggy Bank dies, create a Treasure token.
        this.addAbility(new DiesSourceTriggeredAbility(new CreateTokenEffect(new TreasureToken())));
    }

    private PiggyBank(final PiggyBank card) {
        super(card);
    }

    @Override
    public PiggyBank copy() {
        return new PiggyBank(this);
    }
}
