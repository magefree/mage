package mage.cards.a;

import mage.ApprovingObject;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.asthought.MayLookAtTargetCardEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;
import mage.target.targetpointer.FixedTargets;
import mage.util.CardUtil;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author DarioBarrabinoDev
 */
public final class AppleOfEdenIsuRelic extends CardImpl {

    public AppleOfEdenIsuRelic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        this.supertype.add(SuperType.LEGENDARY);

        // {T}, Pay 4 life, Sacrifice Apple of Eden: Look at target opponent's hand and exile those cards face down. You may play those cards this turn, and mana of any type can be spent to cast them. Until end of turn, whenever you play a land or cast a spell this way, its owner draws a card. At the beginning of the next end step, return the exiled cards to their owner's hand. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(new AppleOfEdenIsuRelicEffect(), new TapSourceCost());
        ability.addCost(new PayLifeCost(4));
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private AppleOfEdenIsuRelic(final AppleOfEdenIsuRelic card) {
        super(card);
    }

    @Override
    public AppleOfEdenIsuRelic copy() {
        return new AppleOfEdenIsuRelic(this);
    }
}

class AppleOfEdenIsuRelicEffect extends OneShotEffect {

    AppleOfEdenIsuRelicEffect() {
        super(Outcome.Benefit);
        staticText = "look at target opponent's hand and exile those cards face down. You may play those cards this turn, " +
                "and mana of any type can be spent to cast them. Until end of turn, whenever you play a land or cast a spell " +
                "this way, its owner draws a card. At the beginning of the next end step, return the exiled cards to their owner's hand";
    }

    private AppleOfEdenIsuRelicEffect(final AppleOfEdenIsuRelicEffect effect) {
        super(effect);
    }

    @Override
    public AppleOfEdenIsuRelicEffect copy() {
        return new AppleOfEdenIsuRelicEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(getTargetPointer().getFirst(game, source));
        MageObject sourceObject = source.getSourceObject(game);
        if (controller == null || opponent == null || sourceObject == null) {
            return false;
        }
        controller.lookAtCards(sourceObject.getIdName(), opponent.getHand(), game);
        Cards cards = new CardsImpl(opponent.getHand());
        if (cards.isEmpty()) {
            return true;
        }
        UUID exileZoneId = CardUtil.getExileZoneId(game, source.getSourceId(), source.getStackMomentSourceZCC());
        for (Card card : cards.getCards(game)) {
            card.setFaceDown(true, game);
        }
        controller.moveCardsToExile(cards.getCards(game), source, game, false, exileZoneId, sourceObject.getIdName());
        cards.retainZone(Zone.EXILED, game);
        Set<UUID> exiledCardIds = new HashSet<>();
        for (Card card : cards.getCards(game)) {
            card.setFaceDown(true, game);
            CardUtil.makeCardPlayable(game, source, card, false, Duration.EndOfTurn, true, controller.getId(), null);
            ContinuousEffect effect = new MayLookAtTargetCardEffect(controller.getId());
            effect.setTargetPointer(new FixedTarget(card.getId(), game));
            game.addEffect(effect, source);
            exiledCardIds.add(card.getId());
        }
        if (cards.isEmpty()) {
            return true;
        }
        game.addDelayedTriggeredAbility(new AppleOfEdenIsuRelicTriggeredAbility(exiledCardIds), source);
        game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(
                new ReturnToHandTargetEffect().setTargetPointer(new FixedTargets(cards, game))
        ), source);
        return true;
    }
}

class AppleOfEdenIsuRelicTriggeredAbility extends DelayedTriggeredAbility {

    private final Set<UUID> exiledCardIds = new HashSet<>();

    AppleOfEdenIsuRelicTriggeredAbility(Set<UUID> exiledCardIds) {
        super(new DrawCardTargetEffect(1), Duration.EndOfTurn, false, false);
        this.exiledCardIds.addAll(exiledCardIds);
        setTriggerPhrase("Until end of turn, whenever you play a land or cast a spell this way, ");
    }

    private AppleOfEdenIsuRelicTriggeredAbility(final AppleOfEdenIsuRelicTriggeredAbility ability) {
        super(ability);
        this.exiledCardIds.addAll(ability.exiledCardIds);
    }

    @Override
    public AppleOfEdenIsuRelicTriggeredAbility copy() {
        return new AppleOfEdenIsuRelicTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.LAND_PLAYED
                || event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!isControlledBy(event.getPlayerId())) {
            return false;
        }
        // only plays allowed by this Apple of Eden count as "this way"
        ApprovingObject approvingObject = event.getApprovingObject();
        if (approvingObject == null
                || !getSourceId().equals(approvingObject.getApprovingAbility().getSourceId())) {
            return false;
        }
        Card card;
        if (event.getType() == GameEvent.EventType.SPELL_CAST) {
            Spell spell = game.getSpell(event.getTargetId());
            card = spell == null ? null : spell.getCard();
        } else {
            card = game.getCard(event.getTargetId());
        }
        if (card == null || !exiledCardIds.contains(card.getMainCard().getId())) {
            return false;
        }
        getEffects().setTargetPointer(new FixedTarget(card.getOwnerId()));
        return true;
    }
}
