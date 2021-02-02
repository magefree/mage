
package mage.cards.g;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.LookAtTargetPlayerHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.target.TargetPlayer;

/**
 *
 * @author Quercitron
 */
public final class GlassesOfUrza extends CardImpl {

    public GlassesOfUrza(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{1}");

        // {tap}: Look at target player's hand.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new LookAtTargetPlayerHandEffect(), new TapSourceCost());
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private GlassesOfUrza(final GlassesOfUrza card) {
        super(card);
    }

    @Override
    public GlassesOfUrza copy() {
        return new GlassesOfUrza(this);
    }
}
