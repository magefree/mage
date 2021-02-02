
package mage.cards.m;

import java.util.UUID;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.abilities.keyword.BuybackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

/**
 *
 * @author EvilGeek
 */
public final class MindPeel extends CardImpl {

    public MindPeel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{B}");

        // Buyback {2}{B}{B}
        this.addAbility(new BuybackAbility("{2}{B}{B}"));
        
        // Target player discards a card.
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new DiscardTargetEffect(1));
    }

    private MindPeel(final MindPeel card) {
        super(card);
    }

    @Override
    public MindPeel copy() {
        return new MindPeel(this);
    }
}
