package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.abilities.keyword.AfflictAbility;
import mage.abilities.triggers.BeginningOfSecondMainTriggeredAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.DamagedEvent;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInYourGraveyard;
import mage.watchers.Watcher;

import java.util.Optional;
import java.util.UUID;

/**
 * @author sobiech
 */
public final class LostMonarchOfIfnir extends CardImpl {

    private final static FilterPermanent filter = new FilterCreaturePermanent(SubType.ZOMBIE, "Zombies");
    private final static Hint hint = new ConditionHint(LostMonarchOfIfnirCondition.instance);

    public LostMonarchOfIfnir(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Afflict 3
        this.addAbility(new AfflictAbility(3));

        // Other Zombies you control have afflict 3.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                new AfflictAbility(3), Duration.WhileOnBattlefield, filter, true
        )));

        // At the beginning of your second main phase, if a player was dealt combat damage by a Zombie this turn, mill three cards, then you may return a creature card from your graveyard to your hand.
        Ability ability = new BeginningOfSecondMainTriggeredAbility(
                new MillCardsControllerEffect(3), false
        ).withInterveningIf(LostMonarchOfIfnirCondition.instance);
        ability.addEffect(new LostMonarchOfIfnirEffect());
        this.addAbility(ability.addHint(hint), new LostMonarchOfIfnirWatcher());
    }

    private LostMonarchOfIfnir(final LostMonarchOfIfnir card) {
        super(card);
    }

    @Override
    public LostMonarchOfIfnir copy() {
        return new LostMonarchOfIfnir(this);
    }
}

class LostMonarchOfIfnirEffect extends OneShotEffect {

    LostMonarchOfIfnirEffect() {
        super(Outcome.Benefit);
        staticText = ", then you may return a creature card from your graveyard to your hand";
    }

    private LostMonarchOfIfnirEffect(LostMonarchOfIfnirEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetCard target = new TargetCardInYourGraveyard(
                0, 1, StaticFilters.FILTER_CARD_CREATURE, true
        );
        player.choose(Outcome.ReturnToHand, target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        return card != null && player.moveCards(card, Zone.HAND, source, game);
    }

    @Override
    public LostMonarchOfIfnirEffect copy() {
        return new LostMonarchOfIfnirEffect(this);
    }
}

enum LostMonarchOfIfnirCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return game
                .getState()
                .getWatcher(LostMonarchOfIfnirWatcher.class)
                .conditionMet();
    }

    @Override
    public String toString() {
        return "a player was dealt combat damage by a Zombie this turn";
    }
}

class LostMonarchOfIfnirWatcher extends Watcher {

    LostMonarchOfIfnirWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        Optional.of(event)
                .filter(e -> e.getType() == GameEvent.EventType.DAMAGED_PLAYER)
                .filter(DamagedPlayerEvent.class::isInstance)
                .map(DamagedPlayerEvent.class::cast)
                .filter(DamagedEvent::isCombatDamage)
                .map(GameEvent::getSourceId)
                .map(game::getPermanentOrLKIBattlefield)
                .filter(permanent -> permanent.hasSubtype(SubType.ZOMBIE, game))
                .ifPresent(ignored -> this.condition = true);
    }
}
