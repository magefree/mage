
package mage.cards.r;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.PermanentsEnterBattlefieldTappedEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class RootMaze extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("artifacts and lands");

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.LAND.getPredicate()
        ));
    }

    public RootMaze(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{G}");

        // Artifacts and lands enter the battlefield tapped.
        this.addAbility(new SimpleStaticAbility(new PermanentsEnterBattlefieldTappedEffect(filter)));
    }

    private RootMaze(final RootMaze card) {
        super(card);
    }

    @Override
    public RootMaze copy() {
        return new RootMaze(this);
    }
}
