
package mage.cards.e;

import java.util.UUID;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;

/**
 *
 * @author fireshoes
 */
public final class EmrakulsInfluence extends CardImpl {

    private static final FilterSpell filterSpell = new FilterSpell("Eldrazi creature spell with converted mana cost 7 or greater");

    static {
        filterSpell.add(new SubtypePredicate( SubType.ELDRAZI));
        filterSpell.add(new ConvertedManaCostPredicate(ComparisonType.MORE_THAN, 6));
    }

    public EmrakulsInfluence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{G}{G}");

        // Whenever you cast an Eldrazi creature spell with converted mana cost 7 or greater, draw two cards.
        this.addAbility(new SpellCastControllerTriggeredAbility(new DrawCardSourceControllerEffect(2), filterSpell, false));
    }

    public EmrakulsInfluence(final EmrakulsInfluence card) {
        super(card);
    }

    @Override
    public EmrakulsInfluence copy() {
        return new EmrakulsInfluence(this);
    }
}
