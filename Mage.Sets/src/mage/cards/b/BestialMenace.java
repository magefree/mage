

package mage.cards.b;

import java.util.UUID;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.ElephantToken;
import mage.game.permanent.token.SnakeToken;
import mage.game.permanent.token.WolfToken;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class BestialMenace extends CardImpl {

    public BestialMenace(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{G}{G}");

        this.getSpellAbility().addEffect(new CreateTokenEffect(new SnakeToken()));
        this.getSpellAbility().addEffect(new CreateTokenEffect(new WolfToken()));
        this.getSpellAbility().addEffect(new CreateTokenEffect(new ElephantToken()));
    }

    public BestialMenace(final BestialMenace card) {
        super(card);
    }

    @Override
    public BestialMenace copy() {
        return new BestialMenace(this);
    }

}
