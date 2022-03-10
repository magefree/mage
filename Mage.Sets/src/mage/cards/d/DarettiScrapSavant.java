package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.CanBeYourCommanderAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.discard.DiscardAndDrawThatManyEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.game.Game;
import mage.game.command.emblems.DarettiScrapSavantEmblem;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class DarettiScrapSavant extends CardImpl {

    public DarettiScrapSavant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{3}{R}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.DARETTI);

        this.setStartingLoyalty(3);

        // +2: Discard up to two cards, then draw that many cards.
        this.addAbility(new LoyaltyAbility(new DiscardAndDrawThatManyEffect(2), 2));

        // -2: Sacrifice an artifact. If you do, return target artifact card from your graveyard to the battlefield.
        LoyaltyAbility loyaltyAbility = new LoyaltyAbility(new DarettiSacrificeEffect(), -2);
        loyaltyAbility.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_ARTIFACT_FROM_YOUR_GRAVEYARD));
        this.addAbility(loyaltyAbility);

        // -10: You get an emblem with "Whenever an artifact is put into your graveyard from the battlefield, return that card to the battlefield at the beginning of the next end step."
        this.addAbility(new LoyaltyAbility(new GetEmblemEffect(new DarettiScrapSavantEmblem()), -10));

        // Daretti, Scrap Savant can be your commander.
        this.addAbility(CanBeYourCommanderAbility.getInstance());
    }

    private DarettiScrapSavant(final DarettiScrapSavant card) {
        super(card);
    }

    @Override
    public DarettiScrapSavant copy() {
        return new DarettiScrapSavant(this);
    }
}

class DarettiSacrificeEffect extends OneShotEffect {

    DarettiSacrificeEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "Sacrifice an artifact. If you do, return target artifact card from your graveyard to the battlefield";
    }

    private DarettiSacrificeEffect(final DarettiSacrificeEffect effect) {
        super(effect);
    }

    @Override
    public DarettiSacrificeEffect copy() {
        return new DarettiSacrificeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Target target = new TargetControlledPermanent(1, 1, new FilterControlledArtifactPermanent(), true);
        if (!target.canChoose(source.getSourceId(), controller.getId(), game)
                || !controller.chooseTarget(outcome, target, source, game)) {
            return true;
        }
        Permanent artifact = game.getPermanent(target.getFirstTarget());
        if (artifact == null || !artifact.sacrifice(source, game)) {
            return true;
        }
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        return card == null || controller.moveCards(card, Zone.BATTLEFIELD, source, game);
    }
}
