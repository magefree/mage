
package mage.cards.f;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.PermanentsEnterBattlefieldTappedEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class FrozenAether extends CardImpl {

    private static final FilterPermanent filter
            = new FilterPermanent("artifacts, creatures, and lands your opponents control");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate(),
                CardType.LAND.getPredicate()
        ));
    }

    public FrozenAether(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{U}");

        // Artifacts, creatures, and lands your opponents control enter the battlefield tapped.
        this.addAbility(new SimpleStaticAbility(new PermanentsEnterBattlefieldTappedEffect(filter)));
    }

    private FrozenAether(final FrozenAether card) {
        super(card);
    }

    @Override
    public FrozenAether copy() {
        return new FrozenAether(this);
    }
}
