
package mage.cards.t;

import java.util.UUID;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.PopulateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevleX2
 */
public final class TrostanisJudgment extends CardImpl {

    public TrostanisJudgment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{5}{W}");


        // Exile target creature, then populate.
        // (Create a token that's a copy of a creature token you control.)
        this.getSpellAbility().addEffect(new ExileTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new PopulateEffect("then"));
    }

    private TrostanisJudgment(final TrostanisJudgment card) {
        super(card);
    }

    @Override
    public TrostanisJudgment copy() {
        return new TrostanisJudgment(this);
    }
}