
package mage.cards.b;

import java.util.UUID;
import mage.abilities.effects.common.discard.DiscardCardYouChooseTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

/**
 *
 * @author LevelX2
 */
public final class Blackmail extends CardImpl {

    public Blackmail(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{B}");


        // Target player reveals three cards from their hand and you choose one of them. That player discards that card.
        this.getSpellAbility().addEffect(new DiscardCardYouChooseTargetEffect(3));
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private Blackmail(final Blackmail card) {
        super(card);
    }

    @Override
    public Blackmail copy() {
        return new Blackmail(this);
    }
}
