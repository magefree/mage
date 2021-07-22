
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ManaValuePredicate;

/**
 *
 * @author fireshoes
 */
public final class BygoneBishop extends CardImpl {

    private static final FilterSpell filterSpell = new FilterSpell("a creature spell with mana value 3 or less");

    static {
        filterSpell.add(CardType.CREATURE.getPredicate());
        filterSpell.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 4));
    }

    public BygoneBishop(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");
        this.subtype.add(SubType.SPIRIT, SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever you cast a creature spell with converted mana cost 3 or less, investigate.
        // (Put a colorless Clue artifact token onto the battlefield with "{2}, Sacrifice this artifact: Draw a card.")
        this.addAbility(new SpellCastControllerTriggeredAbility(new InvestigateEffect(), filterSpell, false));
    }

    private BygoneBishop(final BygoneBishop card) {
        super(card);
    }

    @Override
    public BygoneBishop copy() {
        return new BygoneBishop(this);
    }
}
