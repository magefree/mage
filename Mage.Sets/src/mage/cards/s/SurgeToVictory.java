package mage.cards.s;

import mage.ApprovingObject;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SurgeToVictory extends CardImpl {

    public SurgeToVictory(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{R}{R}");

        // Exile target instant or sorcery card from your graveyard. Creatures you control get +X/+0 until end of turn, where X is that card's mana value. Whenever a creature you control deals combat damage to a player this turn, copy the exiled card. You may cast the copy without paying its mana cost.
        this.getSpellAbility().addEffect(new SurgeToVictoryExileEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(
                StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY_FROM_YOUR_GRAVEYARD
        ));
    }

    private SurgeToVictory(final SurgeToVictory card) {
        super(card);
    }

    @Override
    public SurgeToVictory copy() {
        return new SurgeToVictory(this);
    }
}

class SurgeToVictoryExileEffect extends OneShotEffect {

    SurgeToVictoryExileEffect() {
        super(Outcome.Benefit);
        staticText = "exile target instant or sorcery card from your graveyard. " +
                "Creatures you control get +X/+0 until end of turn, where X is that card's mana value. " +
                "Whenever a creature you control deals combat damage to a player this turn, copy the exiled card. " +
                "You may cast the copy without paying its mana cost";
    }

    private SurgeToVictoryExileEffect(final SurgeToVictoryExileEffect effect) {
        super(effect);
    }

    @Override
    public SurgeToVictoryExileEffect copy() {
        return new SurgeToVictoryExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(source.getFirstTarget());
        if (player == null || card == null) {
            return false;
        }
        player.moveCards(card, Zone.EXILED, source, game);
        game.addEffect(new BoostControlledEffect(card.getManaValue(), 0, Duration.EndOfTurn), source);
        game.addDelayedTriggeredAbility(new SurgeToVictoryTriggeredAbility(card, game), source);
        return true;
    }
}

class SurgeToVictoryTriggeredAbility extends DelayedTriggeredAbility {

    public SurgeToVictoryTriggeredAbility(Card card, Game game) {
        super(new SurgeToVictoryCastEffect(card, game), Duration.EndOfTurn, false, false);
    }

    private SurgeToVictoryTriggeredAbility(final SurgeToVictoryTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return ((DamagedEvent) event).isCombatDamage()
                && isControlledBy(game.getControllerId(event.getSourceId()));
    }

    @Override
    public SurgeToVictoryTriggeredAbility copy() {
        return new SurgeToVictoryTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever a creature you control deals combat damage to a player this turn, " +
                "copy the exiled card. You may cast the copy without paying its mana cost.";
    }
}

class SurgeToVictoryCastEffect extends OneShotEffect {

    private final MageObjectReference mor;

    SurgeToVictoryCastEffect(Card card, Game game) {
        super(Outcome.Benefit);
        this.mor = new MageObjectReference(card, game);
    }

    private SurgeToVictoryCastEffect(final SurgeToVictoryCastEffect effect) {
        super(effect);
        this.mor = effect.mor;
    }

    @Override
    public SurgeToVictoryCastEffect copy() {
        return new SurgeToVictoryCastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = mor.getCard(game);
        if (player == null || card == null) {
            return false;
        }

        Card copiedCard = game.copyCard(card, source, player.getId());
        if (copiedCard == null) {
            return false;
        }
        if (!player.chooseUse(outcome, "Cast the copy of the exiled card?", source, game)) {
            return false;
        }
        game.getState().setValue("PlayFromNotOwnHandZone" + copiedCard.getId(), Boolean.TRUE);
        player.cast(
                player.chooseAbilityForCast(copiedCard, game, true),
                game, true, new ApprovingObject(source, game)
        );
        game.getState().setValue("PlayFromNotOwnHandZone" + copiedCard.getId(), null);
        return true;
    }
}
