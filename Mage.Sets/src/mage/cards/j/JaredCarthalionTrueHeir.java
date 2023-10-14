package mage.cards.j;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.effects.common.BecomesMonarchTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class JaredCarthalionTrueHeir extends CardImpl {

    public JaredCarthalionTrueHeir(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{G}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Jared Carthalion, True Heir enters the battlefield, target opponent becomes the monarch. You can't become the monarch this turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(new BecomesMonarchTargetEffect()
                .setText("target opponent becomes the monarch"));
        ability.addEffect(new JaredCarthalionTrueHeirMonarchEffect());
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

        // If damage would be dealt to Jared Carthalion while you're the monarch, prevent that damage and put that many +1/+1 counters on it.
        this.addAbility(new SimpleStaticAbility(new JaredCarthalionTrueHeirPreventionEffect()));
    }

    private JaredCarthalionTrueHeir(final JaredCarthalionTrueHeir card) {
        super(card);
    }

    @Override
    public JaredCarthalionTrueHeir copy() {
        return new JaredCarthalionTrueHeir(this);
    }
}

class JaredCarthalionTrueHeirMonarchEffect extends ContinuousRuleModifyingEffectImpl {

    JaredCarthalionTrueHeirMonarchEffect() {
        super(Duration.EndOfTurn, Outcome.Detriment, false, false);
        staticText = "You can't become the monarch this turn";
    }

    private JaredCarthalionTrueHeirMonarchEffect(final JaredCarthalionTrueHeirMonarchEffect effect) {
        super(effect);
    }

    @Override
    public JaredCarthalionTrueHeirMonarchEffect copy() {
        return new JaredCarthalionTrueHeirMonarchEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.BECOME_MONARCH;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return source.isControlledBy(event.getPlayerId());
    }
}

class JaredCarthalionTrueHeirPreventionEffect extends PreventionEffectImpl {

    JaredCarthalionTrueHeirPreventionEffect() {
        super(Duration.WhileOnBattlefield, Integer.MAX_VALUE, false, false);
        staticText = "if damage would be dealt to {this} while you're the monarch, " +
                "prevent that damage and put that many +1/+1 counters on it";
    }

    private JaredCarthalionTrueHeirPreventionEffect(final JaredCarthalionTrueHeirPreventionEffect effect) {
        super(effect);
    }

    @Override
    public JaredCarthalionTrueHeirPreventionEffect copy() {
        return new JaredCarthalionTrueHeirPreventionEffect(this);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return super.applies(event, source, game)
                && event.getTargetId().equals(source.getSourceId())
                && source.isControlledBy(game.getMonarchId());
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            permanent.addCounters(CounterType.P1P1.createInstance(event.getAmount()), source.getControllerId(), source, game);
        }
        return super.replaceEvent(event, source, game);
    }
}
