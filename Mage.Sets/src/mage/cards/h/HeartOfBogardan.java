package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.CumulativeUpkeepAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetPlayerOrPlaneswalker;

import java.util.UUID;

/**
 * @author emerald000 & L_J
 */
public final class HeartOfBogardan extends CardImpl {

    public HeartOfBogardan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}{R}");

        // Cumulative upkeep-Pay {2}.
        this.addAbility(new CumulativeUpkeepAbility(new ManaCostsImpl<>("{2}")));

        // When a player doesn't pay Heart of Bogardan's cumulative upkeep, Heart of Bogardan deals X damage to target player and each creature they control, where X is twice the number of age counters on Heart of Bogardan minus 2.
        this.addAbility(new HeartOfBogardanTriggeredAbility());
    }

    private HeartOfBogardan(final HeartOfBogardan card) {
        super(card);
    }

    @Override
    public HeartOfBogardan copy() {
        return new HeartOfBogardan(this);
    }
}

class HeartOfBogardanTriggeredAbility extends TriggeredAbilityImpl {

    HeartOfBogardanTriggeredAbility() {
        super(Zone.BATTLEFIELD, new HeartOfBogardanEffect(), false);
        this.addTarget(new TargetPlayerOrPlaneswalker());
    }

    HeartOfBogardanTriggeredAbility(final HeartOfBogardanTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public HeartOfBogardanTriggeredAbility copy() {
        return new HeartOfBogardanTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DIDNT_PAY_CUMULATIVE_UPKEEP;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getSourceId() != null && event.getSourceId().equals(this.getSourceId());
    }

    @Override
    public String getRule() {
        return "When a player doesn't pay {this}'s cumulative upkeep, "
                + "{this} deals X damage to target player or planeswalker "
                + "and each creature that player or that planeswalker's controller controls,"
                + " where X is twice the number of age counters on {this} minus 2.";
    }
}

class HeartOfBogardanEffect extends OneShotEffect {

    public HeartOfBogardanEffect() {
        super(Outcome.Damage);
        staticText = "{this} deals X damage to target player or planeswalker "
                + "and each creature that player or that planeswalker's controller controls, "
                + "where X is twice the number of age counters on {this} minus 2";
    }

    public HeartOfBogardanEffect(final HeartOfBogardanEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayerOrPlaneswalkerController(source.getFirstTarget());
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (player != null && sourcePermanent != null) {
            int damage = sourcePermanent.getCounters(game).getCount(CounterType.AGE) * 2 - 2;
            if (damage > 0) {
                player.damage(damage, source.getSourceId(), source, game);
                for (Permanent perm : game.getBattlefield().getAllActivePermanents(new FilterCreaturePermanent(), player.getId(), game)) {
                    perm.damage(damage, source.getSourceId(), source, game, false, true);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public HeartOfBogardanEffect copy() {
        return new HeartOfBogardanEffect(this);
    }

}
