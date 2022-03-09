
package mage.cards.g;

import java.util.UUID;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.combat.AttacksIfAbleAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.permanent.token.GoblinToken;
import mage.watchers.common.AttackedThisTurnWatcher;

/**
 *
 * @author Plopman
 */
public final class GoblinAssault extends CardImpl {

    public GoblinAssault(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");

        // At the beginning of your upkeep, create a 1/1 red Goblin creature token with haste.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new CreateTokenEffect(new GoblinToken(true)), TargetController.YOU, false));

        // Goblin creatures attack each turn if able.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new AttacksIfAbleAllEffect(StaticFilters.FILTER_PERMANENT_CREATURE_GOBLINS, Duration.WhileOnBattlefield, true)), new AttackedThisTurnWatcher());
    }

    private GoblinAssault(final GoblinAssault card) {
        super(card);
    }

    @Override
    public GoblinAssault copy() {
        return new GoblinAssault(this);
    }
}
