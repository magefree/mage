package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.RemoveCounterCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.*;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.filter.predicate.permanent.CounterAnyPredicate;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author htrajan
 */
public final class TayamLuminousEnigma extends CardImpl {

    public TayamLuminousEnigma(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{B}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.NIGHTMARE);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Each other creature you control enters the battlefield with an additional vigilance counter on it.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new TayamLuminousEnigmaReplacementEffect()));

        // {3}, Remove three counters from among creatures you control: Put the top three cards of your library into your graveyard, then return a permanent card with converted mana cost 3 or less from your graveyard to the battlefield.
        MillCardsControllerEffect millEffect = new MillCardsControllerEffect(3);
        millEffect.concatBy(".");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, millEffect, new GenericManaCost(3));
        ability.addCost(new TayamLuminousEnigmaCost());
        TayamLuminousEnigmaEffect effect = new TayamLuminousEnigmaEffect();
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private TayamLuminousEnigma(final TayamLuminousEnigma card) {
        super(card);
    }

    @Override
    public TayamLuminousEnigma copy() {
        return new TayamLuminousEnigma(this);
    }
}

class TayamLuminousEnigmaCost extends RemoveCounterCost {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("a creature with a counter among creatures you control");

    static {
        filter.add(CounterAnyPredicate.instance);
    }

    public TayamLuminousEnigmaCost() {
        super(new TargetPermanent(1, 1, filter, true), null, 3);
    }

    public TayamLuminousEnigmaCost(TayamLuminousEnigmaCost cost) {
        super(cost);
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        paid = false;
        int countersRemoved = 0;
        Player controller = game.getPlayer(controllerId);
        for (int i = 0; i < countersToRemove; i++) {
            if (target.choose(Outcome.UnboostCreature, controllerId, source.getSourceId(), source, game)) {
                Permanent permanent = game.getPermanent(target.getFirstTarget());
                if (permanent != null) {
                    if (!permanent.getCounters(game).isEmpty()) {
                        String counterName = null;
                        if (permanent.getCounters(game).size() > 1) {
                            Choice choice = new ChoiceImpl(true);
                            Set<String> choices = new LinkedHashSet<>();
                            for (Counter counter : permanent.getCounters(game).values()) {
                                if (permanent.getCounters(game).getCount(counter.getName()) > 0) {
                                    choices.add(counter.getName());
                                }
                            }
                            choice.setChoices(choices);
                            choice.setMessage("Choose a counter to remove from " + permanent.getLogName());
                            if (!controller.choose(Outcome.UnboostCreature, choice, game)) {
                                return false;
                            }
                            counterName = choice.getChoice();
                        } else {
                            for (Counter counter : permanent.getCounters(game).values()) {
                                if (counter.getCount() > 0) {
                                    counterName = counter.getName();
                                }
                            }
                        }
                        if (counterName != null) {
                            permanent.removeCounters(counterName, 1, source, game);
                            target.clearChosen();
                            if (!game.isSimulation()) {
                                game.informPlayers(new StringBuilder(controller.getLogName())
                                        .append(" removes a ")
                                        .append(counterName).append(" counter from ")
                                        .append(permanent.getName()).toString());
                            }
                            countersRemoved++;
                            if (countersRemoved == countersToRemove) {
                                paid = true;
                                break;
                            }
                        }
                    }
                }
            } else {
                break;
            }
        }
        return paid;
    }

    @Override
    public TayamLuminousEnigmaCost copy() {
        return new TayamLuminousEnigmaCost(this);
    }

    @Override
    public String getText() {
        return "Remove three counters from among creatures you control";
    }
}

class TayamLuminousEnigmaEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterPermanentCard("permanent card in your graveyard with mana value 3 or less");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 4));
    }

    TayamLuminousEnigmaEffect() {
        super(Outcome.Benefit);
        staticText = ", then return a permanent card with mana value 3 or less from your graveyard to the battlefield";
    }

    private TayamLuminousEnigmaEffect(TayamLuminousEnigmaEffect effect) {
        super(effect);
    }

    @Override
    public TayamLuminousEnigmaEffect copy() {
        return new TayamLuminousEnigmaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null || player.getGraveyard().count(filter, game) == 0) {
            return false;
        }
        TargetCard target = new TargetCardInYourGraveyard(filter);
        target.setNotTarget(true);
        if (!player.choose(outcome, player.getGraveyard(), target, source, game)) {
            return false;
        }
        return player.moveCards(game.getCard(target.getFirstTarget()), Zone.BATTLEFIELD, source, game);
    }
}

class TayamLuminousEnigmaReplacementEffect extends ReplacementEffectImpl {

    TayamLuminousEnigmaReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.BoostCreature);
        staticText = "Each other creature you control enters the battlefield with an additional vigilance counter on it.";
    }

    private TayamLuminousEnigmaReplacementEffect(final TayamLuminousEnigmaReplacementEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent creature = ((EntersTheBattlefieldEvent) event).getTarget();
        return creature != null
                && creature.isCreature(game)
                && !source.getSourceId().equals(creature.getId())
                && creature.isControlledBy(source.getControllerId());
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent creature = ((EntersTheBattlefieldEvent) event).getTarget();
        if (creature != null) {
            creature.addCounters(CounterType.VIGILANCE.createInstance(), source.getControllerId(), source, game, event.getAppliedEffects());
        }
        return false;
    }

    @Override
    public TayamLuminousEnigmaReplacementEffect copy() {
        return new TayamLuminousEnigmaReplacementEffect(this);
    }
}