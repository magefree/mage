
package mage.cards.a;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.target.TargetPlayer;
import mage.target.common.TargetOpponentsCreaturePermanent;
import mage.target.targetpointer.SecondTargetPointer;

/**
 *
 * @author Styxo
 */
public final class AcquireTarget extends CardImpl {

    public AcquireTarget(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{B}");

        // Target player draws two cards and loses 2 life. 
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new DrawCardTargetEffect(2));
        Effect effect = new LoseLifeTargetEffect(2);
        effect.setText("and loses 2 life");
        this.getSpellAbility().addEffect(effect);

        // Put a bounty counter on up to one target creature an opponent controls.
        effect = new AddCountersTargetEffect(CounterType.BOUNTY.createInstance());
        effect.setTargetPointer(new SecondTargetPointer());
        effect.setText("Put a bounty counter on up to one target creature an opponent controls.");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetOpponentsCreaturePermanent(0, 1));

    }

    private AcquireTarget(final AcquireTarget card) {
        super(card);
    }

    @Override
    public AcquireTarget copy() {
        return new AcquireTarget(this);
    }
}
