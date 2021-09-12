
package mage.cards.f;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TimingRule;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author nantuko
 */
public final class FeelingOfDread extends CardImpl {

    public FeelingOfDread(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{W}");


        // Tap up to two target creatures.
        this.getSpellAbility().addEffect(new TapTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 2));

        // Flashback {1}{U}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl("{1}{U}")));
    }

    private FeelingOfDread(final FeelingOfDread card) {
        super(card);
    }

    @Override
    public FeelingOfDread copy() {
        return new FeelingOfDread(this);
    }
}
