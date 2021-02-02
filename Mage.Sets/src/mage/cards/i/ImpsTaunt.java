
package mage.cards.i;

import java.util.UUID;
import mage.abilities.effects.common.combat.AttacksIfAbleTargetEffect;
import mage.abilities.keyword.BuybackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LoneFox
 */
public final class ImpsTaunt extends CardImpl {

    public ImpsTaunt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{B}");

        // Buyback {3}
        this.addAbility(new BuybackAbility("{3}"));
        // Target creature attacks this turn if able.
        this.getSpellAbility().addEffect(new AttacksIfAbleTargetEffect(Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private ImpsTaunt(final ImpsTaunt card) {
        super(card);
    }

    @Override
    public ImpsTaunt copy() {
        return new ImpsTaunt(this);
    }
}
