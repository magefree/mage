package mage.cards.c;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.CaseAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SolvedSourceCondition;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.hint.Hint;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInExile;
import mage.target.common.TargetOpponentsCreaturePermanent;
import mage.util.CardUtil;
import mage.watchers.Watcher;

/**
 * Case of the Burning Masks {1}{R}{R}
 * Enchantment - Case
 * When this Case enters the battlefield, it deals 3 damage to target creature an opponent controls.
 * To solve -- Three or more sources you controlled dealt damage this turn.
 * Solved -- Sacrifice this Case: Exile the top three cards of your library. Choose one of them. You may play that card this turn.
 *
 * @author DominionSpy
 */
public final class CaseOfTheBurningMasks extends CardImpl {

    public CaseOfTheBurningMasks(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}{R}");

        this.subtype.add(SubType.CASE);

        // When this Case enters the battlefield, it deals 3 damage to target creature an opponent controls.
        Ability initialAbility = new EntersBattlefieldTriggeredAbility(
                new DamageTargetEffect(3, "it"));
        initialAbility.addTarget(new TargetOpponentsCreaturePermanent());
        initialAbility.addTarget(new TargetOpponentsCreaturePermanent());
        // To solve -- Three or more sources you controlled dealt damage this turn.
        Condition toSolveCondition = new CaseOfTheBurningMasksCondition();
        // Solved -- Sacrifice this Case: Exile the top three cards of your library. Choose one of them. You may play that card this turn.
        Ability solvedAbility = new ConditionalActivatedAbility(new CaseOfTheBurningMasksEffect(),
                new SacrificeSourceCost(), SolvedSourceCondition.SOLVED, false);

        this.addAbility(new CaseAbility(initialAbility, toSolveCondition, solvedAbility)
                .addHint(CaseOfTheBurningMasksHint.instance),
                new CaseOfTheBurningMasksWatcher());
    }

    private CaseOfTheBurningMasks(final CaseOfTheBurningMasks card) {
        super(card);
    }

    @Override
    public CaseOfTheBurningMasks copy() {
        return new CaseOfTheBurningMasks(this);
    }
}

class CaseOfTheBurningMasksCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        CaseOfTheBurningMasksWatcher watcher = game.getState().getWatcher(CaseOfTheBurningMasksWatcher.class);
        return watcher != null && watcher.damagingCountByController(source.getControllerId()) >= 3;
    }

    @Override
    public String toString() {
        return "Three or more sources you controlled dealt damage this turn";
    }
}

enum CaseOfTheBurningMasksHint implements Hint {
    instance;

    @Override
    public CaseOfTheBurningMasksHint copy() {
        return this;
    }

    @Override
    public String getText(Game game, Ability ability) {
        Permanent permanent = game.getPermanent(ability.getSourceId());
        if (permanent == null) {
            return "";
        }
        if (permanent.isSolved()) {
            return "Case is solved";
        }
        int sources = game.getState()
                .getWatcher(CaseOfTheBurningMasksWatcher.class)
                .damagingCountByController(ability.getControllerId());
        StringBuilder sb = new StringBuilder("Case is unsolved. Sources that dealt damage this turn: ");
        sb.append(sources);
        sb.append(" (need 3).");
        if (sources > 2 && game.isActivePlayer(ability.getControllerId())) {
            sb.append(" Case will be solved at the end step.");
        }
        return sb.toString();
    }
}

class CaseOfTheBurningMasksWatcher extends Watcher {

    private final Map<UUID, Set<MageObjectReference>> damagingObjects;

    CaseOfTheBurningMasksWatcher() {
        super(WatcherScope.GAME);
        this.damagingObjects = new HashMap<>(0);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        switch (event.getType()) {
            case DAMAGED_PERMANENT:
            case DAMAGED_PLAYER: {
                MageObjectReference damageSourceRef = new MageObjectReference(event.getSourceId(), game);
                Set<MageObjectReference> mors = damagingObjects.getOrDefault(game.getControllerId(event.getSourceId()), new HashSet<>());
                mors.add(damageSourceRef);
                damagingObjects.put(game.getControllerId(event.getSourceId()), mors);
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        damagingObjects.clear();
    }

    public int damagingCountByController(UUID controllerId) {
        return damagingObjects.getOrDefault(controllerId, Collections.emptySet()).size();
    }
}

class CaseOfTheBurningMasksEffect extends OneShotEffect {

    CaseOfTheBurningMasksEffect() {
        super(Outcome.Benefit);
        staticText = "Exile the top three cards of your library. Choose one of them. You may play that card this turn.";
    }

    private CaseOfTheBurningMasksEffect(final CaseOfTheBurningMasksEffect effect) {
        super(effect);
    }

    @Override
    public CaseOfTheBurningMasksEffect copy() {
        return new CaseOfTheBurningMasksEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, 3));
        player.moveCards(cards, Zone.EXILED, source, game);
        cards.retainZone(Zone.EXILED, game);

        Card card;
        switch (cards.size()) {
            case 0:
                return false;
            case 1:
                card = cards.getRandom(game);
                break;
            default:
                TargetCard target = new TargetCardInExile(StaticFilters.FILTER_CARD);
                target.withNotTarget(true);
                player.choose(outcome, cards, target, source, game);
                card = game.getCard(target.getFirstTarget());
        }
        if (card != null) {
            CardUtil.makeCardPlayable(game, source, card, Duration.EndOfTurn, false);
        }
        return true;
    }
}
