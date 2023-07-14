
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

/**
 *
 * @author Loki
 */
public final class SoulFeast extends CardImpl {

    public SoulFeast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{B}{B}");

        this.getSpellAbility().addEffect(new LoseLifeTargetEffect(4));
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new GainLifeEffect(4).concatBy("and"));
    }

    private SoulFeast(final SoulFeast card) {
        super(card);
    }

    @Override
    public SoulFeast copy() {
        return new SoulFeast(this);
    }
}
