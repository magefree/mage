
package mage.cards.s;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.TimingRule;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public final class SavingGrasp extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature you own");

    static {
        filter.add(TargetController.YOU.getOwnerPredicate());
    }

    public SavingGrasp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{U}");


        // Return target creature you own to your hand.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        // Flashback {W}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl("{W}")));
    }

    private SavingGrasp(final SavingGrasp card) {
        super(card);
    }

    @Override
    public SavingGrasp copy() {
        return new SavingGrasp(this);
    }
}
