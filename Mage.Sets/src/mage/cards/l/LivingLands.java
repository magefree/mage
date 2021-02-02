
package mage.cards.l;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterLandPermanent;
import mage.game.permanent.token.custom.CreatureToken;

import java.util.UUID;

/**
 *
 * @author Quercitron
 */
public final class LivingLands extends CardImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent("Forests");

    static {
        filter.add(SubType.FOREST.getPredicate());
    }

    public LivingLands(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{G}");

        // All Forests are 1/1 creatures that are still lands.
        ContinuousEffect effect = new BecomesCreatureAllEffect(
                new CreatureToken(1, 1),
                "lands", filter, Duration.WhileOnBattlefield, false);
        effect.getDependencyTypes().add(DependencyType.BecomeForest);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
    }

    private LivingLands(final LivingLands card) {
        super(card);
    }

    @Override
    public LivingLands copy() {
        return new LivingLands(this);
    }
}
