package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DamageWithPowerFromOneToAnotherTargetEffect;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.PutCards;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class FeralEncounter extends CardImpl {

    public FeralEncounter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{G}{G}");

        // Look at the top five cards of your library. You may exile a creature card from among them. Put the rest on the bottom of your library in a random order. You may cast the exiled card this turn. At the beginning of the next combat phase this turn, target creature you control deals damage equal to its power to up to one target creature you don't control.
        this.getSpellAbility().addEffect(new FeralEncounterEffect());

        DelayedTriggeredAbility delayed = new FeralEncounterDelayedTriggeredAbility();
        delayed.addTarget(new TargetControlledCreaturePermanent());
        delayed.addTarget(new TargetCreaturePermanent(0, 1, StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL, false));
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(delayed));
    }

    private FeralEncounter(final FeralEncounter card) {
        super(card);
    }

    @Override
    public FeralEncounter copy() {
        return new FeralEncounter(this);
    }
}

class FeralEncounterEffect extends LookLibraryAndPickControllerEffect {

    FeralEncounterEffect() {
        super(5, 1, StaticFilters.FILTER_CARD_CREATURE, PutCards.EXILED, PutCards.BOTTOM_RANDOM);
        staticText = "look at the top five cards of your library. You may exile a creature card from among them. "
                + "Put the rest on the bottom of your library in a random order. You may cast the exiled card this turn";
    }

    private FeralEncounterEffect(final FeralEncounterEffect effect) {
        super(effect);
    }

    @Override
    public FeralEncounterEffect copy() {
        return new FeralEncounterEffect(this);
    }

    @Override
    protected boolean actionWithPickedCards(Game game, Ability source, Player player, Cards pickedCards, Cards otherCards) {
        boolean result = putPickedCards.moveCards(player, pickedCards, source, game);
        pickedCards.retainZone(Zone.EXILED, game);
        for (Card card : pickedCards.getCards(game)) {
            CardUtil.makeCardPlayable(game, source, card, Duration.EndOfTurn, false);
        }
        result |= putLookedCards.moveCards(player, otherCards, source, game);
        return result;
    }
}

class FeralEncounterDelayedTriggeredAbility extends DelayedTriggeredAbility {

    public FeralEncounterDelayedTriggeredAbility() {
        super(new DamageWithPowerFromOneToAnotherTargetEffect(), Duration.EndOfTurn, true, false);
        setTriggerPhrase("At the beginning of the next combat phase this turn, ");
    }

    private FeralEncounterDelayedTriggeredAbility(final FeralEncounterDelayedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public FeralEncounterDelayedTriggeredAbility copy() {
        return new FeralEncounterDelayedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COMBAT_PHASE_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return true;
    }
}
