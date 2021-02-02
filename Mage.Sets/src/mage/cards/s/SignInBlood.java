

package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class SignInBlood extends CardImpl {

    public SignInBlood(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{B}{B}");


        // Target player draws two cards and loses 2 life.
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new DrawCardTargetEffect(2));
        Effect effect = new LoseLifeTargetEffect(2);
        effect.setText("and loses 2 life");
        this.getSpellAbility().addEffect(effect);
    }

    private SignInBlood(final SignInBlood card) {
        super(card);
    }

    @Override
    public SignInBlood copy() {
        return new SignInBlood(this);
    }
}
