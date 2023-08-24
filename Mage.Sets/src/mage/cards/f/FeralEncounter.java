package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfCombatDelayedTriggeredAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DamageWithPowerFromOneToAnotherTargetEffect;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class FeralEncounter extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature you don't control");

    static {
        filter.add(TargetController.NOT_YOU.getControllerPredicate());
    }

    public FeralEncounter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{G}{G}");

        // Look at the top five cards of your library. You may exile a creature card from among them. Put the rest on the bottom of your library in a random order. You may cast the exiled card this turn. At the beginning of the next combat phase this turn, target creature you control deals damage equal to its power to up to one target creature you don't control.
        this.getSpellAbility().addEffect(new FeralEncounterEffect());

        DelayedTriggeredAbility delayed = new AtTheBeginOfCombatDelayedTriggeredAbility(
                new DamageWithPowerFromOneToAnotherTargetEffect(),
                AtTheBeginOfCombatDelayedTriggeredAbility.PhaseSelection.NEXT_COMBAT_PHASE_THIS_TURN
        );
        delayed.addTarget(new TargetControlledCreaturePermanent());
        delayed.addTarget(new TargetCreaturePermanent(0, 1, filter, false));
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(delayed)
                .setText("At the beginning of the next combat phase this turn, target creature you "
                        + "control deals damage equal to its power to up to one target creature you don't control"));
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