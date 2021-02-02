
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.permanent.Permanent;

/**
 *
 * @author TheElk801
 */
public final class RampagingCyclops extends CardImpl {

    public RampagingCyclops(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.CYCLOPS);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Rampaging Cyclops gets -2/-0 as long as two or more creatures are blocking it.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new ConditionalContinuousEffect(
                        new BoostSourceEffect(-2, 0, Duration.WhileOnBattlefield),
                        RampagingCyclopsCondition.instance,
                        "{this} gets -2/-0 as long as two or more creatures are blocking it"
                )
        ));
    }

    private RampagingCyclops(final RampagingCyclops card) {
        super(card);
    }

    @Override
    public RampagingCyclops copy() {
        return new RampagingCyclops(this);
    }
}

enum RampagingCyclopsCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getBattlefield().getPermanent(source.getSourceId());
        if (permanent == null || !permanent.isAttacking()) {
            return false;
        }
        CombatGroup combatGroup = game.getCombat().findGroup(permanent.getId());
        return combatGroup != null && combatGroup.getBlockers().size() > 1;
    }

    @Override
    public String toString() {
        return "two or more creatures are blocking {this}";
    }
}
