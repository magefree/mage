
package mage.cards.c;

import java.util.UUID;
import mage.abilities.effects.common.combat.CantAttackTargetEffect;
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
public final class ChangeOfHeart extends CardImpl {

    public ChangeOfHeart(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{W}");

        // Buyback {3}
        this.addAbility(new BuybackAbility("{3}"));
        // Target creature can't attack this turn.
        this.getSpellAbility().addEffect(new CantAttackTargetEffect(Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private ChangeOfHeart(final ChangeOfHeart card) {
        super(card);
    }

    @Override
    public ChangeOfHeart copy() {
        return new ChangeOfHeart(this);
    }
}
