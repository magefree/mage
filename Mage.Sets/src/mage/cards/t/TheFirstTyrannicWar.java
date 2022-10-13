package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.dynamicvalue.common.LandsYouControlCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class TheFirstTyrannicWar extends CardImpl {

    public TheFirstTyrannicWar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}{U}{R}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I -- You may put a creature card from your hand onto the battlefield. If its mana cost contains {X}, it enters the battlefield with a number of +1/+1 counters on it equal to the number of lands you control.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_I, new TheFirstTyrannicWarFirstEffect());

        // II, III -- Double the number of each kind of counter on target creature you control.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_II, SagaChapter.CHAPTER_III,
                new TheFirstTyrannicWarSecondEffect(), new TargetControlledCreaturePermanent()
        );
        this.addAbility(sagaAbility);
    }

    private TheFirstTyrannicWar(final TheFirstTyrannicWar card) {
        super(card);
    }

    @Override
    public TheFirstTyrannicWar copy() {
        return new TheFirstTyrannicWar(this);
    }
}

class TheFirstTyrannicWarFirstEffect extends OneShotEffect {

    TheFirstTyrannicWarFirstEffect() {
        super(Outcome.Benefit);
        staticText = "you may put a creature card from your hand onto the battlefield. " +
                "If its mana cost contains {X}, it enters the battlefield " +
                "with a number of +1/+1 counters on it equal to the number of lands you control";
    }

    private TheFirstTyrannicWarFirstEffect(final TheFirstTyrannicWarFirstEffect effect) {
        super(effect);
    }

    @Override
    public TheFirstTyrannicWarFirstEffect copy() {
        return new TheFirstTyrannicWarFirstEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetCard target = new TargetCardInHand(0, 1, StaticFilters.FILTER_CARD_CREATURE);
        player.choose(outcome, player.getHand(), target, game);
        Card card = game.getCard(target.getFirstTarget());
        if (card == null) {
            return false;
        }
        if (card.getManaCostSymbols().stream().anyMatch(s -> s.contains("{X}"))) {
            game.addEffect(new TheFirstTyrannicWarReplacementEffect()
                    .setTargetPointer(new FixedTarget(card, game)), source);
        }
        return player.moveCards(card, Zone.BATTLEFIELD, source, game);
    }
}

class TheFirstTyrannicWarReplacementEffect extends ReplacementEffectImpl {

    TheFirstTyrannicWarReplacementEffect() {
        super(Duration.EndOfStep, Outcome.BoostCreature);
    }

    TheFirstTyrannicWarReplacementEffect(TheFirstTyrannicWarReplacementEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getTargetId().equals(getTargetPointer().getFirst(game, source));
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent creature = ((EntersTheBattlefieldEvent) event).getTarget();
        if (creature != null) {
            creature.addCounters(CounterType.P1P1.createInstance(
                    LandsYouControlCount.instance.calculate(game, source, this)
            ), source.getControllerId(), source, game, event.getAppliedEffects());
            discard();
        }
        return false;
    }

    @Override
    public TheFirstTyrannicWarReplacementEffect copy() {
        return new TheFirstTyrannicWarReplacementEffect(this);
    }
}


class TheFirstTyrannicWarSecondEffect extends OneShotEffect {

    TheFirstTyrannicWarSecondEffect() {
        super(Outcome.Benefit);
        staticText = "double the number of each kind of counter on target creature you control";
    }

    private TheFirstTyrannicWarSecondEffect(final TheFirstTyrannicWarSecondEffect effect) {
        super(effect);
    }

    @Override
    public TheFirstTyrannicWarSecondEffect copy() {
        return new TheFirstTyrannicWarSecondEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            return false;
        }
        Set<Counter> counters = permanent
                .getCounters(game)
                .entrySet()
                .stream()
                .map(entry -> CounterType
                        .findByName(entry.getKey())
                        .createInstance(entry.getValue().getCount()))
                .collect(Collectors.toSet());
        if (counters.isEmpty()) {
            return false;
        }
        for (Counter counter : counters) {
            permanent.addCounters(counter, source, game);
        }
        return true;
    }
}
