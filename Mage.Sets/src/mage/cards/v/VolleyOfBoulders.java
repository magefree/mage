
package mage.cards.v;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageMultiEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TimingRule;
import mage.target.common.TargetAnyTargetAmount;

/**
 *
 * @author cbt33
 */
public final class VolleyOfBoulders extends CardImpl {

    public VolleyOfBoulders(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{8}{R}");


        // Volley of Boulders deals 6 damage divided as you choose among any number of target creatures and/or players.
        this.getSpellAbility().addEffect(new DamageMultiEffect(6));
        this.getSpellAbility().addTarget(new TargetAnyTargetAmount(6));
        // Flashback {R}{R}{R}{R}{R}{R}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl("{R}{R}{R}{R}{R}{R}")));
    }

    private VolleyOfBoulders(final VolleyOfBoulders card) {
        super(card);
    }

    @Override
    public VolleyOfBoulders copy() {
        return new VolleyOfBoulders(this);
    }
}
