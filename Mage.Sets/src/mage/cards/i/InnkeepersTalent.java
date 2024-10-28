package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.continuous.GainClassAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.ClassLevelAbility;
import mage.abilities.keyword.ClassReminderAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.CounterAnyPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class InnkeepersTalent extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Permanents you control with counters on them");

    static {
        filter.add(CounterAnyPredicate.instance);
    }

    public InnkeepersTalent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}");

        this.subtype.add(SubType.CLASS);

        // (Gain the next level as a sorcery to add its ability.)
        this.addAbility(new ClassReminderAbility());

        // At the beginning of combat on your turn, put a +1/+1 counter on target creature you control.
        Ability ability = new BeginningOfCombatTriggeredAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance()), TargetController.YOU, false);
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);

        // {G}: Level 2
        this.addAbility(new ClassLevelAbility(2, "{G}"));

        // Permanents you control with counters on them have ward {1}.
        this.addAbility(new SimpleStaticAbility(new GainClassAbilitySourceEffect(
                new GainAbilityControlledEffect(new WardAbility(new GenericManaCost(1), false), Duration.WhileOnBattlefield, filter), 2
        )));

        // {3}{G}: Level 3
        this.addAbility(new ClassLevelAbility(3, "{3}{G}"));

        // If you would put one or more counters on a permanent or player, put twice that many of each of those kinds of counters on that permanent or player instead.
        this.addAbility(new SimpleStaticAbility(new GainClassAbilitySourceEffect(new InnkeepersTalentEffect(), 3)));
    }

    private InnkeepersTalent(final InnkeepersTalent card) {
        super(card);
    }

    @Override
    public InnkeepersTalent copy() {
        return new InnkeepersTalent(this);
    }
}

class InnkeepersTalentEffect extends ReplacementEffectImpl {

    InnkeepersTalentEffect() {
        super(Duration.WhileOnBattlefield, Outcome.BoostCreature, false);
        staticText = "if you would put one or more counters on a permanent or player, " +
                "put twice that many of each of those kinds of counters on that permanent or player instead";
    }

    private InnkeepersTalentEffect(final InnkeepersTalentEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmountForCounters(CardUtil.overflowMultiply(event.getAmount(), 2), true);
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ADD_COUNTERS;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Player targetPlayer = game.getPlayer(event.getTargetId());
        Permanent targetPermanent = game.getPermanentEntering(event.getTargetId());
        if (targetPermanent == null) {
            targetPermanent = game.getPermanent(event.getTargetId());
        }

        // on a permanent or player
        if (targetPlayer == null && targetPermanent == null) {
            return false;
        }

        return source.isControlledBy(event.getPlayerId());
    }

    @Override
    public InnkeepersTalentEffect copy() {
        return new InnkeepersTalentEffect(this);
    }
}
