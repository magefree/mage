package mage.cards.f;

import mage.MageIdentifier;
import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalAsThoughEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.asthought.PlayFromNotOwnHandZoneTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetSacrifice;
import mage.target.targetpointer.FixedTargets;
import mage.watchers.Watcher;

import java.util.*;

/**
 * @author TheElk801
 */
public final class FireLordOzai extends CardImpl {

    public FireLordOzai(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever Fire Lord Ozai attacks, you may sacrifice another creature. If you do, add an amount of {R} equal to the sacrificed creature's power. Until end of combat, you don't lose this mana as steps end.
        this.addAbility(new AttacksTriggeredAbility(new FireLordOzaiSacrificeEffect()));

        // {6}: Exile the top card of each opponent's library. Until end of turn, you may play one of those cards without paying its mana cost.
        this.addAbility(new SimpleActivatedAbility(new FireLordOzaiCastEffect(), new GenericManaCost(6))
                .setIdentifier(MageIdentifier.FireLordOzaiAlternateCast), new FireLordOzaiWatcher());
    }

    private FireLordOzai(final FireLordOzai card) {
        super(card);
    }

    @Override
    public FireLordOzai copy() {
        return new FireLordOzai(this);
    }
}

class FireLordOzaiSacrificeEffect extends OneShotEffect {

    FireLordOzaiSacrificeEffect() {
        super(Outcome.Benefit);
        staticText = "you may sacrifice another creature. If you do, add an amount of {R} " +
                "equal to the sacrificed creature's power. Until end of combat, you don't lose this mana as steps end";
    }

    private FireLordOzaiSacrificeEffect(final FireLordOzaiSacrificeEffect effect) {
        super(effect);
    }

    @Override
    public FireLordOzaiSacrificeEffect copy() {
        return new FireLordOzaiSacrificeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetSacrifice target = new TargetSacrifice(0, 1, StaticFilters.FILTER_ANOTHER_CREATURE);
        player.choose(Outcome.Sacrifice, target, source, game);
        Permanent permanent = game.getPermanent(target.getFirstTarget());
        if (permanent == null || !permanent.sacrifice(source, game)) {
            return false;
        }
        int power = permanent.getPower().getValue();
        if (power > 0) {
            player.getManaPool().addMana(Mana.RedMana(power), game, source, Duration.EndOfCombat);
        }
        return true;
    }
}

class FireLordOzaiCastEffect extends OneShotEffect {

    FireLordOzaiCastEffect() {
        super(Outcome.Benefit);
        staticText = "exile the top card of each opponent's library. Until end of turn, " +
                "you may play one of those cards without paying its mana cost";
    }

    private FireLordOzaiCastEffect(final FireLordOzaiCastEffect effect) {
        super(effect);
    }

    @Override
    public FireLordOzaiCastEffect copy() {
        return new FireLordOzaiCastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Cards cards = new CardsImpl();
        for (UUID playerId : game.getOpponents(source.getControllerId())) {
            Optional.ofNullable(playerId)
                    .map(game::getPlayer)
                    .map(Player::getLibrary)
                    .map(library -> library.getFromTop(game))
                    .ifPresent(cards::add);
        }
        if (cards.isEmpty()) {
            return false;
        }
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        player.moveCards(cards, Zone.EXILED, source, game);
        FireLordOzaiWatcher.storeCardInfo(cards, game, source);
        return true;
    }
}

class FireLordOzaiWatcher extends Watcher {

    private static final class FireLordOzaiCondition implements Condition {

        private final UUID token;

        FireLordOzaiCondition(UUID token) {
            this.token = token;
        }

        @Override
        public boolean apply(Game game, Ability source) {
            return !game
                    .getState()
                    .getWatcher(FireLordOzaiWatcher.class)
                    .usedTokens
                    .contains(token);
        }
    }

    private final Set<UUID> usedTokens = new HashSet<>();
    private final Map<UUID, Map<UUID, UUID>> tokenMap = new HashMap<>();

    FireLordOzaiWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        switch (event.getType()) {
            case SPELL_CAST:
            case LAND_PLAYED:
                if (event.hasApprovingIdentifier(MageIdentifier.FireLordOzaiAlternateCast)) {
                    Optional.ofNullable(tokenMap.get(event.getPlayerId()))
                            .map(map -> map.get(event.getTargetId()))
                            .ifPresent(usedTokens::add);
                }
        }
    }

    @Override
    public void reset() {
        super.reset();
        usedTokens.clear();
        tokenMap.clear();
    }

    static void storeCardInfo(Cards cards, Game game, Ability source) {
        FireLordOzaiWatcher watcher = game.getState().getWatcher(FireLordOzaiWatcher.class);
        UUID token = UUID.randomUUID();
        watcher.tokenMap.computeIfAbsent(source.getControllerId(), x -> new HashMap<>());
        for (UUID cardId : cards) {
            watcher.tokenMap.get(source.getControllerId()).put(cardId, token);
        }
        game.addEffect(new ConditionalAsThoughEffect(new PlayFromNotOwnHandZoneTargetEffect(
                Zone.ALL, TargetController.YOU, Duration.EndOfTurn, true, false
        ), new FireLordOzaiCondition(token)).setTargetPointer(new FixedTargets(cards, game)), source);
    }
}
