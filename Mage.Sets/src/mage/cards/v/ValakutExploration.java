package mage.cards.v;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.LandfallAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.asthought.PlayFromNotOwnHandZoneTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Library;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ValakutExploration extends CardImpl {

    public ValakutExploration(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");

        // Landfall — Whenever a land enters the battlefield under your control, exile the top card of your library. You may play that card for as long as it remains exiled.
        this.addAbility(new LandfallAbility(new ValakutExplorationExileEffect()));

        // At the beginning of your end step, if there are cards exiled with Valakut Exploration, put them into their owner's graveyard, then Valakut Exploration deals that much damage to each opponent.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfEndStepTriggeredAbility(
                        new ValakutExplorationDamageEffect(), TargetController.YOU, false
                ), ValakutExplorationCondition.instance, "At the beginning of your end step, " +
                "if there are cards exiled with {this}, put them into their owner's graveyard, " +
                "then {this} deals that much damage to each opponent."
        ));
    }

    private ValakutExploration(final ValakutExploration card) {
        super(card);
    }

    @Override
    public ValakutExploration copy() {
        return new ValakutExploration(this);
    }
}

enum ValakutExplorationCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        ExileZone exileZone = game.getExile().getExileZone(CardUtil.getExileZoneId(
                game, source.getSourceId(), game.getState().getZoneChangeCounter(source.getSourceId())
        ));
        return exileZone != null && !exileZone.isEmpty();
    }
}

class ValakutExplorationExileEffect extends OneShotEffect {

    ValakutExplorationExileEffect() {
        super(Outcome.Detriment);
        this.staticText = "exile the top card of your library. You may play that card for as long as it remains exiled";
    }

    private ValakutExplorationExileEffect(final ValakutExplorationExileEffect effect) {
        super(effect);
    }

    @Override
    public ValakutExplorationExileEffect copy() {
        return new ValakutExplorationExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (sourcePermanent == null || controller == null || !controller.getLibrary().hasCards()) {
            return false;
        }
        Library library = controller.getLibrary();
        Card card = library.getFromTop(game);
        if (card == null) {
            return true;
        }
        controller.moveCardsToExile(
                card, source, game, true, CardUtil.getExileZoneId(
                        game, source.getSourceId(), source.getSourceObjectZoneChangeCounter()
                ), sourcePermanent.getIdName()
        );
        ContinuousEffect effect = new PlayFromNotOwnHandZoneTargetEffect(Duration.EndOfTurn);
        effect.setTargetPointer(new FixedTarget(card, game));
        game.addEffect(effect, source);
        return true;
    }
}

class ValakutExplorationCastFromExileEffect extends AsThoughEffectImpl {

    ValakutExplorationCastFromExileEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.Custom, Outcome.Benefit);
        staticText = "You may play the card from exile";
    }

    private ValakutExplorationCastFromExileEffect(final ValakutExplorationCastFromExileEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public ValakutExplorationCastFromExileEffect copy() {
        return new ValakutExplorationCastFromExileEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        return source.isControlledBy(affectedControllerId)
                && objectId.equals(getTargetPointer().getFirst(game, source));
    }
}

class ValakutExplorationDamageEffect extends OneShotEffect {

    ValakutExplorationDamageEffect() {
        super(Outcome.Benefit);
    }

    private ValakutExplorationDamageEffect(final ValakutExplorationDamageEffect effect) {
        super(effect);
    }

    @Override
    public ValakutExplorationDamageEffect copy() {
        return new ValakutExplorationDamageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        ExileZone exileZone = game.getExile().getExileZone(CardUtil.getExileZoneId(
                game, source.getSourceId(), source.getSourceObjectZoneChangeCounter()
        ));
        if (exileZone == null) {
            return false;
        }
        int count = exileZone.size();
        if (count < 1) {
            return false;
        }
        player.moveCards(exileZone, Zone.GRAVEYARD, source, game);
        for (UUID playerId : game.getOpponents(source.getControllerId())) {
            player = game.getPlayer(playerId);
            if (playerId == null) {
                continue;
            }
            player.damage(count, source.getSourceId(), game);
        }
        return true;
    }
}
