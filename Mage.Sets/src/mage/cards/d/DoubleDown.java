package mage.cards.d;

import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.CopyTargetStackObjectEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.OutlawPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DoubleDown extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("an outlaw spell");

    static {
        filter.add(OutlawPredicate.instance);
    }

    public DoubleDown(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{U}");

        // Whenever you cast an outlaw spell, copy that spell.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new CopyTargetStackObjectEffect(false, false, false),
                filter, false, SetTargetPointer.SPELL
        ));
    }

    private DoubleDown(final DoubleDown card) {
        super(card);
    }

    @Override
    public DoubleDown copy() {
        return new DoubleDown(this);
    }
}
