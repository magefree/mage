
package mage.cards.o;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.BountyAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Styxo
 */
public final class OpenSeason extends CardImpl {

    public OpenSeason(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}");

        // When {this} enters the battlefield, for each opponent, put a bounty counter on target creature that player controls
        Effect effect = new AddCountersTargetEffect(CounterType.BOUNTY.createInstance());
        effect.setText("for each opponent, put a bounty counter on target creature that player controls");
        Ability ability = new EntersBattlefieldTriggeredAbility(effect);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // Creatures your opponent control with bounty counters on them can't activate abilities
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new OpenSeasonRestrictionEffect()));

        // Bounty - Whenever a creature an opponent controls with a bounty counter on it dies, that creature's controller loses 2 life. Each other player gains 2 life.
        this.addAbility(new BountyAbility(new OpenSeasonEffect(), false, true));

    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability instanceof EntersBattlefieldTriggeredAbility) {
            ability.getTargets().clear();
            for (UUID opponentId : game.getOpponents(ability.getControllerId())) {
                Player opponent = game.getPlayer(opponentId);
                if (opponent != null) {
                    ability.addTarget(new TargetPermanent(new FilterCreaturePermanent("creature from opponent " + opponent.getLogName())));
                }
            }
        }
    }

    public OpenSeason(final OpenSeason card) {
        super(card);
    }

    @Override
    public OpenSeason copy() {
        return new OpenSeason(this);
    }
}

class OpenSeasonRestrictionEffect extends RestrictionEffect {

    public OpenSeasonRestrictionEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "Creatures your opponent control with bounty counters on them can't activate abilities";
    }

    public OpenSeasonRestrictionEffect(final OpenSeasonRestrictionEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.isCreature()
                && permanent.getCounters(game).getCount(CounterType.BOUNTY) > 0
                && game.getOpponents(source.getControllerId()).contains(permanent.getControllerId());
    }

    @Override
    public boolean canUseActivatedAbilities(Permanent permanent, Ability source, Game game) {
        return false;
    }

    @Override
    public OpenSeasonRestrictionEffect copy() {
        return new OpenSeasonRestrictionEffect(this);
    }

}

class OpenSeasonEffect extends OneShotEffect {

    public OpenSeasonEffect() {
        super(Outcome.LoseLife);
        staticText = "that creature's controler loses 2 life. Each other player gains 2 life";
    }

    public OpenSeasonEffect(final OpenSeasonEffect effect) {
        super(effect);
    }

    @Override
    public OpenSeasonEffect copy() {
        return new OpenSeasonEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID controller = game.getControllerId(source.getFirstTarget());
        if (controller != null) {
            game.getPlayer(controller).loseLife(2, game, false);
            for (UUID playerId : game.getOpponents(controller)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    player.gainLife(2, game, source);
                }
            }
            return true;
        }
        return false;
    }

}
