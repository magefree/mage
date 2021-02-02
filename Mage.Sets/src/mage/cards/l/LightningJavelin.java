
package mage.cards.l;

import java.util.UUID;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author LevelX2
 */
public final class LightningJavelin extends CardImpl {

    public LightningJavelin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{R}");

        // Lightning Javelin deals 3 damage to any target.
        this.getSpellAbility().addEffect(new DamageTargetEffect(3));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
        // Scry 1.
        this.getSpellAbility().addEffect(new ScryEffect(1));        
    }

    private LightningJavelin(final LightningJavelin card) {
        super(card);
    }

    @Override
    public LightningJavelin copy() {
        return new LightningJavelin(this);
    }
}
