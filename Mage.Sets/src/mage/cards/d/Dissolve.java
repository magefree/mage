
package mage.cards.d;

import java.util.UUID;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.Target;
import mage.target.TargetSpell;

/**
 *
 * @author LevelX2
 */
public final class Dissolve extends CardImpl {


    public Dissolve(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{U}{U}");


        // Counter target spell. Scry 1. (Look at the top card of your library. You may put that card on the bottom of your library.)
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        Target target = new TargetSpell();
        this.getSpellAbility().addTarget(target);
        this.getSpellAbility().addEffect(new ScryEffect(1));
    }

    private Dissolve(final Dissolve card) {
        super(card);
    }

    @Override
    public Dissolve copy() {
        return new Dissolve(this);
    }
}
