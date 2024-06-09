
package mage.cards.u;

import java.util.UUID;
import mage.abilities.effects.common.CipherEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.SacrificeEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.target.TargetPlayer;

/**
 *
 * @author LevelX2
 */
public final class UndercityPlague extends CardImpl {

    public UndercityPlague(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{B}{B}");


        // Target player loses 1 life, discards a card, then sacrifices a permanent.
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new LoseLifeTargetEffect(1));
        this.getSpellAbility().addEffect(new DiscardTargetEffect(1).setText(", discards a card"));
        this.getSpellAbility().addEffect(new SacrificeEffect(new FilterPermanent(),1,"then that player")
                .setText(", then sacrifices a permanent"));

        // Cipher (Then you may exile this spell card encoded on a creature you control. Whenever that creature deals combat damage to a player, its controller may cast a copy of the encoded card without paying its mana cost.)
        this.getSpellAbility().addEffect(new CipherEffect());

    }

    private UndercityPlague(final UndercityPlague card) {
        super(card);
    }

    @Override
    public UndercityPlague copy() {
        return new UndercityPlague(this);
    }
}
