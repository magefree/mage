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

    private static final FilterLandPermanent filter = new FilterLandPermanent(SubType.FOREST, "All Forests");

    public LivingLands(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{G}");

        // All Forests are 1/1 creatures that are still lands.
        ContinuousEffect effect = new BecomesCreatureAllEffect(
                new CreatureToken(1, 1, "1/1 creatures"),
                "lands", filter, Duration.WhileOnBattlefield, false);
        effect.getDependencyTypes().add(DependencyType.BecomeForest); // TODO: are these dependencies correct/complete?
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
