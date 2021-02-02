
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.BuybackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class SeethingAnger extends CardImpl {

    public SeethingAnger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{R}");

        // Buyback {3}<i>(You may pay an additional {3} as you cast this spell. If you do, put this card into your hand as it resolves.)</i>
        this.addAbility(new BuybackAbility("{3}"));
        
        // Target creature gets +3/+0 until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(3, 0, Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private SeethingAnger(final SeethingAnger card) {
        super(card);
    }

    @Override
    public SeethingAnger copy() {
        return new SeethingAnger(this);
    }
}
