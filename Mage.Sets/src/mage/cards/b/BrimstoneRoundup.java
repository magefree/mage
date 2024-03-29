package mage.cards.b;

import mage.abilities.common.CastSecondSpellTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.PlotAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.MercenaryToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BrimstoneRoundup extends CardImpl {

    public BrimstoneRoundup(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}");

        // Whenever you cast your second spell each turn, create a 1/1 red Mercenary creature token with "{T}: Target creature you control gets +1/+0 until end of turn. Activate only as a sorcery."
        this.addAbility(new CastSecondSpellTriggeredAbility(new CreateTokenEffect(new MercenaryToken())));

        // Plot {2}{R}
        this.addAbility(new PlotAbility(this, "{2}{R}"));
    }

    private BrimstoneRoundup(final BrimstoneRoundup card) {
        super(card);
    }

    @Override
    public BrimstoneRoundup copy() {
        return new BrimstoneRoundup(this);
    }
}
