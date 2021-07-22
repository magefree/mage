
package mage.cards.b;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.DamageEverythingEffect;
import mage.abilities.keyword.HorsemanshipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;

/**
 *
 * @author LoneFox
 */
public final class BorrowingTheEastWind extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with horsemanship");

    static {
        filter.add(new AbilityPredicate(HorsemanshipAbility.class));
    }

    public BorrowingTheEastWind(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{X}{G}{G}");

        // Borrowing the East Wind deals X damage to each creature with horsemanship and each player.
        this.getSpellAbility().addEffect(new DamageEverythingEffect(ManacostVariableValue.REGULAR, filter));                                                                                                  }

    private BorrowingTheEastWind(final BorrowingTheEastWind card) {
        super(card);
    }

    @Override
    public BorrowingTheEastWind copy() {
        return new BorrowingTheEastWind(this);
    }
}
