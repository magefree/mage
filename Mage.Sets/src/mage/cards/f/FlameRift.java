
package mage.cards.f;

import java.util.UUID;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;

/**
 *
 * @author LevelX2
 */
public final class FlameRift extends CardImpl {

    public FlameRift(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{R}");


        // Flame Rift deals 4 damage to each player.
        this.getSpellAbility().addEffect(new DamagePlayersEffect(4, TargetController.ANY));
    }

    private FlameRift(final FlameRift card) {
        super(card);
    }

    @Override
    public FlameRift copy() {
        return new FlameRift(this);
    }
}
