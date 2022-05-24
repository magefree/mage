
package mage.cards.s;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 *
 * @author cbt33
 */
public final class SkullFracture extends CardImpl {

    public SkullFracture(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{B}");


        // Target player discards a card.
        
       this.getSpellAbility().addEffect(new DiscardTargetEffect(1));
       this.getSpellAbility().addTarget(new TargetPlayer());
        // Flashback {3}{B}
       this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{3}{B}")));
    }

    private SkullFracture(final SkullFracture card) {
        super(card);
    }

    @Override
    public SkullFracture copy() {
        return new SkullFracture(this);
    }
}
