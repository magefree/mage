package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.*;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.targetpointer.FixedTargets;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class LaezelsAcrobatics extends CardImpl {

    public LaezelsAcrobatics(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{W}");

        this.getSpellAbility().addEffect(new LaezelsAcrobaticsEffect());
    }

    private LaezelsAcrobatics(final LaezelsAcrobatics card) {
        super(card);
    }

    @Override
    public LaezelsAcrobatics copy() {
        return new LaezelsAcrobatics(this);
    }
}

class LaezelsAcrobaticsEffect extends RollDieWithResultTableEffect {

    private static final FilterControlledCreaturePermanent creatureFilter = new FilterControlledCreaturePermanent();

    static {
        creatureFilter.add(TokenPredicate.FALSE);
    }

    LaezelsAcrobaticsEffect() {
        super(20, "Exile all nontoken creatures you control, then roll a d20");
        this.addTableEntry(
                1, 9,
                new InfoEffect("Return those cards to the battlefield under their owner's control at the " +
                        "beginning of the next end step."));
        this.addTableEntry(
                10, 20,
                new InfoEffect(
                        "Return those cards to the battlefield under their owner's control, then exile them again. Return those cards to the battlefield under their owner's control at the beginning of the next end step."));
    }

    private LaezelsAcrobaticsEffect(final LaezelsAcrobaticsEffect effect) {
        super(effect);
    }

    @Override
    public LaezelsAcrobaticsEffect copy() {
        return new LaezelsAcrobaticsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Cards toFlicker = new CardsImpl(game.getBattlefield().getActivePermanents(creatureFilter, controller.getId(), game));
        controller.moveCards(toFlicker, Zone.EXILED, source, game);
        game.getState().processAction(game);
        int result = controller.rollDice(outcome, source, game, 20);
        if (result >= 1 && result <= 9) {
            Effect effect = new ReturnToBattlefieldUnderOwnerControlTargetEffect(false, false);
            effect.setTargetPointer(new FixedTargets(new CardsImpl(toFlicker), game));
            game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(effect), source);
        } else if (result >= 10 && result <= 20) {
            for (UUID cardId : toFlicker) {
                Card card = game.getCard(cardId);
                if (card != null) {
                    PutCards.BATTLEFIELD.moveCard(game.getPlayer(card.getOwnerId()), card.getMainCard(), source, game, "card");
                }
            }
            game.getState().processAction(game);
            Effect effect = new ExileReturnBattlefieldNextEndStepTargetEffect();
            effect.setTargetPointer(new FixedTargets(toFlicker, game));
            effect.apply(game, source);
        }
        return true;
    }
}
