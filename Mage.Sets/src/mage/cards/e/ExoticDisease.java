
package mage.cards.e;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.DomainValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.hint.common.DomainHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

/**
 *
 * @author LoneFox
 */
public final class ExoticDisease extends CardImpl {

    public ExoticDisease(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{B}");

        // Domain - Target player loses X life and you gain X life, where X is the number of basic land types among lands you control.
        DomainValue dv = new DomainValue();
        Effect effect = new LoseLifeTargetEffect(dv);
        effect.setText("<i>Domain</i> &mdash; Target player loses X life");
        this.getSpellAbility().addEffect(effect);
        effect = new GainLifeEffect(dv);
        effect.setText("and you gain X life, where X is the number of basic land types among lands you control");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addHint(DomainHint.instance);
    }

    private ExoticDisease(final ExoticDisease card) {
        super(card);
    }

    @Override
    public ExoticDisease copy() {
        return new ExoticDisease(this);
    }
}
