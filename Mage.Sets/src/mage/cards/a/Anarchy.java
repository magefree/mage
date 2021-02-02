
package mage.cards.a;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author LevelX2
 */
public final class Anarchy extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("white permanents");

    static {
        filter.add(new ColorPredicate(ObjectColor.WHITE));
    }

    public Anarchy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{R}{R}");


        // Destroy all white permanents.
        this.getSpellAbility().addEffect(new DestroyAllEffect(filter));
    }

    private Anarchy(final Anarchy card) {
        super(card);
    }

    @Override
    public Anarchy copy() {
        return new Anarchy(this);
    }
}
