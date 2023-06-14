
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.StateTriggeredAbility;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class PhylacteryLich extends CardImpl {

    public PhylacteryLich(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{B}{B}");
        this.subtype.add(SubType.ZOMBIE);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Indestructible
        this.addAbility(IndestructibleAbility.getInstance());

        // As Phylactery Lich enters the battlefield, put a phylactery counter on an artifact you control.
        this.addAbility(new AsEntersBattlefieldAbility(new PhylacteryLichEffect(), "put a phylactery counter on an artifact you control"));

        // When you control no permanents with phylactery counters on them, sacrifice Phylactery Lich.
        this.addAbility(new PhylacteryLichAbility());
    }

    private PhylacteryLich(final PhylacteryLich card) {
        super(card);
    }

    @Override
    public PhylacteryLich copy() {
        return new PhylacteryLich(this);
    }

    static class PhylacteryLichAbility extends StateTriggeredAbility {

        public PhylacteryLichAbility() {
            super(Zone.BATTLEFIELD, new SacrificeSourceEffect());
        }

        public PhylacteryLichAbility(final PhylacteryLichAbility ability) {
            super(ability);
        }

        @Override
        public PhylacteryLichAbility copy() {
            return new PhylacteryLichAbility(this);
        }

        @Override
        public boolean checkTrigger(GameEvent event, Game game) {
            for (Permanent perm : game.getBattlefield().getAllActivePermanents(controllerId)) {
                if (perm.getCounters(game).getCount(CounterType.PHYLACTERY) > 0) {
                    return false;
                }
            }
            return true;
        }

        @Override
        public String getRule() {
            return "When you control no permanents with phylactery counters on them, sacrifice {this}.";
        }

    }

}

class PhylacteryLichEffect extends OneShotEffect {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("artifact");

    static {
        filter.add(CardType.ARTIFACT.getPredicate());
    }

    public PhylacteryLichEffect() {
        super(Outcome.Neutral);
        staticText = "put a phylactery counter on an artifact you control";
    }

    public PhylacteryLichEffect(final PhylacteryLichEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            TargetControlledPermanent target = new TargetControlledPermanent(1, 1, filter, true);
            if (target.canChoose(source.getControllerId(), source, game)) {
                if (player.choose(Outcome.Neutral, target, source, game)) {
                    Permanent permanent = game.getPermanent(target.getFirstTarget());
                    if (permanent != null) {
                        permanent.addCounters(CounterType.PHYLACTERY.createInstance(), source.getControllerId(), source, game);
                    }
                }
            }
        }
        return false;
    }

    @Override
    public PhylacteryLichEffect copy() {
        return new PhylacteryLichEffect(this);
    }

}
