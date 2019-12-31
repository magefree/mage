package mage.cards.e;

import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterSpell;
import mage.filter.common.FilterCreatureSpell;
import mage.filter.predicate.mageobject.AdventurePredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EdgewallInnkeeper extends CardImpl {

    private static final FilterSpell filter
            = new FilterCreatureSpell("a creature spell that has an Adventure");

    static {
        filter.add(AdventurePredicate.instance);
    }

    public EdgewallInnkeeper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PEASANT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever you cast a creature spell that has an Adventure, draw a card.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new DrawCardSourceControllerEffect(1), filter, false
        ));
    }

    private EdgewallInnkeeper(final EdgewallInnkeeper card) {
        super(card);
    }

    @Override
    public EdgewallInnkeeper copy() {
        return new EdgewallInnkeeper(this);
    }
}
