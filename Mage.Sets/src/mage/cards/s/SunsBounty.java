
package mage.cards.s;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.RecoverAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author LevelX2
 */
public final class SunsBounty extends CardImpl {

    public SunsBounty(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{W}");

        // You gain 4 life.
        this.getSpellAbility().addEffect(new GainLifeEffect(4));

        // Recover {1}{W}
        this.addAbility(new RecoverAbility(new ManaCostsImpl<>("{1}{W}"), this));
    }

    private SunsBounty(final SunsBounty card) {
        super(card);
    }

    @Override
    public SunsBounty copy() {
        return new SunsBounty(this);
    }
}
