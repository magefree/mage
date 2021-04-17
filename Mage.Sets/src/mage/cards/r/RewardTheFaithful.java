
package mage.cards.r;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.HighestManaValueCount;
import mage.abilities.effects.common.GainLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

/**
 *
 * @author nigelzor
 */
public final class RewardTheFaithful extends CardImpl {

    public RewardTheFaithful(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}");

        // Any number of target players each gain life equal to the highest converted mana cost among permanents you control.
        this.getSpellAbility().addEffect(new GainLifeTargetEffect(new HighestManaValueCount())
                .setText("Any number of target players each gain life equal to the highest mana value among permanents you control."));
        this.getSpellAbility().addTarget(new TargetPlayer(0, Integer.MAX_VALUE, false));
    }

    private RewardTheFaithful(final RewardTheFaithful card) {
        super(card);
    }

    @Override
    public RewardTheFaithful copy() {
        return new RewardTheFaithful(this);
    }
}
