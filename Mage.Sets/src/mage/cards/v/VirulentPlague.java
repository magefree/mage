
package mage.cards.v;

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
public final class VirulentPlague extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Creature tokens");
    static {
        filter.add(TokenPredicate.TRUE);
    }

    public VirulentPlague(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{B}");

        // Creature tokens get -2/-2
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostAllEffect(-2,-2, Duration.WhileOnBattlefield, filter, false)));
    }

    private VirulentPlague(final VirulentPlague card) {
        super(card);
    }

    @Override
    public VirulentPlague copy() {
        return new VirulentPlague(this);
    }
}
