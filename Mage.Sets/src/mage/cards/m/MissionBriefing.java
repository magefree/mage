package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.asthought.PlayFromNotOwnHandZoneTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author TheElk801
 */
public final class MissionBriefing extends CardImpl {

    public MissionBriefing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}{U}");

        // Surveil 2, then choose an instant or sorcery card in your graveyard. You may cast that card this turn. If that card would be put into your graveyard this turn, exile it instead.
        this.getSpellAbility().addEffect(new MissionBriefingEffect());
    }

    private MissionBriefing(final MissionBriefing card) {
        super(card);
    }

    @Override
    public MissionBriefing copy() {
        return new MissionBriefing(this);
    }
}

class MissionBriefingEffect extends OneShotEffect {

    public MissionBriefingEffect() {
        super(Outcome.Benefit);
        this.staticText = "Surveil 2, then choose an instant or sorcery card "
                + "in your graveyard. You may cast that card this turn. "
                + "If that card would be put into your graveyard this turn, "
                + "exile it instead";
    }

    public MissionBriefingEffect(final MissionBriefingEffect effect) {
        super(effect);
    }

    @Override
    public MissionBriefingEffect copy() {
        return new MissionBriefingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        player.surveil(2, source, game);
        Target target = new TargetCardInYourGraveyard(
                new FilterInstantOrSorceryCard("instant or sorcery card from your graveyard"));
        if (!player.choose(outcome, target, source, game)) {
            return true;
        }
        Card card = game.getCard(target.getFirstTarget());
        if (card != null) {
            ContinuousEffect effect = new PlayFromNotOwnHandZoneTargetEffect();
            effect.setTargetPointer(new FixedTarget(card, game));
            game.addEffect(effect, source);
            effect = new MissionBriefingReplacementEffect(card.getId());
            game.addEffect(effect, source);
            return true;
        }
        return false;
    }
}

class MissionBriefingReplacementEffect extends ReplacementEffectImpl {

    private final UUID cardId;

    public MissionBriefingReplacementEffect(UUID cardId) {
        super(Duration.EndOfTurn, Outcome.Exile);
        this.cardId = cardId;
        staticText = "If that card would be put into your graveyard this turn, "
                + "exile it instead";
    }

    public MissionBriefingReplacementEffect(final MissionBriefingReplacementEffect effect) {
        super(effect);
        this.cardId = effect.cardId;
    }

    @Override
    public MissionBriefingReplacementEffect copy() {
        return new MissionBriefingReplacementEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        Card card = game.getCard(this.cardId);
        if (controller != null && card != null) {
            controller.moveCardsToExile(card, source, game, true, null, "");
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
