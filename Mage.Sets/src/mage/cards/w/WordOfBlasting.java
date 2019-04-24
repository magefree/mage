
package mage.cards.w;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.TargetConvertedManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetControllerEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.TargetPermanent;

/**
 *
 * @author LoneFox
 */
public final class WordOfBlasting extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("Wall");

    static {
        filter.add(new SubtypePredicate(SubType.WALL));
    }

    public WordOfBlasting(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{R}");

        // Destroy target Wall. It can't be regenerated. Word of Blasting deals damage equal to that Wall's converted mana cost to the Wall's controller.
        this.getSpellAbility().addEffect(new DestroyTargetEffect(true));
        Effect effect = new DamageTargetControllerEffect(TargetConvertedManaCost.instance);
        effect.setText("{this} deals damage equal to that Wall's converted mana cost to the Wall's controller");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
    }

    public WordOfBlasting(final WordOfBlasting card) {
        super(card);
    }

    @Override
    public WordOfBlasting copy() {
        return new WordOfBlasting(this);
    }
}
