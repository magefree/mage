
package mage.cards.e;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TimingRule;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author North
 */
public final class EarthRift extends CardImpl {

    public EarthRift(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{R}");


        // Destroy target land.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetLandPermanent());
        // Flashback {5}{R}{R}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl("{5}{R}{R}")));
    }

    private EarthRift(final EarthRift card) {
        super(card);
    }

    @Override
    public EarthRift copy() {
        return new EarthRift(this);
    }
}
