package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInGraveyard;
import mage.target.common.TargetCardInHand;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

/**
 * "Put a {filter} card from your graveyard or your hand onto the battlefield.
 *
 * @author TheElk801, Alex-Vasile
 */
public class PutCardFromHandOrGraveyardOntoBattlefieldEffect extends OneShotEffect {

    public static final String cardkey = "cardPutOnBattlefield";
    private final FilterCard filterCard;
    private final boolean tapped;
    private final Effect effectToApplyOnPermanent;

    public PutCardFromHandOrGraveyardOntoBattlefieldEffect(FilterCard filterCard) {
        this(filterCard, false);
    }


    public PutCardFromHandOrGraveyardOntoBattlefieldEffect(FilterCard filterCard, boolean tapped) {
        this(filterCard, tapped, null);
    }

    /**
     *
     * @param filterCard                Filter used to filter which cards are valid choices. (no default)
     * @param tapped                    If the permanent should enter the battlefield tapped (default is False)
     * @param effectToApplyOnPermanent  An effect to apply to the permanent after it enters (default null)
     *                                  See "Swift Warkite" or "Nissa of Shadowed Boughs".
     */
    public PutCardFromHandOrGraveyardOntoBattlefieldEffect(FilterCard filterCard, boolean tapped, Effect effectToApplyOnPermanent) {
        super(filterCard instanceof FilterCreatureCard ? Outcome.PutCreatureInPlay : Outcome.PutCardInPlay);
        this.filterCard = filterCard;
        this.tapped = tapped;
        this.effectToApplyOnPermanent = effectToApplyOnPermanent;
    }

    private PutCardFromHandOrGraveyardOntoBattlefieldEffect(final PutCardFromHandOrGraveyardOntoBattlefieldEffect effect) {
        super(effect);
        this.filterCard = effect.filterCard;
        this.tapped = effect.tapped;
        if (effect.effectToApplyOnPermanent != null) {
            this.effectToApplyOnPermanent = effect.effectToApplyOnPermanent.copy();
        } else {
            this.effectToApplyOnPermanent = null;
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        boolean cardsAvailableInGraveyard = controller.getGraveyard().count(filterCard, game) > 0;
        boolean cardsAvailableInHand = controller.getHand().count(filterCard, game) > 0;
        if (!cardsAvailableInHand && !cardsAvailableInGraveyard) {
            return false;
        }

        TargetCard targetCard;
        Zone zone;
        if (cardsAvailableInHand && cardsAvailableInGraveyard) {
            boolean choseHand = controller.chooseUse(outcome, "Choose a card in your hand or your graveyard?",
                    null, "Hand", "Graveyard", source, game);
            zone = choseHand ? Zone.HAND : Zone.GRAVEYARD;
        } else {
            zone = cardsAvailableInHand ? Zone.HAND : Zone.GRAVEYARD;
        }

        if (zone == Zone.HAND) {
            targetCard = new TargetCardInHand(0, 1, StaticFilters.FILTER_CARD_ARTIFACT);
            controller.choose(outcome, controller.getHand(), targetCard, game);
        } else {
            targetCard = new TargetCardInGraveyard(0, 1, StaticFilters.FILTER_CARD_ARTIFACT);
            controller.choose(outcome, controller.getGraveyard(), targetCard, game);
        }
        Card card = game.getCard(targetCard.getFirstTarget());

        if (card == null || !controller.moveCards(card, Zone.BATTLEFIELD, source, game, tapped, false, false, null)) {
            return false;
        }

        if (effectToApplyOnPermanent != null) {
            effectToApplyOnPermanent.setTargetPointer(new FixedTarget(card.getId()));
            effectToApplyOnPermanent.apply(game, source);
        }
        return true;
    }

    @Override
    public PutCardFromHandOrGraveyardOntoBattlefieldEffect copy() {
        return new PutCardFromHandOrGraveyardOntoBattlefieldEffect(this);
    }

    @Override
    public String getText(Mode mode) {
        return "you may put " + CardUtil.addArticle(this.filterCard.getMessage()) +
                " from your hand or graveyard onto the battlefield" +
                (this.tapped ? " tapped" : "") +
                (effectToApplyOnPermanent == null ? "" : ". " + effectToApplyOnPermanent.getText(mode));
    }
}
