
package mage.cards.f;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TimingRule;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author cbt33
 */
public final class Firebolt extends CardImpl {

    public Firebolt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{R}");


        // Firebolt deals 2 damage to any target.
        this.getSpellAbility().addEffect(new DamageTargetEffect(2));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
        // Flashback {4}{R}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl("{4}{R}")));
    }

    private Firebolt(final Firebolt card) {
        super(card);
    }

    @Override
    public Firebolt copy() {
        return new Firebolt(this);
    }
}
