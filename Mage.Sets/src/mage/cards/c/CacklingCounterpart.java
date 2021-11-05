
package mage.cards.c;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TimingRule;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 * @author nantuko
 */
public final class CacklingCounterpart extends CardImpl {

    public CacklingCounterpart(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{U}{U}");

        // Create a token that's a copy of target creature you control.
        this.getSpellAbility().addEffect(new CreateTokenCopyTargetEffect());
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());

        // Flashback {5}{U}{U}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl("{5}{U}{U}")));
    }

    private CacklingCounterpart(final CacklingCounterpart card) {
        super(card);
    }

    @Override
    public CacklingCounterpart copy() {
        return new CacklingCounterpart(this);
    }
}
