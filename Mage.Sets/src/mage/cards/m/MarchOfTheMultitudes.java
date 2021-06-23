package mage.cards.m;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.ConvokeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.SoldierLifelinkToken;

/**
 *
 * @author TheElk801
 */
public final class MarchOfTheMultitudes extends CardImpl {

    public MarchOfTheMultitudes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{G}{W}{W}");

        // Convoke
        this.addAbility(new ConvokeAbility());

        // Create X 1/1 white Soldier creature tokens with lifelink.
        this.getSpellAbility().addEffect(new CreateTokenEffect(
                new SoldierLifelinkToken(),
                ManacostVariableValue.REGULAR
        ));
    }

    private MarchOfTheMultitudes(final MarchOfTheMultitudes card) {
        super(card);
    }

    @Override
    public MarchOfTheMultitudes copy() {
        return new MarchOfTheMultitudes(this);
    }
}
