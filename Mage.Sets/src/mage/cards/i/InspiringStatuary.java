
package mage.cards.i;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledSpellsEffect;
import mage.abilities.keyword.ImproviseAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;

/**
 *
 * @author Styxo
 */
public final class InspiringStatuary extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("non-artifact spells you cast");

    static {
        filter.add(Predicates.not(new CardTypePredicate(CardType.ARTIFACT)));
    }

    public InspiringStatuary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // Non-artifact spells you cast have improvise.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityControlledSpellsEffect(new ImproviseAbility(), filter)));

    }

    public InspiringStatuary(final InspiringStatuary card) {
        super(card);
    }

    @Override
    public InspiringStatuary copy() {
        return new InspiringStatuary(this);
    }
}
