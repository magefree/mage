
package mage.cards.g;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.Target;
import mage.target.TargetPermanent;

/**
 *
 * @author LevelX2
 */
public final class GlareOfHeresy extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("white permanent");
    static {
        filter.add(new ColorPredicate(ObjectColor.WHITE));
    }
    public GlareOfHeresy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{W}");


        // Exile target white permanent.
        this.getSpellAbility().addEffect(new ExileTargetEffect());
        Target target = new TargetPermanent(filter);
        this.getSpellAbility().addTarget(target);
    }

    private GlareOfHeresy(final GlareOfHeresy card) {
        super(card);
    }

    @Override
    public GlareOfHeresy copy() {
        return new GlareOfHeresy(this);
    }
}
