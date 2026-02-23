package mage.cards.s;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.StormAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.players.Player;
import mage.util.CardUtil;
import mage.util.RandomUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StormOfMemories extends CardImpl {

    public StormOfMemories(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}{R}{R}");

        // Storm
        this.addAbility(new StormAbility());

        // Exile an instant or sorcery card with mana value 3 or less from your graveyard at random. You may cast it without paying its mana cost. If that spell would be put into a graveyard, exile it instead.
        this.getSpellAbility().addEffect(new StormOfMemoriesEffect());
    }

    private StormOfMemories(final StormOfMemories card) {
        super(card);
    }

    @Override
    public StormOfMemories copy() {
        return new StormOfMemories(this);
    }
}

class StormOfMemoriesEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterInstantOrSorceryCard();

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 4));
    }

    StormOfMemoriesEffect() {
        super(Outcome.Benefit);
        staticText = "exile an instant or sorcery card with mana value 3 or less from your graveyard at random. " +
                "You may cast it without paying its mana cost. If that spell would be put into a graveyard, exile it instead.";
    }

    private StormOfMemoriesEffect(final StormOfMemoriesEffect effect) {
        super(effect);
    }

    @Override
    public StormOfMemoriesEffect copy() {
        return new StormOfMemoriesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Card card = RandomUtil.randomFromCollection(player.getGraveyard().getCards(filter, game));
        return card != null && CardUtil.castSpellWithAttributesForFree(
                player, source, game, new CardsImpl(card),
                StaticFilters.FILTER_CARD, StormOfMemoriesTracker.instance
        );
    }
}

enum StormOfMemoriesTracker implements CardUtil.SpellCastTracker {
    instance;

    @Override
    public boolean checkCard(Card card, Game game) {
        return true;
    }

    @Override
    public void addCard(Card card, Ability source, Game game) {
        game.addEffect(new StormOfMemoriesReplacementEffect(card, game), source);
    }
}

class StormOfMemoriesReplacementEffect extends ReplacementEffectImpl {

    private final MageObjectReference mor;

    StormOfMemoriesReplacementEffect(Card card, Game game) {
        super(Duration.EndOfTurn, Outcome.Exile);
        this.mor = new MageObjectReference(card.getMainCard(), game);
    }

    private StormOfMemoriesReplacementEffect(final StormOfMemoriesReplacementEffect effect) {
        super(effect);
        this.mor = effect.mor;
    }

    @Override
    public StormOfMemoriesReplacementEffect copy() {
        return new StormOfMemoriesReplacementEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        Card card = mor.getCard(game);
        return controller != null
                && card != null
                && controller.moveCards(card, Zone.EXILED, source, game);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        return zEvent.getToZone() == Zone.GRAVEYARD
                && zEvent.getTargetId().equals(mor.getSourceId());
    }
}
