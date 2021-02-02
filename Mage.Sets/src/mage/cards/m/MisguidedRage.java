
package mage.cards.m;

import java.util.UUID;
import mage.abilities.effects.common.SacrificeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.target.TargetPlayer;

/**
 *
 * @author LoneFox
 */
public final class MisguidedRage extends CardImpl {

    public MisguidedRage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{R}");

        // Target player sacrifices a permanent.
        this.getSpellAbility().addEffect(new SacrificeEffect(new FilterPermanent(), 1, "Target player"));
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private MisguidedRage(final MisguidedRage card) {
        super(card);
    }

    @Override
    public MisguidedRage copy() {
        return new MisguidedRage(this);
    }
}
