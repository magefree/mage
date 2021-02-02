
package mage.cards.f;

import java.util.UUID;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetSpell;

/**
 *
 * @author LevelX2
 */
public final class FallOfTheGavel extends CardImpl {

    public FallOfTheGavel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{W}{U}");


        // Target player draws two cards.
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addEffect(new GainLifeEffect(5));
        this.getSpellAbility().addTarget(new TargetSpell());

    }

    private FallOfTheGavel(final FallOfTheGavel card) {
        super(card);
    }

    @Override
    public FallOfTheGavel copy() {
        return new FallOfTheGavel(this);
    }
}
