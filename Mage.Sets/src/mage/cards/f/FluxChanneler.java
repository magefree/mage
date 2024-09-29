package mage.cards.f;

import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.counter.ProliferateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FluxChanneler extends CardImpl {

    public FluxChanneler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever you cast a noncreature spell, proliferate. (Choose any number of permanents and/or players, then give each another counter of each kind already there.)
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new ProliferateEffect(), StaticFilters.FILTER_SPELL_A_NON_CREATURE, false
        ));
    }

    private FluxChanneler(final FluxChanneler card) {
        super(card);
    }

    @Override
    public FluxChanneler copy() {
        return new FluxChanneler(this);
    }
}
