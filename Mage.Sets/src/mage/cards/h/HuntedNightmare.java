package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HuntedNightmare extends CardImpl {

    public HuntedNightmare(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{B}");

        this.subtype.add(SubType.NIGHTMARE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // When Hunted Nightmare enters the battlefield, target opponent puts a deathtouch counter on a creature they control.
        Ability ability = new EntersBattlefieldTriggeredAbility(new HuntedNightmareEffect());
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private HuntedNightmare(final HuntedNightmare card) {
        super(card);
    }

    @Override
    public HuntedNightmare copy() {
        return new HuntedNightmare(this);
    }
}

class HuntedNightmareEffect extends OneShotEffect {

    HuntedNightmareEffect() {
        super(Outcome.Benefit);
        staticText = "target opponent puts a deathtouch counter on a creature they control";
    }

    private HuntedNightmareEffect(final HuntedNightmareEffect effect) {
        super(effect);
    }

    @Override
    public HuntedNightmareEffect copy() {
        return new HuntedNightmareEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getFirstTarget());
        if (player == null || game.getBattlefield().countAll(
                StaticFilters.FILTER_PERMANENT_CREATURE, player.getId(), game
        ) == 0) {
            return false;
        }
        Target target = new TargetControlledCreaturePermanent();
        target.setNotTarget(true);
        player.choose(outcome, target, source, game);
        Permanent permanent = game.getPermanent(target.getFirstTarget());
        return permanent != null && permanent.addCounters(CounterType.DEATHTOUCH.createInstance(), player.getId(), source, game);
    }
}
