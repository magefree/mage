package mage.cards.v;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CopyEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.cards.Card;
import mage.cards.ModalDoubleFacesCard;
import mage.constants.*;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.command.emblems.TibaltCosmicImpostorEmblem;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInExile;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

/**
 *
 * @author jeffwadsworth
 */
public final class ValkiGodOfLies extends ModalDoubleFacesCard {

    public ValkiGodOfLies(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.GOD}, "{1}{B}",
                "Tibalt, Cosmic Impostor", new CardType[]{CardType.PLANESWALKER}, new SubType[]{SubType.TIBALT}, "{5}{B}{R}"
        );

        // 1.
        // Valki, God of Lies
        // Legendary Creature - God
        this.getLeftHalfCard().addSuperType(SuperType.LEGENDARY);
        this.getLeftHalfCard().setPT(new MageInt(2), new MageInt(1));

        // When Valki enters the battlefield, each opponent reveals their hand. For each opponent, exile a creature card they revealed this way until Valki leaves the battlefield.
        this.getLeftHalfCard().addAbility(new EntersBattlefieldTriggeredAbility(new ValkiGodOfLiesRevealExileEffect()));

        // X: Choose a creature card exiled with Valki with converted mana cost X. Valki becomes a copy of that card.
        this.getLeftHalfCard().addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new ValkiGodOfLiesCopyExiledEffect(), new ManaCostsImpl<>("{X}")));

        // 2.
        // Tibalt, Cosmic Impostor
        // Legendary Planeswalker — Tibalt
        this.getRightHalfCard().addSuperType(SuperType.LEGENDARY);
        this.getRightHalfCard().setStartingLoyalty(5);

        // As Tibalt enters the battlefield, you get an emblem with “You may play cards exiled with Tibalt, Cosmic Impostor, and you may spend mana as though it were mana of any color to cast those spells.”
        this.getRightHalfCard().addAbility(new AsEntersBattlefieldAbility(new GetEmblemEffect(new TibaltCosmicImpostorEmblem())));

        // +2: Exile the top card of each player’s library.
        this.getRightHalfCard().addAbility(new LoyaltyAbility(new ExileTopCardEachPlayersLibrary(), 2));

        // −3: Exile target artifact or creature.
        LoyaltyAbility loyaltyAbility = new LoyaltyAbility(new ExileTargetArtifactOrCreatureEffect(), -3);
        loyaltyAbility.addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_CREATURE));
        this.getRightHalfCard().addAbility(loyaltyAbility);

        // −8: Exile all cards from all graveyards. Add {R}{R}{R}.
        this.getRightHalfCard().addAbility(new LoyaltyAbility(new ExileAllCardsFromAllGraveyards(), -8));

    }

    private ValkiGodOfLies(final ValkiGodOfLies card) {
        super(card);
    }

    @Override
    public ValkiGodOfLies copy() {
        return new ValkiGodOfLies(this);
    }
}

class ValkiGodOfLiesRevealExileEffect extends OneShotEffect {

    ValkiGodOfLiesRevealExileEffect() {
        super(Outcome.Benefit);
        staticText = "each opponent reveals their hand. For each opponent, exile a creature card they revealed this way until Valki leaves the battlefield.";
    }

    private ValkiGodOfLiesRevealExileEffect(final ValkiGodOfLiesRevealExileEffect effect) {
        super(effect);
    }

    @Override
    public ValkiGodOfLiesRevealExileEffect copy() {
        return new ValkiGodOfLiesRevealExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject Valki = game.getObject(source);
        Set<Card> cardsToExile = new LinkedHashSet<>();
        if (controller != null
                && Valki != null) {
            UUID exileId = CardUtil.getExileZoneId(source.getSourceId().toString() + Valki.getZoneChangeCounter(game), game);
            for (UUID opponentId : game.getOpponents(controller.getId())) {
                Player opponent = game.getPlayer(opponentId);
                if (opponent != null) {
                    opponent.revealCards(source, opponent.getHand(), game);
                    TargetCard targetToExile = new TargetCard(Zone.HAND, StaticFilters.FILTER_CARD_CREATURE);
                    targetToExile.withChooseHint("card to exile");
                    targetToExile.setNotTarget(true);
                    if (controller.choose(Outcome.Exile, opponent.getHand(), targetToExile, game)) {
                        Card targetedCardToExile = game.getCard(targetToExile.getFirstTarget());
                        if (targetedCardToExile != null
                                && game.getState().getZone(source.getSourceId()) == Zone.BATTLEFIELD) {
                            cardsToExile.add(targetedCardToExile);
                        }
                    }
                }
            }
            // exile all cards at one time
            controller.moveCardsToExile(cardsToExile, source, game, true, exileId, Valki.getName());
            game.addDelayedTriggeredAbility(new ValkiGodOfLiesReturnExiledCardAbility(), source);
            return true;
        }
        return false;
    }
}

class ValkiGodOfLiesReturnExiledCardAbility extends DelayedTriggeredAbility {

    public ValkiGodOfLiesReturnExiledCardAbility() {
        super(new ValkiGodOfLiesReturnExiledCardEffect(), Duration.OneUse);
        this.usesStack = false;
        this.setRuleVisible(false);
    }

    public ValkiGodOfLiesReturnExiledCardAbility(final ValkiGodOfLiesReturnExiledCardAbility ability) {
        super(ability);
    }

    @Override
    public ValkiGodOfLiesReturnExiledCardAbility copy() {
        return new ValkiGodOfLiesReturnExiledCardAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getTargetId().equals(this.getSourceId())) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
            if (zEvent.getFromZone() == Zone.BATTLEFIELD) {
                return true;
            }
        }
        return false;
    }
}

class ValkiGodOfLiesReturnExiledCardEffect extends OneShotEffect {

    public ValkiGodOfLiesReturnExiledCardEffect() {
        super(Outcome.Neutral);
        this.staticText = "Return exiled card to its owner's hand";
    }

    public ValkiGodOfLiesReturnExiledCardEffect(final ValkiGodOfLiesReturnExiledCardEffect effect) {
        super(effect);
    }

    @Override
    public ValkiGodOfLiesReturnExiledCardEffect copy() {
        return new ValkiGodOfLiesReturnExiledCardEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject ValkiOnBattlefield = game.getLastKnownInformation(source.getSourceId(), Zone.BATTLEFIELD);
        if (controller != null
                && ValkiOnBattlefield != null) {
            // Valki, God of Lies has changed zone, so make sure to get the exile zone via its last known battlefield state
            UUID exileId = CardUtil.getExileZoneId(source.getSourceId().toString() + (ValkiOnBattlefield.getZoneChangeCounter(game)), game);
            ExileZone exile = game.getExile().getExileZone(exileId);
            Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
            if (exile != null
                    && sourcePermanent != null) {
                controller.moveCards(exile, Zone.HAND, source, game);
                return true;
            }
        }
        return false;
    }
}

class ValkiGodOfLiesCopyExiledEffect extends OneShotEffect {

    public ValkiGodOfLiesCopyExiledEffect() {
        super(Outcome.Benefit);
        this.staticText = "Choose a creature card exiled with Valki with mana value X. Valki becomes a copy of that card.";
    }

    public ValkiGodOfLiesCopyExiledEffect(final ValkiGodOfLiesCopyExiledEffect effect) {
        super(effect);
    }

    @Override
    public ValkiGodOfLiesCopyExiledEffect copy() {
        return new ValkiGodOfLiesCopyExiledEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject Valki = game.getObject(source);
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null
                && Valki != null) {
            UUID exileId = CardUtil.getExileZoneId(source.getSourceId().toString() + (Valki.getZoneChangeCounter(game)), game);
            FilterCard filter = new FilterCard();
            filter.add(new ManaValuePredicate(ComparisonType.EQUAL_TO, source.getManaCostsToPay().getX()));
            TargetCardInExile target = new TargetCardInExile(filter, exileId);
            Cards cards = game.getExile().getExileZone(exileId);
            if (cards != null
                    && !cards.isEmpty()
                    && controller.choose(Outcome.Benefit, cards, target, game)) {
                Card chosenExiledCard = game.getCard(target.getFirstTarget());
                if (chosenExiledCard != null) {
                    ContinuousEffect copyEffect = new CopyEffect(Duration.WhileOnBattlefield, chosenExiledCard.getMainCard(), source.getSourceId());
                    copyEffect.setTargetPointer(new FixedTarget(Valki.getId(), game));
                    game.addEffect(copyEffect, source);
                    return true;
                }
            }
        }
        return false;
    }
}

class ExileTopCardEachPlayersLibrary extends OneShotEffect {

    public ExileTopCardEachPlayersLibrary() {
        super(Outcome.Benefit);
        this.staticText = "Exile the top card of each player's library";
    }

    public ExileTopCardEachPlayersLibrary(final ExileTopCardEachPlayersLibrary effect) {
        super(effect);
    }

    @Override
    public ExileTopCardEachPlayersLibrary copy() {
        return new ExileTopCardEachPlayersLibrary(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject Tibalt = source.getSourceObject(game);
        UUID exileId = CardUtil.getExileZoneId(source.getSourceId().toString(), game);
        Set<Card> cardsToExile = new LinkedHashSet<>();
        if (controller != null
                && Tibalt != null) {
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null
                        && player.getLibrary().hasCards()) {
                    Card topCard = player.getLibrary().getFromTop(game);
                    cardsToExile.add(topCard);
                }
            }
            // exile all cards at one time
            if (!cardsToExile.isEmpty()) {
                return controller.moveCardsToExile(cardsToExile, source, game, true, exileId, Tibalt.getName());
            }
        }
        return false;
    }
}

class ExileTargetArtifactOrCreatureEffect extends OneShotEffect {

    public ExileTargetArtifactOrCreatureEffect() {
        super(Outcome.Detriment);
        this.staticText = "Exile target artifact or creature";
    }

    public ExileTargetArtifactOrCreatureEffect(final ExileTargetArtifactOrCreatureEffect effect) {
        super(effect);
    }

    @Override
    public ExileTargetArtifactOrCreatureEffect copy() {
        return new ExileTargetArtifactOrCreatureEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject Tibalt = source.getSourceObject(game);
        UUID exileId = CardUtil.getExileZoneId(source.getSourceId().toString(), game);
        if (controller != null
                && Tibalt != null) {
            Permanent targetCreatureOrArtifact = game.getPermanent(source.getTargets().getFirstTarget());
            if (targetCreatureOrArtifact != null) {
                controller.moveCardsToExile(targetCreatureOrArtifact, source, game, true, exileId, Tibalt.getName());
                return true;
            }
        }
        return false;
    }
}

class ExileAllCardsFromAllGraveyards extends OneShotEffect {

    public ExileAllCardsFromAllGraveyards() {
        super(Outcome.Benefit);
        this.staticText = "Exile all graveyards. Add {R}{R}{R}";
    }

    public ExileAllCardsFromAllGraveyards(final ExileAllCardsFromAllGraveyards effect) {
        super(effect);
    }

    @Override
    public ExileAllCardsFromAllGraveyards copy() {
        return new ExileAllCardsFromAllGraveyards(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject Tibalt = source.getSourceObject(game);
        Set<Card> cardsToExile = new LinkedHashSet<>();
        UUID exileId = CardUtil.getExileZoneId(source.getSourceId().toString(), game);
        if (controller != null
                && Tibalt != null) {
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    cardsToExile.addAll(player.getGraveyard().getCards(game));
                }
            }
            // exile all cards at one time
            controller.moveCardsToExile(cardsToExile, source, game, true, exileId, Tibalt.getName());
            // add {R}{R}{R}
            controller.getManaPool().addMana(Mana.RedMana(3), game, source);
            return true;
        }
        return false;
    }
}
