
package mage.cards.g;

import java.util.UUID;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

/**
 *
 * @author Loki
 */
public final class GlimpseTheUnthinkable extends CardImpl {

    public GlimpseTheUnthinkable(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{U}{B}");


        // Target player puts the top ten cards of their library into their graveyard.
        this.getSpellAbility().addEffect(new MillCardsTargetEffect(10));
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private GlimpseTheUnthinkable(final GlimpseTheUnthinkable card) {
        super(card);
    }

    @Override
    public GlimpseTheUnthinkable copy() {
        return new GlimpseTheUnthinkable(this);
    }
}
