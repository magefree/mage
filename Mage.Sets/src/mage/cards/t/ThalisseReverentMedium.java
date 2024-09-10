package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.dynamicvalue.common.TokensCreatedThisTurnCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.game.permanent.token.SpiritWhiteToken;
import mage.watchers.common.CreatedTokenWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ThalisseReverentMedium extends CardImpl {

    public ThalisseReverentMedium(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // At the beginning of each end step, create X 1/1 white Spirit creature tokens with flying, where X is the number of tokens you created this turn.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new CreateTokenEffect(
                new SpiritWhiteToken(), TokensCreatedThisTurnCount.instance
        ), TargetController.ANY, false).addHint(TokensCreatedThisTurnCount.getHint()), new CreatedTokenWatcher());
    }

    private ThalisseReverentMedium(final ThalisseReverentMedium card) {
        super(card);
    }

    @Override
    public ThalisseReverentMedium copy() {
        return new ThalisseReverentMedium(this);
    }
}
