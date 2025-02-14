package mage.cards.l;

import java.util.Optional;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.abilities.triggers.BeginningOfSecondMainTriggeredAbility;
import mage.cards.Card;
import mage.constants.*;
import mage.abilities.keyword.AfflictAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.DamagedEvent;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInYourGraveyard;
import mage.watchers.Watcher;

/**
 * @author sobiech
 */
public final class LostMonarchOfIfnir extends CardImpl {
    private final static Hint hint = new ConditionHint(LostMonarchOfIfnirCondition.instance, "Player was dealt combat damage by a Zombie this turn");

    private final static FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.ZOMBIE, "Zombies");

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
                new AfflictAbility(3),
                Duration.WhileOnBattlefield,
                filter,
                true
        )));

        final TriggeredAbility ability = new BeginningOfSecondMainTriggeredAbility(
                Zone.BATTLEFIELD,
                TargetController.YOU,
                new LostMonarchOfIfnirEffect(),
                false
        );
        ability.addHint(hint);

        // At the beginning of your second main phase, if a player was dealt combat damage by a Zombie this turn, mill three cards, then you may return a creature card from your graveyard to your hand.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                ability,
                LostMonarchOfIfnirCondition.instance,
                "at the beginning of your second main phase, " +
                "if a player was dealt combat damage by a Zombie this turn, " +
                "mill three cards, then you may return a creature card from your graveyard to your hand"
        ), new LostMonarchOfIfnirWatcher());
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
    private static final FilterCard filter = new FilterCreatureCard();

    LostMonarchOfIfnirEffect() {
        super(Outcome.Benefit);
    }
    private LostMonarchOfIfnirEffect(OneShotEffect effect){
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        new MillCardsControllerEffect(3).apply(game, source);

        final TargetCard target = new TargetCardInYourGraveyard(0, 1, filter, true);
        final Player player = game.getPlayer(source.getControllerId());

        if (player == null) {
            return false;
        }

        if (!player.choose(Outcome.ReturnToHand, target, source, game)) {
            return true;
        }

        final Card card = game.getCard(target.getFirstTarget());
        if (card == null) {
            return true;
        }

        player.moveCards(card, Zone.HAND, source, game);

        return true;
    }

    @Override
    public OneShotEffect copy() {
        return new LostMonarchOfIfnirEffect(this);
    }
}

enum LostMonarchOfIfnirCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        final LostMonarchOfIfnirWatcher watcher = game.getState().getWatcher(LostMonarchOfIfnirWatcher.class);
        return watcher != null && watcher.wasDealtDamage();
    }
}

class LostMonarchOfIfnirWatcher extends Watcher {
    private boolean deltDamage; //by a zombie this turn

     LostMonarchOfIfnirWatcher() {
         super(WatcherScope.GAME);
         this.deltDamage = false;
    }

    @Override
    public void watch(GameEvent event, Game game) {
        Optional.of(event)
                .filter(e -> e.getType() == GameEvent.EventType.DAMAGED_PLAYER)
                .filter(e -> e instanceof DamagedPlayerEvent)
                .map(DamagedPlayerEvent.class::cast)
                .filter(DamagedEvent::isCombatDamage)
                .map(damagedPlayerEvent -> game.getPermanentOrLKIBattlefield(event.getSourceId()))
                .filter(permanent -> permanent.hasSubtype(SubType.ZOMBIE, game))
                .ifPresent(ignored -> this.deltDamage = true);
    }

    @Override
    public void reset() {
        super.reset();
        this.deltDamage = false;
    }

    boolean wasDealtDamage() {
        return this.deltDamage;
    }
}

