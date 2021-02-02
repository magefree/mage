
package mage.cards.t;

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
public final class ThreeTragedies extends CardImpl {

    public ThreeTragedies(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{B}{B}");
        this.subtype.add(SubType.ARCANE);

        // Target player discards three cards.
        this.getSpellAbility().addEffect(new DiscardTargetEffect(3));
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private ThreeTragedies(final ThreeTragedies card) {
        super(card);
    }

    @Override
    public ThreeTragedies copy() {
        return new ThreeTragedies(this);
    }
}
