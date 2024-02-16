
package mage.cards.r;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.game.permanent.token.KorAllyToken;

/**
 *
 * @author fireshoes
 */
public final class RetreatToEmeria extends CardImpl {

    public RetreatToEmeria(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}");

        // <i>Landfall</i> &mdash; Whenever a land enters the battlefield under you control, choose one - Create a 1/1 white Kor Ally creature token; or Creatures you control get +1/+1 until end of turn.
        LandfallAbility ability = new LandfallAbility(new CreateTokenEffect(new KorAllyToken()), false);
        Mode mode = new Mode(new BoostControlledEffect(1, 1, Duration.EndOfTurn));
        ability.addMode(mode);
        this.addAbility(ability);
    }

    private RetreatToEmeria(final RetreatToEmeria card) {
        super(card);
    }

    @Override
    public RetreatToEmeria copy() {
        return new RetreatToEmeria(this);
    }
}
