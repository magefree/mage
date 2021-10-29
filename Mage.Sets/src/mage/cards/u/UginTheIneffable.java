package mage.cards.u;

import mage.MageObject;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.PlaneswalkerEntersWithLoyaltyCountersAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorlessPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.token.UginTheIneffableToken;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static mage.constants.Outcome.Benefit;

/**
 * @author TheElk801
 */
public final class UginTheIneffable extends CardImpl {

    private static final FilterCard filter = new FilterCard();
    private static final FilterPermanent filter2 = new FilterPermanent("permanent that's one or more colors");

    static {
        filter.add(ColorlessPredicate.instance);
        filter2.add(Predicates.not(ColorlessPredicate.instance));
    }

    public UginTheIneffable(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{6}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.UGIN);
        this.addAbility(new PlaneswalkerEntersWithLoyaltyCountersAbility(4));

        // Colorless spells you cast cost {2} less to cast.
        this.addAbility(new SimpleStaticAbility(new SpellsCostReductionControllerEffect(
                filter, 2
        ).setText("Colorless spells you cast cost {2} less to cast.")));

        // +1: Exile the top card of your library face down and look at it. Create a 2/2 colorless Spirit creature token. When that token leaves the battlefield, put the exiled card into your hand.
        this.addAbility(new LoyaltyAbility(new UginTheIneffableEffect(), 1));

        // -3: Destroy target permanent that's one or more colors.
        Ability ability = new LoyaltyAbility(new DestroyTargetEffect(), -3);
        ability.addTarget(new TargetPermanent(filter2));
        this.addAbility(ability);
    }

    private UginTheIneffable(final UginTheIneffable card) {
        super(card);
    }

    @Override
    public UginTheIneffable copy() {
        return new UginTheIneffable(this);
    }
}

class UginTheIneffableEffect extends OneShotEffect {

    UginTheIneffableEffect() {
        super(Benefit);
        staticText = "Exile the top card of your library face down and look at it. "
                + "Create a 2/2 colorless Spirit creature token. When that token leaves the battlefield, "
                + "put the exiled card into your hand.";
    }

    private UginTheIneffableEffect(final UginTheIneffableEffect effect) {
        super(effect);
    }

    @Override
    public UginTheIneffableEffect copy() {
        return new UginTheIneffableEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (player == null || sourceObject == null) {
            return false;
        }

        Card card = player.getLibrary().getFromTop(game);
        if (card == null) {
            return false;
        }

        // exile and look
        UUID exileZoneId = CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter());
        if (player.moveCardsToExile(card, source, game, false, exileZoneId, sourceObject.getIdName() + " (" + player.getName() + ")")) {
            card.turnFaceDown(source, game, source.getControllerId());
            player.lookAtCards(player.getName() + " - " + card.getIdName() + " - " + CardUtil.sdf.format(System.currentTimeMillis()), card, game);
        }

        // create token
        Set<MageObjectReference> tokenObjs = new HashSet<>();
        CreateTokenEffect effect = new CreateTokenEffect(new UginTheIneffableToken());
        effect.apply(game, source);

        // with return ability
        for (UUID addedTokenId : effect.getLastAddedTokenIds()) {
            // display referenced exiled face-down card on token
            SimpleStaticAbility sa = new SimpleStaticAbility(Zone.BATTLEFIELD, new InfoEffect("Referenced object: "
                    + card.getId().toString().substring(0, 3)));
            GainAbilityTargetEffect gainAbilityEffect = new GainAbilityTargetEffect(sa, Duration.WhileOnBattlefield);
            gainAbilityEffect.setTargetPointer(new FixedTarget(addedTokenId));
            game.addEffect(gainAbilityEffect, source);

            // look at face-down card in exile
            UginTheIneffableLookAtFaceDownEffect lookAtEffect = new UginTheIneffableLookAtFaceDownEffect();
            lookAtEffect.setTargetPointer(new FixedTarget(card.getId(), game));
            game.addEffect(lookAtEffect, source);

            tokenObjs.add(new MageObjectReference(addedTokenId, game));
            game.addDelayedTriggeredAbility(new UginTheIneffableDelayedTriggeredAbility(
                    tokenObjs, new MageObjectReference(card, game)
            ), source);
        }
        return true;
    }
}

class UginTheIneffableDelayedTriggeredAbility extends DelayedTriggeredAbility {

    private final Set<MageObjectReference> tokenRefs;
    private final MageObjectReference cardRef;

    UginTheIneffableDelayedTriggeredAbility(Set<MageObjectReference> tokens, MageObjectReference card) {
        super(null, Duration.Custom, true);
        this.tokenRefs = tokens;
        this.cardRef = card;
    }

    private UginTheIneffableDelayedTriggeredAbility(final UginTheIneffableDelayedTriggeredAbility ability) {
        super(ability);
        this.tokenRefs = ability.tokenRefs;
        this.cardRef = ability.cardRef;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = ((ZoneChangeEvent) event);
        if (zEvent.getToZone() == Zone.BATTLEFIELD
                || tokenRefs.stream().noneMatch(tokenRef -> tokenRef.refersTo(zEvent.getTarget(), game))) {
            return false;
        }
        this.getEffects().clear();
        Effect effect = new ReturnToHandTargetEffect();
        effect.setTargetPointer(new FixedTarget(cardRef));
        this.addEffect(effect);
        return true;
    }

    @Override
    public UginTheIneffableDelayedTriggeredAbility copy() {
        return new UginTheIneffableDelayedTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "When this token leaves the battlefield, put the exiled card into your hand.";
    }
}

class UginTheIneffableLookAtFaceDownEffect extends AsThoughEffectImpl {

    UginTheIneffableLookAtFaceDownEffect() {
        super(AsThoughEffectType.LOOK_AT_FACE_DOWN, Duration.EndOfGame, Outcome.Benefit);
    }

    private UginTheIneffableLookAtFaceDownEffect(final UginTheIneffableLookAtFaceDownEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public UginTheIneffableLookAtFaceDownEffect copy() {
        return new UginTheIneffableLookAtFaceDownEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        UUID cardId = getTargetPointer().getFirst(game, source);
        if (cardId == null) {
            this.discard();
        }
        return affectedControllerId.equals(source.getControllerId())
                && objectId.equals(cardId)
                && game.getState().getExile().containsId(cardId, game);
    }
}
