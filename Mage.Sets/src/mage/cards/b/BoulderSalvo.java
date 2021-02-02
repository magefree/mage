
package mage.cards.b;

import java.util.UUID;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.SurgeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class BoulderSalvo extends CardImpl {

    public BoulderSalvo(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{R}");
        
        // Boulder Salvo deals 4 damage to target creature.
        this.getSpellAbility().addEffect(new DamageTargetEffect(4));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Surge {1}{R}
        addAbility(new SurgeAbility(this, "{1}{R}"));
    }

    private BoulderSalvo(final BoulderSalvo card) {
        super(card);
    }

    @Override
    public BoulderSalvo copy() {
        return new BoulderSalvo(this);
    }
}
