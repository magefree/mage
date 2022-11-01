package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.dynamicvalue.common.CardTypeAssignment;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInLibrary;
import mage.watchers.Watcher;

import java.util.*;

/**
 * @author TheElk801
 */
public final class HurkylMasterWizard extends CardImpl {

    public HurkylMasterWizard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{U}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // At the beginning of your end step, if you've cast a noncreature spell this turn, reveal the top five cards of your library. For each card type among noncreature spells you've cast this turn, you may put a card of that type from among the revealed cards into your hand. Put the rest on the bottom of your library in a random order.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new HurkylMasterWizardEffect(), TargetController.YOU,
                HurkylMasterWizardCondition.instance, false
        ), new HurkylMasterWizardWatcher());
    }

    private HurkylMasterWizard(final HurkylMasterWizard card) {
        super(card);
    }

    @Override
    public HurkylMasterWizard copy() {
        return new HurkylMasterWizard(this);
    }
}

enum HurkylMasterWizardCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return HurkylMasterWizardWatcher.checkPlayer(source, game);
    }

    @Override
    public String toString() {
        return "if you've cast a noncreature spell this turn";
    }
}

class HurkylMasterWizardEffect extends OneShotEffect {

    HurkylMasterWizardEffect() {
        super(Outcome.Benefit);
        staticText = "reveal the top five cards of your library. For each card type among noncreature spells " +
                "you've cast this turn, you may put a card of that type from among the revealed cards into your hand. " +
                "Put the rest on the bottom of your library in a random order";
    }

    private HurkylMasterWizardEffect(final HurkylMasterWizardEffect effect) {
        super(effect);
    }

    @Override
    public HurkylMasterWizardEffect copy() {
        return new HurkylMasterWizardEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, 5));
        player.revealCards(source, cards, game);
        TargetCard target = new HurkylMasterWizardTarget(source, game);
        player.choose(outcome, cards, target, game);
        Cards toHand = new CardsImpl(target.getTargets());
        player.moveCards(toHand, Zone.HAND, source, game);
        cards.retainZone(Zone.LIBRARY, game);
        player.putCardsOnBottomOfLibrary(cards, game, source, false);
        return true;
    }
}

class HurkylMasterWizardTarget extends TargetCardInLibrary {

    private final CardTypeAssignment cardTypeAssigner;

    HurkylMasterWizardTarget(Ability source, Game game) {
        super(0, Integer.MAX_VALUE, StaticFilters.FILTER_CARD);
        cardTypeAssigner = new CardTypeAssignment(HurkylMasterWizardWatcher.getCardTypes(source, game).toArray(new CardType[]{}));
    }

    private HurkylMasterWizardTarget(final HurkylMasterWizardTarget target) {
        super(target);
        this.cardTypeAssigner = target.cardTypeAssigner;
    }

    @Override
    public HurkylMasterWizardTarget copy() {
        return new HurkylMasterWizardTarget(this);
    }

    @Override
    public boolean canTarget(UUID playerId, UUID id, Ability source, Game game) {
        if (!super.canTarget(playerId, id, source, game)) {
            return false;
        }
        Card card = game.getCard(id);
        if (card == null) {
            return false;
        }
        if (this.getTargets().isEmpty()) {
            return true;
        }
        Cards cards = new CardsImpl(this.getTargets());
        cards.add(card);
        return cardTypeAssigner.getRoleCount(cards, game) >= cards.size();
    }
}

class HurkylMasterWizardWatcher extends Watcher {

    private final Map<UUID, Set<CardType>> map = new HashMap<>();

    HurkylMasterWizardWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.SPELL_CAST) {
            return;
        }
        Spell spell = game.getSpell(event.getTargetId());
        if (spell != null && !spell.isCreature(game)) {
            map.computeIfAbsent(event.getPlayerId(), x -> new HashSet<>()).addAll(spell.getCardType(game));
        }
    }

    @Override
    public void reset() {
        map.clear();
        super.reset();
    }

    static boolean checkPlayer(Ability source, Game game) {
        return game
                .getState()
                .getWatcher(HurkylMasterWizardWatcher.class)
                .map
                .containsKey(source.getControllerId());
    }

    static Set<CardType> getCardTypes(Ability source, Game game) {
        return game
                .getState()
                .getWatcher(HurkylMasterWizardWatcher.class)
                .map
                .getOrDefault(source.getControllerId(), Collections.emptySet());
    }
}
