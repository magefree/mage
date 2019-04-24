package mage.cards.c;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.BeginningOfPreCombatMainTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ChooseACardNameEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.SendOptionUsedEventEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.*;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetOpponent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CabalTherapist extends CardImpl {

    public CabalTherapist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");

        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Menace
        this.addAbility(new MenaceAbility());

        // At the beginning of your precombat main phase, you may sacrifice a creature. When you do, choose a nonland card name, then target player reveals their hand and discards all cards with that name.
        this.addAbility(new BeginningOfPreCombatMainTriggeredAbility(
                new DoIfCostPaid(
                        new CabalTherapistCreateReflexiveTriggerEffect(),
                        new SacrificeTargetCost(new TargetControlledPermanent(
                                StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT
                        )), "Sacrifice a creature?"
                ).setText("you may sacrifice a creature. When you do, " +
                        "choose a nonland card name, then target player reveals their hand " +
                        "and discards all cards with that name."),
                TargetController.YOU, false
        ));
    }

    private CabalTherapist(final CabalTherapist card) {
        super(card);
    }

    @Override
    public CabalTherapist copy() {
        return new CabalTherapist(this);
    }
}

class CabalTherapistCreateReflexiveTriggerEffect extends OneShotEffect {

    CabalTherapistCreateReflexiveTriggerEffect() {
        super(Outcome.Benefit);
    }

    private CabalTherapistCreateReflexiveTriggerEffect(final CabalTherapistCreateReflexiveTriggerEffect effect) {
        super(effect);
    }

    @Override
    public CabalTherapistCreateReflexiveTriggerEffect copy() {
        return new CabalTherapistCreateReflexiveTriggerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        game.addDelayedTriggeredAbility(new CabalTherapistReflexiveTriggeredAbility(), source);
        return new SendOptionUsedEventEffect().apply(game, source);
    }
}

class CabalTherapistReflexiveTriggeredAbility extends DelayedTriggeredAbility {

    CabalTherapistReflexiveTriggeredAbility() {
        super(new ChooseACardNameEffect(ChooseACardNameEffect.TypeOfName.NON_LAND_NAME), Duration.OneUse, true);
        this.addEffect(new CabalTherapistDiscardEffect());
        this.addTarget(new TargetPlayer());
    }

    private CabalTherapistReflexiveTriggeredAbility(final CabalTherapistReflexiveTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public CabalTherapistReflexiveTriggeredAbility copy() {
        return new CabalTherapistReflexiveTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.OPTION_USED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getPlayerId().equals(this.getControllerId())
                && event.getSourceId().equals(this.getSourceId());
    }

    @Override
    public String getRule() {
        return "Choose a nonland card name, then target player reveals their hand and discards all cards with that name.";
    }
}

class CabalTherapistDiscardEffect extends OneShotEffect {

    CabalTherapistDiscardEffect() {
        super(Outcome.Discard);
    }

    private CabalTherapistDiscardEffect(final CabalTherapistDiscardEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(targetPointer.getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (targetPlayer == null || controller == null || sourceObject == null) {
            return false;
        }
        String cardName = (String) game.getState().getValue(source.getSourceId().toString() + ChooseACardNameEffect.INFO_KEY);
        Cards hand = targetPlayer.getHand();

        for (Card card : hand.getCards(game)) {
            if (card.isSplitCard()) {
                SplitCard splitCard = (SplitCard) card;
                if (CardUtil.haveSameNames(splitCard.getLeftHalfCard().getName(), cardName)) {
                    targetPlayer.discard(card, source, game);
                } else if (CardUtil.haveSameNames(splitCard.getRightHalfCard().getName(), cardName)) {
                    targetPlayer.discard(card, source, game);
                }
            }
            if (CardUtil.haveSameNames(card.getName(), cardName)) {
                targetPlayer.discard(card, source, game);
            }
        }
        targetPlayer.revealCards("Cabal Therapist", hand, game);
        return true;
    }

    @Override
    public CabalTherapistDiscardEffect copy() {
        return new CabalTherapistDiscardEffect(this);
    }
}
