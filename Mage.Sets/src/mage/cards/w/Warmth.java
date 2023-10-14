
package mage.cards.w;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author Quercitron
 */
public final class Warmth extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a red spell");
    static {
        filter.add(new ColorPredicate(ObjectColor.RED));
    }

    public Warmth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{W}");


        // Whenever an opponent casts a red spell, you gain 2 life.
        this.addAbility(new SpellCastOpponentTriggeredAbility(new GainLifeEffect(2), filter, false));
    }

    private Warmth(final Warmth card) {
        super(card);
    }

    @Override
    public Warmth copy() {
        return new Warmth(this);
    }
}
