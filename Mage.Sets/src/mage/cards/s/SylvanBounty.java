
package mage.cards.s;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.GainLifeTargetEffect;
import mage.abilities.keyword.BasicLandcyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

/**
 *
 * @author North
 */
public final class SylvanBounty extends CardImpl {

    public SylvanBounty(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{5}{G}");


        // Target player gains 8 life.
        this.getSpellAbility().addEffect(new GainLifeTargetEffect(8));
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.addAbility(new BasicLandcyclingAbility(new ManaCostsImpl<>("{1}{G}")));
    }

    private SylvanBounty(final SylvanBounty card) {
        super(card);
    }

    @Override
    public SylvanBounty copy() {
        return new SylvanBounty(this);
    }
}
