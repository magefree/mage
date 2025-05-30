
package mage.cards.r;

import mage.abilities.dynamicvalue.common.GreatestAmongPermanentsValue;
import mage.abilities.effects.common.GainLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author nigelzor
 */
public final class RewardTheFaithful extends CardImpl {

    public RewardTheFaithful(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}");

        // Any number of target players each gain life equal to the greatest converted mana cost among permanents you control.
        this.getSpellAbility().addEffect(new GainLifeTargetEffect(GreatestAmongPermanentsValue.MANAVALUE_CONTROLLED_PERMANENTS)
                .setText("Any number of target players each gain life equal to the greatest mana value among permanents you control."));
        this.getSpellAbility().addHint(GreatestAmongPermanentsValue.MANAVALUE_CONTROLLED_PERMANENTS.getHint());
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
