
package mage.cards.i;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TokenPredicate;

/**
 *
 * @author LevelX2
 */
public final class IllnessInTheRanks extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Creature tokens");
    static {
        filter.add(TokenPredicate.TRUE);
    }

    public IllnessInTheRanks(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{B}");


        // Creature tokens get -1/-1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostAllEffect(-1,-1, Duration.WhileOnBattlefield, filter, false)));
    }

    private IllnessInTheRanks(final IllnessInTheRanks card) {
        super(card);
    }

    @Override
    public IllnessInTheRanks copy() {
        return new IllnessInTheRanks(this);
    }
}
