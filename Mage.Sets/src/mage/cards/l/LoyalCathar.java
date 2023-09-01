package mage.cards.l;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.CantBlockAbility;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
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

/**
 * @author BetaSteward
 */
public final class LoyalCathar extends TransformingDoubleFacedCard {

    public LoyalCathar(UUID ownerId, CardSetInfo setInfo) {
        super(
                ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.SOLDIER}, "{W}{W}",
                "Unhallowed Cathar",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.ZOMBIE, SubType.SOLDIER}, "B"
        );
        this.getLeftHalfCard().setPT(2, 2);
        this.getRightHalfCard().setPT(2, 1);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Vigilance
        this.getLeftHalfCard().addAbility(VigilanceAbility.getInstance());

        // When Loyal Cathar dies, return it to the battlefield transformed under your control at the beginning of the next end step.
        this.getLeftHalfCard().addAbility(new DiesSourceTriggeredAbility(new LoyalCatharEffect()));

        // Unhallowed Cathar
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

    LoyalCatharEffect() {
        super(Outcome.Benefit);
        staticText = "return it to the battlefield transformed under your control at the beginning of the next end step";
    }

    LoyalCatharEffect(LoyalCatharEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject sourceObject = source.getSourceObject(game);
        if (!(sourceObject instanceof Card)
                || sourceObject.getZoneChangeCounter(game) != source.getSourceObjectZoneChangeCounter() + 1) {
            return false;
        }
        game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(
                new ReturnLoyalCatharEffect(sourceObject, game)), source
        );
        return true;
    }

    @Override
    public LoyalCatharEffect copy() {
        return new LoyalCatharEffect(this);
    }

}

class ReturnLoyalCatharEffect extends OneShotEffect {

    public ReturnLoyalCatharEffect(MageObject sourceObject, Game game) {
        super(Outcome.PutCardInPlay);
        this.setTargetPointer(new FixedTarget((Card) sourceObject, game));
        this.staticText = "return it to the battlefield transformed under your control";
    }

    public ReturnLoyalCatharEffect(final ReturnLoyalCatharEffect effect) {
        super(effect);
    }

    @Override
    public ReturnLoyalCatharEffect copy() {
        return new ReturnLoyalCatharEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (controller == null || card == null) {
            return false;
        }
        TransformingDoubleFacedCard.setCardTransformed(card, game);
        controller.moveCards(card, Zone.BATTLEFIELD, source, game);
        return true;
    }
}
