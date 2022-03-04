
package mage.cards.b;

import java.util.UUID;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.ConspireAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author jeffwadsworth
 */
public final class BurnTrail extends CardImpl {

    public BurnTrail(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{R}");

        // Burn Trail deals 3 damage to any target.
        this.getSpellAbility().addEffect(new DamageTargetEffect(3));
        this.getSpellAbility().addTarget(new TargetAnyTarget());

        // Conspire
        this.addAbility(new ConspireAbility(ConspireAbility.ConspireTargets.ONE));
    }

    private BurnTrail(final BurnTrail card) {
        super(card);
    }

    @Override
    public BurnTrail copy() {
        return new BurnTrail(this);
    }
}
