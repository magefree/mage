

package mage.cards.w;

import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 *
 * @author Loki
 */
public final class WakingNightmare extends CardImpl {

    public WakingNightmare (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{B}");
        this.subtype.add(SubType.ARCANE);

        this.getSpellAbility().addEffect(new DiscardTargetEffect(2));
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private WakingNightmare(final WakingNightmare card) {
        super(card);
    }

    @Override
    public WakingNightmare copy() {
        return new WakingNightmare(this);
    }

}
