
package mage.cards.g;

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
 * @author nantuko
 */
public final class Geistflame extends CardImpl {

    public Geistflame(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{R}");


        // Geistflame deals 1 damage to any target.
        this.getSpellAbility().addEffect(new DamageTargetEffect(1));
        this.getSpellAbility().addTarget(new TargetAnyTarget());

        // Flashback {3}{R}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl("{3}{R}")));
    }

    private Geistflame(final Geistflame card) {
        super(card);
    }

    @Override
    public Geistflame copy() {
        return new Geistflame(this);
    }
}
