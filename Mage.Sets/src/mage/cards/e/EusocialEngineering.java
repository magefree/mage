package mage.cards.e;

import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.WarpAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.RobotToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EusocialEngineering extends CardImpl {

    public EusocialEngineering(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{G}{G}");

        // Landfall -- Whenever a land you control enters, create a 2/2 colorless Robot artifact creature token.
        this.addAbility(new LandfallAbility(new CreateTokenEffect(new RobotToken())));

        // Warp {1}{G}
        this.addAbility(new WarpAbility(this, "{1}{G}"));
    }

    private EusocialEngineering(final EusocialEngineering card) {
        super(card);
    }

    @Override
    public EusocialEngineering copy() {
        return new EusocialEngineering(this);
    }
}
