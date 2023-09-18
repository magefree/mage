
package mage.cards.f;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DoUnlessTargetPlayerOrTargetsControllerPaysEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

/**
 *
 * @author L_J
 */
public final class Flay extends CardImpl {

    public Flay(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{B}");

        // Target player discards a card at random. Then that player discards another card at random unless they pay {1}.
        this.getSpellAbility().addEffect(new DiscardTargetEffect(1, true));
        Effect effect = new DoUnlessTargetPlayerOrTargetsControllerPaysEffect(new DiscardTargetEffect(1, true), new ManaCostsImpl<>("{1}"));
        effect.setText("Then that player discards another card at random unless they pay {1}");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private Flay(final Flay card) {
        super(card);
    }

    @Override
    public Flay copy() {
        return new Flay(this);
    }
}
