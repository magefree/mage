

package mage.cards.e;

import java.util.UUID;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.EldraziSpawnToken;
import mage.target.TargetPlayer;

/**
 *
 * @author Loki
 */
public final class EssenceFeed extends CardImpl {

    public EssenceFeed (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{5}{B}");

        this.getSpellAbility().addEffect(new LoseLifeTargetEffect(3));
        this.getSpellAbility().addEffect(new GainLifeEffect(3));
        this.getSpellAbility().addEffect(new CreateTokenEffect(new EldraziSpawnToken(), 3).concatBy("and"));
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    public EssenceFeed (final EssenceFeed card) {
        super(card);
    }

    @Override
    public EssenceFeed copy() {
        return new EssenceFeed(this);
    }

}
