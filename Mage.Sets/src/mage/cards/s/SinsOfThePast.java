
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.ExileSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author emerald000
 */
public final class SinsOfThePast extends CardImpl {

    public SinsOfThePast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{B}{B}");

        // Until end of turn, you may cast target instant or sorcery card from your graveyard without paying its mana cost. If that card would be put into your graveyard this turn, exile it instead. Exile Sins of the Past.
        this.getSpellAbility().addEffect(new SinsOfThePastEffect());
        this.getSpellAbility().addEffect(new ExileSourceEffect());
        this.getSpellAbility().addTarget(new TargetCardInGraveyard(new FilterInstantOrSorceryCard()));
    }

    public SinsOfThePast(final SinsOfThePast card) {
        super(card);
    }

    @Override
    public SinsOfThePast copy() {
        return new SinsOfThePast(this);
    }
}

class SinsOfThePastEffect extends OneShotEffect {

    SinsOfThePastEffect() {
        super(Outcome.PlayForFree);
        this.staticText = "Until end of turn, you may cast target instant or sorcery card from your graveyard without paying its mana cost. If that card would be put into your graveyard this turn, exile it instead";
    }

    SinsOfThePastEffect(final SinsOfThePastEffect effect) {
        super(effect);
    }

    @Override
    public SinsOfThePastEffect copy() {
        return new SinsOfThePastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(this.getTargetPointer().getFirst(game, source));
        if (card != null) {
            ContinuousEffect effect = new SinsOfThePastCastFromGraveyardEffect();
            effect.setTargetPointer(new FixedTarget(card.getId()));
            game.addEffect(effect, source);
            effect = new SinsOfThePastReplacementEffect(card.getId());
            game.addEffect(effect, source);
            return true;
        }
        return false;
    }
}

class SinsOfThePastCastFromGraveyardEffect extends AsThoughEffectImpl {

    SinsOfThePastCastFromGraveyardEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfTurn, Outcome.PlayForFree);
    }

    SinsOfThePastCastFromGraveyardEffect(final SinsOfThePastCastFromGraveyardEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public SinsOfThePastCastFromGraveyardEffect copy() {
        return new SinsOfThePastCastFromGraveyardEffect(this);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        if (sourceId.equals(this.getTargetPointer().getFirst(game, source)) && affectedControllerId.equals(source.getControllerId())) {
            Player player = game.getPlayer(affectedControllerId);
            player.setCastSourceIdWithAlternateMana(sourceId, null, null);
            return true;
        }
        return false;
    }
}

class SinsOfThePastReplacementEffect extends ReplacementEffectImpl {

    private final UUID cardId;

    SinsOfThePastReplacementEffect(UUID cardId) {
        super(Duration.EndOfTurn, Outcome.Exile);
        this.cardId = cardId;
    }

    SinsOfThePastReplacementEffect(final SinsOfThePastReplacementEffect effect) {
        super(effect);
        this.cardId = effect.cardId;
    }

    @Override
    public SinsOfThePastReplacementEffect copy() {
        return new SinsOfThePastReplacementEffect(this);
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
