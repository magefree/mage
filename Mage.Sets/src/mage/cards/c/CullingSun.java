
package mage.cards.c;

import java.util.UUID;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ManaValuePredicate;

/**
 *
 * @author Loki
 */
public final class CullingSun extends CardImpl {
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with mana value 3 or less");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 4));
    }

    public CullingSun(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{W}{W}{B}");


        this.getSpellAbility().addEffect(new DestroyAllEffect(filter).setText("destroy each creature with mana value 3 or less"));
    }

    private CullingSun(final CullingSun card) {
        super(card);
    }

    @Override
    public CullingSun copy() {
        return new CullingSun(this);
    }
}
