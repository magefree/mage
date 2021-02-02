
package mage.cards.f;

import java.util.UUID;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

/**
 *
 * @author Plopman
 */
public final class FillWithFright extends CardImpl {

    public FillWithFright(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{B}");


        // Target player discards two cards.
        this.getSpellAbility().addEffect(new DiscardTargetEffect(2));
        this.getSpellAbility().addTarget(new TargetPlayer());
        // Scry 2.
        this.getSpellAbility().addEffect(new ScryEffect(2));
    }

    private FillWithFright(final FillWithFright card) {
        super(card);
    }

    @Override
    public FillWithFright copy() {
        return new FillWithFright(this);
    }
}
