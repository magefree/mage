
package mage.cards.j;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.PlaneswalkerEntersWithLoyaltyCountersAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.game.Game;
import mage.game.command.emblems.JaceTelepathUnboundEmblem;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public final class JaceTelepathUnbound extends CardImpl {

    public JaceTelepathUnbound(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.JACE);

        this.color.setBlue(true);
        this.nightCard = true;
        this.transformable = true;

        this.addAbility(new PlaneswalkerEntersWithLoyaltyCountersAbility(5));

        // +1: Up to one target creature gets -2/-0 until your next turn.
        Effect effect = new BoostTargetEffect(-2, 0, Duration.UntilYourNextTurn);
        effect.setText("Up to one target creature gets -2/-0 until your next turn");
        Ability ability = new LoyaltyAbility(effect, 1);
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        this.addAbility(ability);

        // -3: You may cast target instant or sorcery card from your graveyard this turn. If that card would be put into your graveyard this turn, exile it instead.
        ability = new LoyaltyAbility(new JaceTelepathUnboundEffect(), -3);
        ability.addTarget(new TargetCardInYourGraveyard(new FilterInstantOrSorceryCard()));
        this.addAbility(ability);

        // -9: You get an emblem with "Whenever you cast a spell, target opponent puts the top five cards of their library into their graveyard".
        this.addAbility(new LoyaltyAbility(new GetEmblemEffect(new JaceTelepathUnboundEmblem()), -9));
    }

    public JaceTelepathUnbound(final JaceTelepathUnbound card) {
        super(card);
    }

    @Override
    public JaceTelepathUnbound copy() {
        return new JaceTelepathUnbound(this);
    }
}

class JaceTelepathUnboundEffect extends OneShotEffect {

    JaceTelepathUnboundEffect() {
        super(Outcome.Benefit);
        this.staticText = "You may cast target instant or sorcery card from your graveyard this turn. If that card would be put into your graveyard this turn, exile it instead";
    }

    JaceTelepathUnboundEffect(final JaceTelepathUnboundEffect effect) {
        super(effect);
    }

    @Override
    public JaceTelepathUnboundEffect copy() {
        return new JaceTelepathUnboundEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(this.getTargetPointer().getFirst(game, source));
        if (card != null) {
            ContinuousEffect effect = new JaceTelepathUnboundCastFromGraveyardEffect();
            effect.setTargetPointer(new FixedTarget(card.getId(), card.getZoneChangeCounter(game)));
            game.addEffect(effect, source);
            effect = new JaceTelepathUnboundReplacementEffect(card.getId());
            game.addEffect(effect, source);
            return true;
        }
        return false;
    }
}

class JaceTelepathUnboundCastFromGraveyardEffect extends AsThoughEffectImpl {

    JaceTelepathUnboundCastFromGraveyardEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfTurn, Outcome.Benefit);
    }

    JaceTelepathUnboundCastFromGraveyardEffect(final JaceTelepathUnboundCastFromGraveyardEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public JaceTelepathUnboundCastFromGraveyardEffect copy() {
        return new JaceTelepathUnboundCastFromGraveyardEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        return objectId.equals(this.getTargetPointer().getFirst(game, source)) && affectedControllerId.equals(source.getControllerId());
    }
}

class JaceTelepathUnboundReplacementEffect extends ReplacementEffectImpl {

    private final UUID cardId;

    JaceTelepathUnboundReplacementEffect(UUID cardId) {
        super(Duration.EndOfTurn, Outcome.Exile);
        this.cardId = cardId;
        staticText = "If that card would be put into your graveyard this turn, exile it instead";
    }

    JaceTelepathUnboundReplacementEffect(final JaceTelepathUnboundReplacementEffect effect) {
        super(effect);
        this.cardId = effect.cardId;
    }

    @Override
    public JaceTelepathUnboundReplacementEffect copy() {
        return new JaceTelepathUnboundReplacementEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        Card card = game.getCard(this.cardId);
        if (controller != null && card != null) {
            controller.moveCardToExileWithInfo(card, null, "", source.getSourceId(), game, Zone.STACK, true);
            return true;
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        return zEvent.getToZone() == Zone.GRAVEYARD
                && zEvent.getTargetId().equals(this.cardId);
    }
}
