
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.SplitSecondAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author LevelX2
 */
public final class SuddenShock extends CardImpl {

    public SuddenShock(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{R}");


        // Split second
        this.addAbility(new SplitSecondAbility());
        // Sudden Shock deals 2 damage to any target.
        this.getSpellAbility().addEffect(new DamageTargetEffect(2, true));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
    }

    private SuddenShock(final SuddenShock card) {
        super(card);
    }

    @Override
    public SuddenShock copy() {
        return new SuddenShock(this);
    }
}
