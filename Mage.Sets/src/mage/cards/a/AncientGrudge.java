
package mage.cards.a;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TimingRule;
import mage.target.common.TargetArtifactPermanent;

/**
 * @author nantuko
 */
public final class AncientGrudge extends CardImpl {
    public AncientGrudge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{R}");

        // Destroy target artifact.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetArtifactPermanent());

        // Flashback {G}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{G}")));
    }

    private AncientGrudge(final AncientGrudge card) {
        super(card);
    }

    @Override
    public AncientGrudge copy() {
        return new AncientGrudge(this);
    }
}
