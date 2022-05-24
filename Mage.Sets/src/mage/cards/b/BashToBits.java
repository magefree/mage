
package mage.cards.b;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.Target;
import mage.target.common.TargetArtifactPermanent;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class BashToBits extends CardImpl {

    public BashToBits(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{R}");


        // Destroy target artifact.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        Target target = new TargetArtifactPermanent();
        this.getSpellAbility().addTarget(target);
        // Flashback {4}{R}{R}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{4}{R}{R}")));
    }

    private BashToBits(final BashToBits card) {
        super(card);
    }

    @Override
    public BashToBits copy() {
        return new BashToBits(this);
    }
}
