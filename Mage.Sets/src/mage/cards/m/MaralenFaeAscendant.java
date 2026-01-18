package mage.cards.m;

import java.util.*;

import mage.MageIdentifier;
import mage.MageInt;
import mage.MageObject;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldThisOrAnotherTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.*;
import mage.abilities.keyword.FlyingAbility;
import mage.filter.FilterPermanent;
import mage.constants.SubType;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetOpponent;
import mage.filter.common.FilterControlledPermanent;
import mage.util.CardUtil;
import mage.watchers.Watcher;
import mage.watchers.common.OnceEachTurnCastWatcher;

/**
 *
 * @author Callumvl
 */
public final class MaralenFaeAscendant extends CardImpl {
    public static final FilterPermanent filter = new FilterControlledPermanent("Elf or Faerie");
    static {
        filter.add(Predicates.or(
                SubType.ELF.getPredicate(),
                SubType.FAERIE.getPredicate()
        ));
    }
    public MaralenFaeAscendant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{G}{U}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.FAERIE);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Maralen or another Elf or Faerie you control enters, exile the top two cards of target opponent's library.
        Ability ability = new EntersBattlefieldThisOrAnotherTriggeredAbility(
                new MaralenFaeAscendantEffect(), filter, false, true
        );
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

        // Once each turn, you may cast a spell with mana value less than or equal to the number of Elves and Faeries you control from among cards exiled with Maralen this turn without paying its mana cost.
        Ability castAbility = new SimpleStaticAbility(new MaralenFaeAscendantCastFromExileEffect())
                .setIdentifier(MageIdentifier.OnceEachTurnCastWatcher)
                .addHint(OnceEachTurnCastWatcher.getHint());
        castAbility.addWatcher(new MaralenFaeAscendantWatcher());
        this.addAbility(castAbility, new OnceEachTurnCastWatcher());
    }

    private MaralenFaeAscendant(final MaralenFaeAscendant card) {
        super(card);
    }

    @Override
    public MaralenFaeAscendant copy() {
        return new MaralenFaeAscendant(this);
    }
}

class MaralenFaeAscendantEffect extends OneShotEffect {

    MaralenFaeAscendantEffect() {
        super(Outcome.Exile);
        this.staticText = "exile the top two cards of target opponent's library";
    }

    private MaralenFaeAscendantEffect(final MaralenFaeAscendantEffect effect) {
        super(effect);
    }

    @Override
    public MaralenFaeAscendantEffect copy() {
        return new MaralenFaeAscendantEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));

        if (player == null) {
            return false;
        }
        UUID exileZone = CardUtil.getExileZoneId(
                game,
                source.getSourceId(),
                source.getSourceObject(game).getZoneChangeCounter(game)
        );
        for (Card card : player.getLibrary().getTopCards(game, 2)) {
            player.moveCardsToExile(
                    card,
                    source,
                    game,
                    true,
                    exileZone,
                    CardUtil.getSourceName(game, source)
            );
        }
        return true;
    }
}

class MaralenFaeAscendantCastFromExileEffect extends AsThoughEffectImpl {

    MaralenFaeAscendantCastFromExileEffect() {
        super(AsThoughEffectType.CAST_FROM_NOT_OWN_HAND_ZONE, Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "Once each turn, you may cast a spell with mana value less than or equal to the number of " +
                "Elves and Faeries you control from among cards exiled with {this} this turn " +
                "without paying its mana cost.";
    }

    private MaralenFaeAscendantCastFromExileEffect(final MaralenFaeAscendantCastFromExileEffect effect) {
        super(effect);
    }

    @Override
    public MaralenFaeAscendantCastFromExileEffect copy() {
        return new MaralenFaeAscendantCastFromExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        throw new IllegalArgumentException("Wrong code usage: can't call applies method on empty affectedAbility");
    }

    @Override
    public boolean applies(UUID objectId, Ability affectedAbility, Ability source, Game game, UUID playerId) {
        Player controller = game.getPlayer(source.getControllerId());
        OnceEachTurnCastWatcher watcher = game.getState().getWatcher(OnceEachTurnCastWatcher.class);
        Permanent sourcePermanent = source.getSourcePermanentIfItStillExists(game);
        if (controller == null || sourcePermanent == null || watcher == null) {
            return false;
        }
        // Only applies for the controller of the ability.
        if (!playerId.equals(source.getControllerId())) {
            return false;
        }
        // Has the ability already been used this turn by the player?
        if (watcher.isAbilityUsed(controller.getId(), new MageObjectReference(sourcePermanent, game))) {
            return false;
        }
        MaralenFaeAscendantWatcher exileWatcher =
                game.getState().getWatcher(MaralenFaeAscendantWatcher.class);

        Card card = game.getCard(objectId);
        if (card == null || exileWatcher == null) {
            return false;
        }
        // Only allow spells exiled by Maralen this turn
        UUID exileZone = CardUtil.getExileZoneId(
                game,
                source.getSourceId(),
                source.getSourceObject(game).getZoneChangeCounter(game)
        );
        if (!exileWatcher.isExiledThisTurn(exileZone, card.getId())) {
            return false;
        }
        // Check mana value restriction and allow to play for free
        int maxManaValue = game.getBattlefield().count(MaralenFaeAscendant.filter, controller.getId(), source, game);
        if (card.getManaValue() <= maxManaValue) {
            allowCardToPlayWithoutMana(objectId, source, controller.getId(), game);
            return true;
        }

        return false;
    }

}

class MaralenFaeAscendantWatcher extends Watcher {

    private final Map<UUID, Set<UUID>> exiledThisTurn = new HashMap<>();

    MaralenFaeAscendantWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void reset() {
        super.reset();
        exiledThisTurn.clear();
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.ZONE_CHANGE) {
            return;
        }
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (zEvent.getFromZone() != Zone.LIBRARY || zEvent.getToZone() != Zone.EXILED) {
            return;
        }
        MageObject sourceObject = game.getObject(zEvent.getSourceId());

        UUID exileZone = CardUtil.getExileZoneId(
                game,
                sourceObject.getId(),
                sourceObject.getZoneChangeCounter(game)
        );

        exiledThisTurn
                .computeIfAbsent(exileZone, k -> new HashSet<>())
                .add(zEvent.getTargetId());
    }
    public boolean isExiledThisTurn(UUID exileZone, UUID cardId) {
        Set<UUID> exiledCards = exiledThisTurn.get(exileZone);
        return exiledCards != null && exiledCards.contains(cardId);
    }
}