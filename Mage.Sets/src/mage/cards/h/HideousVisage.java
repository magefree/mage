
package mage.cards.h;

import java.util.UUID;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.IntimidateAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterControlledCreaturePermanent;

/**
 *
 * @author Loki
 */
public final class HideousVisage extends CardImpl {

    public HideousVisage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{B}");


        this.getSpellAbility().addEffect(new GainAbilityControlledEffect(IntimidateAbility.getInstance(), Duration.EndOfTurn, new FilterControlledCreaturePermanent("Creatures")));
    }

    private HideousVisage(final HideousVisage card) {
        super(card);
    }

    @Override
    public HideousVisage copy() {
        return new HideousVisage(this);
    }
}
