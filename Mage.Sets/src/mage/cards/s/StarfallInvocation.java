package mage.cards.s;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.condition.common.GiftWasPromisedCondition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.GiftAbility;
import mage.cards.*;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author Grath
 */
public final class StarfallInvocation extends CardImpl {

    public StarfallInvocation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{W}{W}");
        

        // Gift a card
        this.addAbility(new GiftAbility(this, GiftType.CARD));

        // Destroy all creatures. If the gift was promised, return a creature card put into your graveyard this way to the battlefield under your control.
        this.getSpellAbility().addEffect(new StarfallInvocationEffect());
    }

    private StarfallInvocation(final StarfallInvocation card) {
        super(card);
    }

    @Override
    public StarfallInvocation copy() {
        return new StarfallInvocation(this);
    }
}

class StarfallInvocationEffect extends OneShotEffect {

    StarfallInvocationEffect() {
        super(Outcome.DestroyPermanent);
        staticText = "destroy all creatures. If the gift was promised, return a creature card put into your " +
                "graveyard this way to the battlefield under your control";
    }

    private StarfallInvocationEffect(final StarfallInvocationEffect effect) {
        super(effect);
    }

    @Override
    public StarfallInvocationEffect copy() {
        return new StarfallInvocationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Cards yourCreatureCards = new CardsImpl();

        Player controller = game.getPlayer(source.getControllerId());

        for (Permanent permanent : game.getBattlefield().getActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, source.getControllerId(), source, game)) {
            if (permanent != null && permanent.destroy(source, game, false)) {
                if (controller != null && permanent.isOwnedBy(controller.getId())) {
                    yourCreatureCards.add(permanent);
                }
            }
        }

        if (controller != null && GiftWasPromisedCondition.TRUE.apply(game, source)) {
            game.processAction();
            TargetCard target = new TargetCardInYourGraveyard();
            controller.choose(Outcome.PutCreatureInPlay, yourCreatureCards, target, source, game);
            Card targetCard = game.getCard(target.getFirstTarget());
            if (targetCard != null) {
                controller.moveCards(targetCard, Zone.BATTLEFIELD, source, game);
            }
        }
        return true;
    }
}
