package mage.cards.v;

import mage.MageInt;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CopyEffect;
import mage.abilities.effects.common.ExileUntilSourceLeavesEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.cards.Card;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.ModalDoubleFacedCard;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.command.emblems.TibaltCosmicImpostorEmblem;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInExile;
import mage.target.targetpointer.FixedTarget;
import mage.target.targetpointer.FixedTargets;
import mage.util.CardUtil;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class ValkiGodOfLies extends ModalDoubleFacedCard {

    public ValkiGodOfLies(UUID ownerId, CardSetInfo setInfo) {
        super(
                ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.GOD}, "{1}{B}",
                "Tibalt, Cosmic Impostor",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.PLANESWALKER}, new SubType[]{SubType.TIBALT}, "{5}{B}{R}"
        );

        // 1.
        // Valki, God of Lies
        // Legendary Creature - God
        this.getLeftHalfCard().setPT(new MageInt(2), new MageInt(1));

        // When Valki enters the battlefield, each opponent reveals their hand. For each opponent, exile a creature card they revealed this way until Valki leaves the battlefield.
        this.getLeftHalfCard().addAbility(new EntersBattlefieldTriggeredAbility(new ValkiGodOfLiesRevealExileEffect()));

        // X: Choose a creature card exiled with Valki with converted mana cost X. Valki becomes a copy of that card.
        this.getLeftHalfCard().addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new ValkiGodOfLiesCopyExiledEffect(), new ManaCostsImpl<>("{X}")));

        // 2.
        // Tibalt, Cosmic Impostor
        // Legendary Planeswalker — Tibalt
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
        if (controller == null) {
            return false;
        }
        Set<Card> cardsToExile = new LinkedHashSet<>();
        for (UUID opponentId : game.getOpponents(controller.getId())) {
            Player opponent = game.getPlayer(opponentId);
            if (opponent != null && !opponent.getHand().isEmpty()) {
                opponent.revealCards(source, opponent.getHand(), game);
                TargetCard targetToExile = new TargetCard(Zone.HAND, StaticFilters.FILTER_CARD_CREATURE);
                targetToExile.withChooseHint("card to exile");
                targetToExile.setNotTarget(true);
                if (opponent.getHand().count(StaticFilters.FILTER_CARD_CREATURE, game) > 0 &&
                        controller.choose(Outcome.Exile, opponent.getHand(), targetToExile, source, game)) {
                    Card targetedCardToExile = game.getCard(targetToExile.getFirstTarget());
                    if (targetedCardToExile != null
                            && game.getState().getZone(source.getSourceId()) == Zone.BATTLEFIELD) {
                        cardsToExile.add(targetedCardToExile);
                    }
                }
            }
        }
        // exile all cards at one time
        if (cardsToExile.isEmpty()) {
            return true;
        }
        Effect effect = new ExileUntilSourceLeavesEffect(Zone.HAND);
        effect.setTargetPointer(new FixedTargets(cardsToExile, game));
        return effect.apply(game, source);
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
            UUID exileId = CardUtil.getCardExileZoneId(game, source);
            FilterCard filter = new FilterCard();
            filter.add(new ManaValuePredicate(ComparisonType.EQUAL_TO, source.getManaCostsToPay().getX()));
            TargetCardInExile target = new TargetCardInExile(filter, exileId);
            Cards cards = game.getExile().getExileZone(exileId);
            if (cards != null
                    && !cards.isEmpty()
                    && controller.choose(Outcome.Benefit, cards, target, source, game)) {
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
