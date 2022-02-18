
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.SourceTappedCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.combat.BlocksIfAbleSourceEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterAttackingCreature;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.permanent.Permanent;

/**
 *
 * @author Plopman
 */
public final class Watchdog extends CardImpl {

    public Watchdog(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");
        this.subtype.add(SubType.DOG);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Watchdog blocks each turn if able.
        addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BlocksIfAbleSourceEffect(Duration.WhileOnBattlefield)));
        // As long as Watchdog is untapped, all creatures attacking you get -1/-0.
        addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
                new BoostAllEffect(-1, 0, Duration.WhileOnBattlefield, new WatchdogFilter(), false), SourceTappedCondition.UNTAPPED, "As long as {this} is untapped, all creatures attacking you get -1/-0")));
    }

    private Watchdog(final Watchdog card) {
        super(card);
    }

    @Override
    public Watchdog copy() {
        return new Watchdog(this);
    }
}

class WatchdogFilter extends FilterAttackingCreature {

    public WatchdogFilter() {
        super("creatures attacking you");
    }

    public WatchdogFilter(final WatchdogFilter filter) {
        super(filter);
    }

    @Override
    public WatchdogFilter copy() {
        return new WatchdogFilter(this);
    }

    @Override
    public boolean match(Permanent permanent, UUID sourceId, UUID playerId, Game game) {
        if (!super.match(permanent, sourceId, playerId, game)) {
            return false;
        }

        for (CombatGroup group : game.getCombat().getGroups()) {
            for (UUID attacker : group.getAttackers()) {
                if (attacker.equals(permanent.getId())) {
                    UUID defenderId = group.getDefenderId();
                    if (defenderId.equals(playerId)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }
}
