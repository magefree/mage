package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.counters.Counter;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetOpponent;

/**
 *
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
                new SuncleanserRemoveCountersEffect(false), false
        );
        ability.addEffect(new SuncleanserPreventCountersEffect(false));
        ability.addTarget(new TargetCreaturePermanent());

        // • Target opponent loses all counters. That player can't get counters for as long as Suncleanser remains on the battlefield.
        Mode mode = new Mode(new SuncleanserRemoveCountersEffect(true));
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

class SuncleanserRemoveCountersEffect extends OneShotEffect {

    public SuncleanserRemoveCountersEffect(boolean player) {
        super(Outcome.Benefit);
        if (player) {
            staticText = "Target opponent loses all counters";
        } else {
            staticText = "Remove all counters from target creature";
        }
    }

    public SuncleanserRemoveCountersEffect(SuncleanserRemoveCountersEffect effect) {
        super(effect);
    }

    @Override
    public SuncleanserRemoveCountersEffect copy() {
        return new SuncleanserRemoveCountersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent != null) {
            for (Counter counter : permanent.getCounters(game).copy().values()) { // copy to prevent ConcurrentModificationException
                permanent.removeCounters(counter, source, game);
            }
            return true;
        }
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (player != null) {
            for (Counter counter : player.getCounters().copy().values()) { // copy to prevent ConcurrentModificationException
                player.removeCounters(counter.getName(), counter.getCount(), source, game);
            }
            return true;
        }
        return false;
    }

}

class SuncleanserPreventCountersEffect extends ContinuousRuleModifyingEffectImpl {

    public SuncleanserPreventCountersEffect(boolean player) {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        if (player) {
            staticText = "That player can't get counters for as long as {this} remains on the battlefield.";
        } else {
            staticText = "It can't have counters put on it for as long as {this} remains on the battlefield";
        }
    }

    public SuncleanserPreventCountersEffect(final SuncleanserPreventCountersEffect effect) {
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
