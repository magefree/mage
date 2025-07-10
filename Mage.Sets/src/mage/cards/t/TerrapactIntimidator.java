package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.Controllable;
import mage.game.Game;
import mage.game.permanent.token.LanderToken;
import mage.players.Player;
import mage.target.common.TargetOpponent;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TerrapactIntimidator extends CardImpl {

    public TerrapactIntimidator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.KAVU);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // When this creature enters, target opponent may have you create two Lander tokens. If they don't, put two +1/+1 counters on this creature.
        Ability ability = new EntersBattlefieldTriggeredAbility(new TerrapactIntimidatorEffect());
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private TerrapactIntimidator(final TerrapactIntimidator card) {
        super(card);
    }

    @Override
    public TerrapactIntimidator copy() {
        return new TerrapactIntimidator(this);
    }
}

class TerrapactIntimidatorEffect extends OneShotEffect {

    TerrapactIntimidatorEffect() {
        super(Outcome.Benefit);
        staticText = "target opponent may have you create two Lander tokens. " +
                "If they don't, put two +1/+1 counters on this creature";
    }

    private TerrapactIntimidatorEffect(final TerrapactIntimidatorEffect effect) {
        super(effect);
    }

    @Override
    public TerrapactIntimidatorEffect copy() {
        return new TerrapactIntimidatorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (player == null) {
            return false;
        }
        if (player.chooseUse(
                Outcome.Detriment, "Have " + Optional
                        .ofNullable(source)
                        .map(Controllable::getControllerId)
                        .map(game::getPlayer)
                        .map(Player::getLogName)
                        .orElse("them") +
                        " create two Lander tokens?", source, game
        )) {
            return new LanderToken().putOntoBattlefield(2, game, source);
        }
        return Optional
                .ofNullable(source.getSourcePermanentIfItStillExists(game))
                .filter(permanent -> permanent.addCounters(CounterType.P1P1.createInstance(2), source, game))
                .isPresent();
    }
}
