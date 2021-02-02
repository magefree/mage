
package mage.cards.m;

import java.util.UUID;
import mage.abilities.effects.common.CipherEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

/**
 *
 * @author LevelX2
 */
public final class MentalVapors extends CardImpl {

    public MentalVapors (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{B}");


        // Target player discards a card.
        this.getSpellAbility().addEffect(new DiscardTargetEffect(1));
        this.getSpellAbility().addTarget(new TargetPlayer());
        
        // Cipher (Then you may exilce this spell card encoded on a creature you control. Whenever that creature deals combat damage to a player, its controller may cast a copy of the encoded card without paying its mana cost.)
        this.getSpellAbility().addEffect(new CipherEffect());

    }

    private MentalVapors(final MentalVapors card) {
        super(card);
    }

    @Override
    public MentalVapors  copy() {
        return new MentalVapors(this);
    }
}
