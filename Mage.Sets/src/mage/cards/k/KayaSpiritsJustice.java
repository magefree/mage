package mage.cards.k;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CopyEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeBatchEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentCard;
import mage.game.permanent.token.WhiteBlackSpiritToken;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInExile;
import mage.target.common.TargetCardInGraveyard;
import mage.target.targetadjustment.TargetAdjuster;
import mage.target.targetpointer.EachTargetPointer;
import mage.target.targetpointer.FixedTargets;
import mage.util.CardUtil;
import mage.util.functions.CopyApplier;

/**
 *
 * @author DominionSpy
 */
public final class KayaSpiritsJustice extends CardImpl {

    public KayaSpiritsJustice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{W}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.KAYA);
        this.setStartingLoyalty(3);

        // Whenever one or more creatures you control and/or creature cards in your graveyard are put into exile, you may choose a creature card from among them.
        // Until end of turn, target token you control becomes a copy of it, except it has flying.
        Ability ability = new KayaSpiritsJusticeTriggeredAbility();
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_TOKEN));
        this.addAbility(ability);

        // +2: Surveil 2, then exile a card from a graveyard.
        ability = new LoyaltyAbility(new SurveilEffect(2, false), 2);
        ability.addEffect(new KayaSpiritsJusticeExileEffect().concatBy(", then"));
        this.addAbility(ability);
        // +1: Create a 1/1 white and black Spirit creature token with flying.
        ability = new LoyaltyAbility(new CreateTokenEffect(new WhiteBlackSpiritToken()), 1);
        this.addAbility(ability);
        // -2: Exile target creature you control. For each other player, exile up to one target creature that player controls.
        ability = new LoyaltyAbility(new ExileTargetEffect()
                .setText("exile target creature you control. For each other player, " +
                        "exile up to one target creature that player controls")
                .setTargetPointer(new EachTargetPointer()), -2);
        ability.setTargetAdjuster(KayaSpiritsJusticeAdjuster.instance);
        this.addAbility(ability);
    }

    private KayaSpiritsJustice(final KayaSpiritsJustice card) {
        super(card);
    }

    @Override
    public KayaSpiritsJustice copy() {
        return new KayaSpiritsJustice(this);
    }
}

class KayaSpiritsJusticeTriggeredAbility extends TriggeredAbilityImpl {

    KayaSpiritsJusticeTriggeredAbility() {
        super(Zone.BATTLEFIELD, new KayaSpiritsJusticeCopyEffect(), false);
        setTriggerPhrase("Whenever one or more creatures you control and/or creature cards in your graveyard are put into exile, " +
                "you may choose a creature card from among them. Until end of turn, target token you control becomes a copy of it, " +
                "except it has flying.");
    }

    private KayaSpiritsJusticeTriggeredAbility(final KayaSpiritsJusticeTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public KayaSpiritsJusticeTriggeredAbility copy() {
        return new KayaSpiritsJusticeTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE_BATCH;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeBatchEvent zEvent = (ZoneChangeBatchEvent) event;

        Set<Card> battlefieldCards = zEvent.getEvents()
                .stream()
                .filter(e -> e.getFromZone() == Zone.BATTLEFIELD)
                .filter(e -> e.getToZone() == Zone.EXILED)
                .map(ZoneChangeEvent::getTargetId)
                .filter(Objects::nonNull)
                .map(game::getCard)
                .filter(Objects::nonNull)
                .filter(card -> {
                    Permanent permanent = game.getPermanentOrLKIBattlefield(card.getId());
                    return StaticFilters.FILTER_PERMANENT_CREATURE
                            .match(permanent, getControllerId(), this, game);
                })
                .collect(Collectors.toSet());

        Set<Card> graveyardCards = zEvent.getEvents()
                .stream()
                .filter(e -> e.getFromZone() == Zone.GRAVEYARD)
                .filter(e -> e.getToZone() == Zone.EXILED)
                .map(ZoneChangeEvent::getTargetId)
                .filter(Objects::nonNull)
                .map(game::getCard)
                .filter(Objects::nonNull)
                .filter(card -> StaticFilters.FILTER_CARD_CREATURE
                        .match(card, getControllerId(), this, game))
                .collect(Collectors.toSet());

        Set<Card> cards = new HashSet<>(battlefieldCards);
        cards.addAll(graveyardCards);
        if (cards.isEmpty()) {
            return false;
        }

        getEffects().setTargetPointer(new FixedTargets(new CardsImpl(cards), game));
        return true;
    }
}

class KayaSpiritsJusticeCopyEffect extends OneShotEffect {

    KayaSpiritsJusticeCopyEffect() {
        super(Outcome.Copy);
    }

    private KayaSpiritsJusticeCopyEffect(final KayaSpiritsJusticeCopyEffect effect) {
        super(effect);
    }

    @Override
    public KayaSpiritsJusticeCopyEffect copy() {
        return new KayaSpiritsJusticeCopyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent copyToPermanent = game.getPermanent(source.getFirstTarget());
        Cards exiledCards = new CardsImpl(getTargetPointer().getTargets(game, source));
        if (controller == null || copyToPermanent == null || exiledCards.isEmpty()) {
            return false;
        }

        TargetCard target = new TargetCardInExile(0, 1, StaticFilters.FILTER_CARD_CREATURE, null);
        if (!controller.chooseTarget(outcome, exiledCards, target, source, game)) {
            return false;
        }

        Card copyFromCard = game.getCard(target.getFirstTarget());
        if (copyFromCard == null) {
            return false;
        }

        Permanent newBlueprint = new PermanentCard(copyFromCard, source.getControllerId(), game);
        newBlueprint.assignNewId();
        CopyApplier applier = new KayaSpiritsJusticeCopyApplier();
        applier.apply(game, newBlueprint, source, copyToPermanent.getId());
        CopyEffect copyEffect = new CopyEffect(Duration.EndOfTurn, newBlueprint, copyToPermanent.getId());
        copyEffect.newId();
        copyEffect.setApplier(applier);
        Ability newAbility = source.copy();
        copyEffect.init(newAbility, game);
        game.addEffect(copyEffect, source);

        return true;
    }
}

class KayaSpiritsJusticeCopyApplier extends CopyApplier {

    @Override
    public boolean apply(Game game, MageObject blueprint, Ability source, UUID copyToObjectId) {
        blueprint.getAbilities().add(FlyingAbility.getInstance());
        return true;
    }
}

class KayaSpiritsJusticeExileEffect extends OneShotEffect {

    KayaSpiritsJusticeExileEffect() {
        super(Outcome.Exile);
        staticText = "exile a card from a graveyard";
    }

    private KayaSpiritsJusticeExileEffect(final KayaSpiritsJusticeExileEffect effect) {
        super(effect);
    }

    @Override
    public KayaSpiritsJusticeExileEffect copy() {
        return new KayaSpiritsJusticeExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            TargetCardInGraveyard target = new TargetCardInGraveyard();
            target.withNotTarget(true);
            controller.choose(outcome, target, source, game);
            Card card = game.getCard(target.getFirstTarget());
            if (card != null) {
                UUID exileId = CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter());
                MageObject sourceObject = source.getSourceObject(game);
                String exileName = sourceObject == null ? null : sourceObject.getIdName();
                return controller.moveCardsToExile(card, source, game, true, exileId, exileName);
            }
        }
        return false;
    }
}

enum KayaSpiritsJusticeAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();

        Player controller = game.getPlayer(ability.getControllerId());
        if (controller == null) {
            return;
        }
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_CONTROLLED_CREATURE));

        for (UUID playerId : game.getState().getPlayersInRange(ability.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null || player == controller) {
                continue;
            }
            FilterPermanent filter = new FilterCreaturePermanent("creature that player controls");
            filter.add(new ControllerIdPredicate(playerId));
            ability.addTarget(new TargetPermanent(0, 1, filter)
                    .withChooseHint("from " + player.getLogName()));
        }
    }
}
