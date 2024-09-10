
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;

/**
 *
 * @author fireshoes
 */
public final class SramSeniorEdificer extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("an Aura, Equipment, or Vehicle spell");

    static {
        filter.add(Predicates.or(SubType.AURA.getPredicate(),
            SubType.EQUIPMENT.getPredicate(),
            SubType.VEHICLE.getPredicate()));
    }

    public SramSeniorEdificer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever you cast an Aura, Equipment, or Vehicle spell, draw a card.
        this.addAbility(new SpellCastControllerTriggeredAbility(new DrawCardSourceControllerEffect(1), filter, false));
    }

    private SramSeniorEdificer(final SramSeniorEdificer card) {
        super(card);
    }

    @Override
    public SramSeniorEdificer copy() {
        return new SramSeniorEdificer(this);
    }
}
