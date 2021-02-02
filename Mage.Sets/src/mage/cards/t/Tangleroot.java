
package mage.cards.t;

import java.util.UUID;
import mage.Mana;
import mage.abilities.common.SpellCastAllTriggeredAbility;
import mage.abilities.effects.mana.AddManaToManaPoolTargetControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.filter.StaticFilters;

/**
 *
 * @author Eirkei
 */
public final class Tangleroot extends CardImpl {

    public Tangleroot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // Whenever a player casts a creature spell, that player adds {G}.
        this.addAbility(new SpellCastAllTriggeredAbility(
                new AddManaToManaPoolTargetControllerEffect(Mana.GreenMana(1), "their"), StaticFilters.FILTER_SPELL_A_CREATURE, false, SetTargetPointer.PLAYER));
    }

    private Tangleroot(final Tangleroot card) {
        super(card);
    }

    @java.lang.Override
    public Tangleroot copy() {
        return new Tangleroot(this);
    }

}
