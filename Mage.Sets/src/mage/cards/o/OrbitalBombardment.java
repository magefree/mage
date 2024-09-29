
package mage.cards.o;

import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author Styxo
 */
public final class OrbitalBombardment extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("non-Starship creatures");

    static {
        filter.add(Predicates.not(SubType.STARSHIP.getPredicate()));
    }

    public OrbitalBombardment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{W}{W}");

        // Destroy all non-Starship creatuers.
        getSpellAbility().getEffects().add(new DestroyAllEffect(filter));
    }

    private OrbitalBombardment(final OrbitalBombardment card) {
        super(card);
    }

    @Override
    public OrbitalBombardment copy() {
        return new OrbitalBombardment(this);
    }
}
