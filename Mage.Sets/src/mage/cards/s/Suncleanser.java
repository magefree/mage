package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.RemoveAllCountersPermanentTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Suncleanser extends CardImpl {

    public Suncleanser(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // When Suncleanser enters the battlefield, choose one —
        // • Remove all counters from target creature. It can't have counters put on it for as long as Suncleanser remains on the battlefield.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new RemoveAllCountersPermanentTargetEffect(), false
        );
        ability.addEffect(new SuncleanserPreventCountersEffect(false));
        ability.addTarget(new TargetCreaturePermanent());

        // • Target opponent loses all counters. That player can't get counters for as long as Suncleanser remains on the battlefield.
        Mode mode = new Mode(new SuncleanserRemoveCountersPlayerEffect());
        mode.addEffect(new SuncleanserPreventCountersEffect(true));
        mode.addTarget(new TargetOpponent());
        ability.addMode(mode);
        this.addAbility(ability);
    }

    private Suncleanser(final Suncleanser card) {
        super(card);
    }

    @Override
    public Suncleanser copy() {
        return new Suncleanser(this);
    }
}

class SuncleanserRemoveCountersPlayerEffect extends OneShotEffect {

    SuncleanserRemoveCountersPlayerEffect() {
        super(Outcome.Detriment);
        staticText = "Target opponent loses all counters";
    }

    private SuncleanserRemoveCountersPlayerEffect(final SuncleanserRemoveCountersPlayerEffect effect) {
        super(effect);
    }

    @Override
    public SuncleanserRemoveCountersPlayerEffect copy() {
        return new SuncleanserRemoveCountersPlayerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (player == null) {
            return false;
        }
        player.loseAllCounters(source, game);
        return true;
    }
}

class SuncleanserPreventCountersEffect extends ContinuousRuleModifyingEffectImpl {

    SuncleanserPreventCountersEffect(boolean player) {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        if (player) {
            staticText = "That player can't get counters for as long as {this} remains on the battlefield.";
        } else {
            staticText = "It can't have counters put on it for as long as {this} remains on the battlefield";
        }
    }

    private SuncleanserPreventCountersEffect(final SuncleanserPreventCountersEffect effect) {
        super(effect);
    }

    @Override
    public SuncleanserPreventCountersEffect copy() {
        return new SuncleanserPreventCountersEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ADD_COUNTERS;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!source.getFirstTarget().equals(event.getTargetId())) {
            return false;
        }
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent == null) {
            discard();
            return false;
        }
        return true;
    }
}
