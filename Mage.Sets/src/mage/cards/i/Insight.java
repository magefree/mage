
package mage.cards.i;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author Quercitron
 */
public final class Insight extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a green spell");

    static {
        filter.add(new ColorPredicate(ObjectColor.GREEN));
    }

    public Insight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{U}");


        // Whenever an opponent casts a green spell, you draw a card.
        this.addAbility(new SpellCastOpponentTriggeredAbility(new DrawCardSourceControllerEffect(1, "you"), filter, false));
    }

    private Insight(final Insight card) {
        super(card);
    }

    @Override
    public Insight copy() {
        return new Insight(this);
    }
}
