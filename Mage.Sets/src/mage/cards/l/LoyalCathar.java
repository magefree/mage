package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.common.CantBlockAbility;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.TransformAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.Card;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

public final class LoyalCathar extends TransformingDoubleFacedCard {

    public LoyalCathar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.SOLDIER}, "{W}{W}",
                "Unhallowed Cathar",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.ZOMBIE, SubType.SOLDIER}, "B"
        );

        // Loyal Cathar
        this.getLeftHalfCard().setPT(2, 2);

        // Vigilance
        this.getLeftHalfCard().addAbility(VigilanceAbility.getInstance());

        // When Loyal Cathar dies, return it to the battlefield transformed under your control at the beginning of the next end step.
        this.getLeftHalfCard().addAbility(new DiesSourceTriggeredAbility(new LoyalCatharEffect()));

        // Unhallowed Cathar
        this.getRightHalfCard().setPT(2, 1);

        // Unhallowed Cathar can't block.
        this.getRightHalfCard().addAbility(new CantBlockAbility());
    }

    private LoyalCathar(final LoyalCathar card) {
        super(card);
    }

    @Override
    public LoyalCathar copy() {
        return new LoyalCathar(this);
    }
}

class LoyalCatharEffect extends OneShotEffect {

    private static final String effectText = "return it to the battlefield transformed under your control at the beginning of the next end step";

    LoyalCatharEffect() {
        super(Outcome.Benefit);
        staticText = effectText;
    }

    private LoyalCatharEffect(final LoyalCatharEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        //create delayed triggered ability
        if (Zone.GRAVEYARD == game.getState().getZone(source.getSourceId())) {
            Effect effect = new ReturnLoyalCatharEffect();
            effect.setTargetPointer(new FixedTarget(source.getSourceId(), game.getState().getZoneChangeCounter(source.getSourceId())));
            game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(effect), source);
        }
        return true;
    }

    @Override
    public LoyalCatharEffect copy() {
        return new LoyalCatharEffect(this);
    }

}

class ReturnLoyalCatharEffect extends OneShotEffect {

    ReturnLoyalCatharEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "return it to the battlefield transformed under your control";
    }

    private ReturnLoyalCatharEffect(final ReturnLoyalCatharEffect effect) {
        super(effect);
    }

    @Override
    public ReturnLoyalCatharEffect copy() {
        return new ReturnLoyalCatharEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (card != null) {
            game.getState().setValue(TransformAbility.VALUE_KEY_ENTER_TRANSFORMED + card.getId(), Boolean.TRUE);
            controller.moveCards(card, Zone.BATTLEFIELD, source, game);
        }
        return true;
    }
}
