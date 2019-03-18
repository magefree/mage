
package mage.cards.c;

import java.util.UUID;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;

/**
 *
 * @author Loki
 */
public final class CullingSun extends CardImpl {
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with converted mana cost 3 or less");

    static {
        filter.add(new ConvertedManaCostPredicate(ComparisonType.FEWER_THAN, 4));
    }

    public CullingSun(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{W}{W}{B}");


        this.getSpellAbility().addEffect(new DestroyAllEffect(filter));
    }

    public CullingSun(final CullingSun card) {
        super(card);
    }

    @Override
    public CullingSun copy() {
        return new CullingSun(this);
    }
}
