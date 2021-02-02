
package mage.cards.c;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.ConvokeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author emerald000
 */
public final class CovenantOfBlood extends CardImpl {

    public CovenantOfBlood(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{6}{B}");


        // Convoke
        this.addAbility(new ConvokeAbility());
        
        // Covenant of Blood deals 4 damage to any target
        Effect effect = new DamageTargetEffect(4);
        effect.setText("{this} deals 4 damage to any target");
        this.getSpellAbility().addEffect(effect);
        // and you gain 4 life.
        effect = new GainLifeEffect(4);
        effect.setText("and you gain 4 life");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetAnyTarget());
    }

    private CovenantOfBlood(final CovenantOfBlood card) {
        super(card);
    }

    @Override
    public CovenantOfBlood copy() {
        return new CovenantOfBlood(this);
    }
}
