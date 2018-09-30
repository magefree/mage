
package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.CanBeYourCommanderAbility;
import mage.abilities.common.PlaneswalkerEntersWithLoyaltyCountersAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterArtifactCard;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.game.Game;
import mage.game.command.emblems.DarettiScrapSavantEmblem;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetDiscard;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public final class DarettiScrapSavant extends CardImpl {

    public DarettiScrapSavant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{3}{R}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.DARETTI);

        this.addAbility(new PlaneswalkerEntersWithLoyaltyCountersAbility(3));

        // +2: Discard up to two cards, then draw that many cards.
        this.addAbility(new LoyaltyAbility(new DarettiDiscardDrawEffect(), 2));

        // -2: Sacrifice an artifact. If you do, return target artifact card from your graveyard to the battlefield.
        LoyaltyAbility loyaltyAbility = new LoyaltyAbility(new DarettiSacrificeEffect(), -2);
        loyaltyAbility.addTarget(new TargetCardInYourGraveyard(new FilterArtifactCard("artifact card from your graveyard")));
        this.addAbility(loyaltyAbility);

        // -10: You get an emblem with "Whenever an artifact is put into your graveyard from the battlefield, return that card to the battlefield at the beginning of the next end step."
        this.addAbility(new LoyaltyAbility(new GetEmblemEffect(new DarettiScrapSavantEmblem()), -10));

        // Daretti, Scrap Savant can be your commander.
        this.addAbility(CanBeYourCommanderAbility.getInstance());
    }

    public DarettiScrapSavant(final DarettiScrapSavant card) {
        super(card);
    }

    @Override
    public DarettiScrapSavant copy() {
        return new DarettiScrapSavant(this);
    }
}

class DarettiDiscardDrawEffect extends OneShotEffect {

    public DarettiDiscardDrawEffect() {
        super(Outcome.Detriment);
        this.staticText = "Discard up to two cards, then draw that many cards";
    }

    public DarettiDiscardDrawEffect(final DarettiDiscardDrawEffect effect) {
        super(effect);
    }

    @Override
    public DarettiDiscardDrawEffect copy() {
        return new DarettiDiscardDrawEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            TargetDiscard target = new TargetDiscard(0, 2, new FilterCard(), controller.getId());
            target.choose(outcome, controller.getId(), source.getSourceId(), game);
            int count = 0;
            for (UUID cardId : target.getTargets()) {
                Card card = game.getCard(cardId);
                if (card != null) {
                    controller.discard(card, source, game);
                    count++;
                }
            }
            controller.drawCards(count, game);
            return true;
        }
        return false;
    }
}

class DarettiSacrificeEffect extends OneShotEffect {

    public DarettiSacrificeEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "Sacrifice an artifact. If you do, return target artifact card from your graveyard to the battlefield";
    }

    public DarettiSacrificeEffect(final DarettiSacrificeEffect effect) {
        super(effect);
    }

    @Override
    public DarettiSacrificeEffect copy() {
        return new DarettiSacrificeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Target target = new TargetControlledPermanent(1, 1, new FilterControlledArtifactPermanent(), true);
            if (target.canChoose(source.getSourceId(), controller.getId(), game)
                    && controller.chooseTarget(outcome, target, source, game)) {
                Permanent artifact = game.getPermanent(target.getFirstTarget());
                if (artifact != null && artifact.sacrifice(source.getSourceId(), game)) {
                    Card card = game.getCard(getTargetPointer().getFirst(game, source));
                    if (card != null) {
                        return controller.moveCards(card, Zone.BATTLEFIELD, source, game);
                    }
                }
            }
            return true;
        }
        return false;
    }
}

class DarettiScrapSavantEffect extends OneShotEffect {

    DarettiScrapSavantEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "return that card to the battlefield at the beginning of the next end step";
    }

    DarettiScrapSavantEffect(final DarettiScrapSavantEffect effect) {
        super(effect);
    }

    @Override
    public DarettiScrapSavantEffect copy() {
        return new DarettiScrapSavantEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (card != null && game.getState().getZone(card.getId()) == Zone.GRAVEYARD) {
            Effect effect = new ReturnFromGraveyardToBattlefieldTargetEffect();
            effect.setTargetPointer(new FixedTarget(card.getId(), card.getZoneChangeCounter(game)));
            effect.setText("return that card to the battlefield at the beginning of the next end step");
            game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(Zone.COMMAND, effect, TargetController.ANY), source);
            return true;
        }
        return false;
    }
}
