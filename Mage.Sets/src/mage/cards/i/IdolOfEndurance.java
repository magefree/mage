package mage.cards.i;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.OnLeaveReturnExiledAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.util.CardUtil;
import mage.watchers.Watcher;

import java.util.Objects;
import java.util.UUID;

/**
 *
 * @author htrajan
 */
public final class IdolOfEndurance extends CardImpl {

    public IdolOfEndurance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{W}");

        // When Idol of Endurance enters the battlefield, exile all creature cards with converted mana cost 3 or less from your graveyard until Idol of Endurance leaves the battlefield.
        Ability ability = new EntersBattlefieldTriggeredAbility(new IdolOfEnduranceExileEffect());
        ability.addEffect(new CreateDelayedTriggeredAbilityEffect(new OnLeaveReturnExiledAbility(Zone.GRAVEYARD)));
        this.addAbility(ability);

        // {1}{W}, {T}: Until end of turn, you may cast a creature spell from among the cards exiled with Idol of Endurance without paying its mana cost.
    }

    private IdolOfEndurance(final IdolOfEndurance card) {
        super(card);
    }

    @Override
    public IdolOfEndurance copy() {
        return new IdolOfEndurance(this);
    }
}

class IdolOfEnduranceExileEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCreatureCard();

    static {
        filter.add(new ConvertedManaCostPredicate(ComparisonType.FEWER_THAN, 4));
    }

    public IdolOfEnduranceExileEffect() {
        super(Outcome.Exile);
        staticText = "exile all creature cards with converted mana cost 3 or less from your graveyard until {this} leaves the battlefield";
    }

    @Override
    public IdolOfEnduranceExileEffect copy() {
        return new IdolOfEnduranceExileEffect();
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            controller.getGraveyard().stream()
                .map(game::getCard)
                .filter(Objects::nonNull)
                .filter(card -> filter.match(card, game))
                .forEach(card -> controller.moveCards(card, Zone.EXILED, source, game));
            return true;
        }
        return false;
    }
}

class IdolOfEnduranceCastFromExileEffect extends AsThoughEffectImpl {

    private final Ability exileZoneSource;

    IdolOfEnduranceCastFromExileEffect(Ability exileZoneSource) {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.Custom, Outcome.Benefit);
        staticText = "you may cast a creature spell from among the cards exiled with {this} without paying its mana cost";
        this.exileZoneSource = exileZoneSource;
    }

    private IdolOfEnduranceCastFromExileEffect(IdolOfEnduranceCastFromExileEffect effect) {
        super(effect);
        this.exileZoneSource = effect.exileZoneSource;
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        ExileZone exileZone = CardUtil.getExileIfPossible(game, exileZoneSource);
        Card card = game.getCard(objectId);
        IdolOfEnduranceWatcher watcher = game.getState().getWatcher(IdolOfEnduranceWatcher.class, source.getId());
        if (card != null && watcher != null) {
            if (!watcher.usedIdolOfEnduranceExileCast() && exileZone.getCards(StaticFilters.FILTER_CARD_CREATURE, game).contains(card)) {
                allowCardToPlayWithoutMana(objectId, source, affectedControllerId, game);
                watcher.setUsedIdolOfEnduranceExileCast(true);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public IdolOfEnduranceCastFromExileEffect copy() {
        return new IdolOfEnduranceCastFromExileEffect(this);
    }
}

class IdolOfEnduranceResetWatcherEffect extends OneShotEffect {

    IdolOfEnduranceResetWatcherEffect() {
        super(Outcome.Benefit);
        staticText = "";
    }

    private IdolOfEnduranceResetWatcherEffect(IdolOfEnduranceResetWatcherEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        IdolOfEnduranceWatcher watcher = game.getState().getWatcher(IdolOfEnduranceWatcher.class, source.getId());
        if (watcher != null) {
            watcher.reset();
            return true;
        }
        return false;
    }

    @Override
    public IdolOfEnduranceResetWatcherEffect copy() {
        return new IdolOfEnduranceResetWatcherEffect(this);
    }
}

class IdolOfEnduranceWatcher extends Watcher {

    private final Ability exileZoneSource;
    private boolean usedIdolOfEnduranceExileCast;

    public IdolOfEnduranceWatcher(Ability exileZoneSource) {
        super(WatcherScope.PLAYER);
        this.exileZoneSource = exileZoneSource;
    }

    @Override
    public void watch(GameEvent event, Game game) {

    }

    public boolean usedIdolOfEnduranceExileCast() {
        return usedIdolOfEnduranceExileCast;
    }

    public void setUsedIdolOfEnduranceExileCast(boolean usedIdolOfEnduranceExileCast) {
        this.usedIdolOfEnduranceExileCast = usedIdolOfEnduranceExileCast;
    }

    @Override
    public void reset() {
        super.reset();
        usedIdolOfEnduranceExileCast = false;
    }
}