
package mage.cards.r;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoUnlessTargetPlayerOrTargetsControllerPaysEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

/**
 *
 * @author L_J
 */
public final class RhysticSyphon extends CardImpl {

    public RhysticSyphon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{B}{B}");

        // Unless target player pays {3}, they lose 5 life and you gain 5 life.
        DoUnlessTargetPlayerOrTargetsControllerPaysEffect effect = new DoUnlessTargetPlayerOrTargetsControllerPaysEffect(new LoseLifeTargetEffect(5), new ManaCostsImpl<>("{3}"));
        effect.addEffect(new GainLifeEffect(5));
        effect.setText("Unless target player pays {3}, they lose 5 life and you gain 5 life");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private RhysticSyphon(final RhysticSyphon card) {
        super(card);
    }

    @Override
    public RhysticSyphon copy() {
        return new RhysticSyphon(this);
    }
}
