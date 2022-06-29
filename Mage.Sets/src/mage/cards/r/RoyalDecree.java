
package mage.cards.r;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.CumulativeUpkeepAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author L_J
 */
public final class RoyalDecree extends CardImpl {

    public RoyalDecree(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}{W}");

        // Cumulative upkeep-Pay {W}.
        this.addAbility(new CumulativeUpkeepAbility(new ManaCostsImpl<>("{W}")));

        // Whenever a Swamp, Mountain, black permanent, or red permanent becomes tapped, Royal Decree deals 1 damage to that permanent's controller.
        this.addAbility(new RoyalDecreeAbility());
    }

    private RoyalDecree(final RoyalDecree card) {
        super(card);
    }

    @Override
    public RoyalDecree copy() {
        return new RoyalDecree(this);
    }
}

class RoyalDecreeAbility extends TriggeredAbilityImpl {

    private static final FilterPermanent filter = new FilterPermanent("a Swamp, Mountain, black permanent, or red permanent");
    static {
        filter.add(Predicates.or(
                SubType.SWAMP.getPredicate(),
                SubType.MOUNTAIN.getPredicate(),
                new ColorPredicate(ObjectColor.BLACK),
                new ColorPredicate(ObjectColor.RED)));
    }

    public RoyalDecreeAbility() {
            super(Zone.BATTLEFIELD, new DamageTargetEffect(1));
    }

    RoyalDecreeAbility(final RoyalDecreeAbility ability) {
            super(ability);
    }

    @Override
    public RoyalDecreeAbility copy() {
            return new RoyalDecreeAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TAPPED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(event.getTargetId());
        if (permanent != null && filter.match(permanent, getControllerId(), this, game)) {
            Player player = game.getPlayer(permanent.getControllerId());
            if (player != null) {
                for (Effect effect : this.getEffects()) {
                    effect.setTargetPointer(new FixedTarget(player.getId()));
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a Swamp, Mountain, black permanent, or red permanent becomes tapped, Royal Decree deals 1 damage to that permanent's controller.";
    }
}
