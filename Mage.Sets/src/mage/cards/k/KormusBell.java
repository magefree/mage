package mage.cards.k;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.game.permanent.token.custom.CreatureToken;

/**
 *
 * @author KholdFuzion
 *
 */
public final class KormusBell extends CardImpl {

    public KormusBell(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // All Swamps are 1/1 black creatures that are still lands.
        ContinuousEffect effect = new BecomesCreatureAllEffect(
                new CreatureToken(1, 1, "1/1 black creature").withColor("B"),
                "lands", new FilterPermanent(SubType.SWAMP, "Swamps"),
                Duration.WhileOnBattlefield, true);
        effect.addDependedToType(DependencyType.BecomeSwamp);
        effect.addDependedToType(DependencyType.BecomeIsland);
        effect.addDependedToType(DependencyType.BecomeForest);
        effect.addDependedToType(DependencyType.BecomeMountain);
        effect.addDependedToType(DependencyType.BecomePlains);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
    }

    private KormusBell(final KormusBell card) {
        super(card);
    }

    @Override
    public KormusBell copy() {
        return new KormusBell(this);
    }
}
