package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class CommunalBrewing extends CardImpl {

    public CommunalBrewing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");

        // When Communal Brewing enters, any number of target opponents each draw a card. Put an ingredient
        // counter on Communal Brewing, then put an ingredient counter on it for each card drawn this way.
        Ability ability = new EntersBattlefieldTriggeredAbility(new CommunalBrewingEffect());
        ability.addTarget(new TargetOpponent(0, Integer.MAX_VALUE, false));
        this.addAbility(ability);

        // Whenever you cast a creature spell, that creature enters with X additional +1/+1
        // counters on it, where X is the number of ingredient counters on Communal Brewing.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new CommunalBrewingCountersEffect(),
                StaticFilters.FILTER_SPELL_A_CREATURE,
                false, SetTargetPointer.SPELL
        ));
    }

    private CommunalBrewing(final CommunalBrewing card) {
        super(card);
    }

    @Override
    public CommunalBrewing copy() {
        return new CommunalBrewing(this);
    }
}

class CommunalBrewingEffect extends OneShotEffect {

    CommunalBrewingEffect() {
        super(Outcome.Benefit);
        staticText = "any number of target opponents each draw a card. Put an ingredient counter " +
                "on {this}, then put an ingredient counter on it for each card drawn this way";
    }

    private CommunalBrewingEffect(final CommunalBrewingEffect effect) {
        super(effect);
    }

    @Override
    public CommunalBrewingEffect copy() {
        return new CommunalBrewingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int count = 0;
        for (UUID targetId : getTargetPointer().getTargets(game, source)) {
            Player opponent = game.getPlayer(targetId);
            if (opponent == null) {
                continue;
            }
            count += opponent.drawCards(1, source, game); // Known issue with Teferi's Ageless Insight. See #12616
        }
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null) {
            return false;
        }
        return permanent.addCounters(CounterType.INGREDIENT.createInstance(count + 1), source, game);
    }
}

class CommunalBrewingCountersEffect extends ReplacementEffectImpl {

    CommunalBrewingCountersEffect() {
        super(Duration.EndOfTurn, Outcome.BoostCreature);
        this.staticText = "that creature enters with X additional +1/+1 counters on it, where X is the number of ingredient counters on {this}";
    }

    private CommunalBrewingCountersEffect(final CommunalBrewingCountersEffect effect) {
        super(effect);
    }

    @Override
    public CommunalBrewingCountersEffect copy() {
        return new CommunalBrewingCountersEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Spell spell = game.getSpellOrLKIStack(getTargetPointer().getFirst(game, source));
        return spell != null && event.getTargetId().equals(spell.getSourceId());
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        Permanent creature = ((EntersTheBattlefieldEvent) event).getTarget();
        if (permanent == null || creature == null) {
            return false;
        }
        creature.addCounters(CounterType.P1P1.createInstance(
                permanent.getCounters(game).getCount(CounterType.INGREDIENT)
        ), source.getControllerId(), source, game, event.getAppliedEffects());
        return false; // Must return false, otherwise creature doesn't enter battlefield
    }
}
