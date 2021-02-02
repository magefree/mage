
package mage.cards.c;

import java.util.UUID;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.abilities.keyword.HauntAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

/**
 *
 * @author LevelX2
 */
public final class CryOfContrition extends CardImpl {

    public CryOfContrition(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{B}");

        // Target player discards a card.
        this.getSpellAbility().addEffect(new DiscardTargetEffect(1));
        this.getSpellAbility().addTarget(new TargetPlayer());
        
        // Haunt (When this spell card is put into a graveyard after resolving, exile it haunting target creature.)
        // When the creature Cry of Contrition haunts dies, target player discards a card.
        HauntAbility ability = new HauntAbility(this, new DiscardTargetEffect(1));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
        
    }

    private CryOfContrition(final CryOfContrition card) {
        super(card);
    }

    @Override
    public CryOfContrition copy() {
        return new CryOfContrition(this);
    }
}
