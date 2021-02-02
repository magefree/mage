
package mage.cards.v;

import java.util.UUID;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreatureCard;
import mage.game.permanent.token.ZombieToken;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author North
 */
public final class VileRebirth extends CardImpl {

    public VileRebirth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{B}");

        // Exile target creature card from a graveyard.
        this.getSpellAbility().addEffect(new ExileTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInGraveyard(new FilterCreatureCard("creature card from a graveyard")));

        // Create a 2/2 black Zombie creature token.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new ZombieToken()));
    }

    private VileRebirth(final VileRebirth card) {
        super(card);
    }

    @Override
    public VileRebirth copy() {
        return new VileRebirth(this);
    }
}
