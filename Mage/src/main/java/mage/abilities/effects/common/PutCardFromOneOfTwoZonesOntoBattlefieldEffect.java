package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CommanderCardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInCommandZone;
import mage.target.common.TargetCardInGraveyard;
import mage.target.common.TargetCardInHand;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

/**
 * "Put a {filter} card from {zone 1} or {zone 2} onto the battlefield.
 *
 * @author TheElk801, Alex-Vasile
 */
public class PutCardFromOneOfTwoZonesOntoBattlefieldEffect extends OneShotEffect {

    private final FilterCard filterCard;
    private final boolean tapped;
    private final Effect effectToApplyOnPermanent;
    private final Zone zone1;
    private final Zone zone2;

    public PutCardFromOneOfTwoZonesOntoBattlefieldEffect(FilterCard filterCard) {
        this(filterCard, false);
    }

    public PutCardFromOneOfTwoZonesOntoBattlefieldEffect(FilterCard filterCard, boolean tapped) {
        this(filterCard, tapped, null);
    }

    /**
     *
     * @param filterCard                Filter used to filter which cards are valid choices. (no default)
     * @param tapped                    If the permanent should enter the battlefield tapped (default is False)
     * @param effectToApplyOnPermanent  An effect to apply to the permanent after it enters (default null)
     *                                  See "Swift Warkite" or "Nissa of Shadowed Boughs".
     * @param zone1                     The first zone to pick from (default of HAND)
     * @param zone2                     The second zone to pick from (defualt of GRAVEYARD)
     */
    public PutCardFromOneOfTwoZonesOntoBattlefieldEffect(FilterCard filterCard, boolean tapped, Effect effectToApplyOnPermanent, Zone zone1, Zone zone2) {
        super(filterCard instanceof FilterCreatureCard ? Outcome.PutCreatureInPlay : Outcome.PutCardInPlay);
        this.filterCard = filterCard;
        this.tapped = tapped;
        this.effectToApplyOnPermanent = effectToApplyOnPermanent;
        this.zone1 = zone1;
        this.zone2 = zone2;
    }

    public PutCardFromOneOfTwoZonesOntoBattlefieldEffect(FilterCard filterCard, boolean tapped, Effect effectToApplyOnPermanent) {
        this(filterCard, tapped, effectToApplyOnPermanent, Zone.HAND, Zone.GRAVEYARD);
    }

    public PutCardFromOneOfTwoZonesOntoBattlefieldEffect(FilterCard filterCard, Zone zone1, Zone zone2) {
        this(filterCard, false, null, zone1, zone2);
    }

    private PutCardFromOneOfTwoZonesOntoBattlefieldEffect(final PutCardFromOneOfTwoZonesOntoBattlefieldEffect effect) {
        super(effect);
        this.filterCard = effect.filterCard;
        this.tapped = effect.tapped;
        this.zone1 = effect.zone1;
        this.zone2 = effect.zone2;
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

        Cards cardsInZone1 = getCardsFromZone(game, controller, zone1);
        Cards cardsInZone2 = getCardsFromZone(game, controller, zone2);

        boolean cardsAvailableInZone1 = cardsInZone1.count(filterCard, game) > 0;
        boolean cardsAvailableInZone2 = cardsInZone2.count(filterCard, game) > 0;
        if (!cardsAvailableInZone1 && !cardsAvailableInZone2) {
            return false;
        }

        boolean choose1stZone;
        if (cardsAvailableInZone1 && cardsAvailableInZone2) {
            choose1stZone = controller.chooseUse(outcome, "Where do you want to chose the card from?",
                    null, zone1.name(), zone2.name(), source, game);
        } else {
            choose1stZone = cardsAvailableInZone1;
        }

        Zone zone = choose1stZone ? zone1 : zone2;
        Cards cards = choose1stZone ? cardsInZone1 : cardsInZone2;
        TargetCard targetCard;

        switch (zone) {
            case HAND:
                targetCard = new TargetCardInHand(filterCard);
                break;
            case GRAVEYARD:
                targetCard = new TargetCardInGraveyard(filterCard);
                break;
            case COMMAND:
                targetCard = new TargetCardInCommandZone(filterCard);
                break;
            default:
                return false;
        }
        controller.choose(outcome, cards, targetCard, game);
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

    private static Cards getCardsFromZone(Game game, Player player, Zone zone) {
        switch (zone) {
            case HAND:
                return player.getHand();
            case COMMAND:
                return new CardsImpl(game.getCommanderCardsFromCommandZone(player, CommanderCardType.ANY));
            case GRAVEYARD:
                return player.getGraveyard();
            default:
                return new CardsImpl();
        }
    }

    @Override
    public PutCardFromOneOfTwoZonesOntoBattlefieldEffect copy() {
        return new PutCardFromOneOfTwoZonesOntoBattlefieldEffect(this);
    }

    @Override
    public String getText(Mode mode) {
        return "you may put " + CardUtil.addArticle(this.filterCard.getMessage()) +
                " from your hand or graveyard onto the battlefield" +
                (this.tapped ? " tapped" : "") +
                (effectToApplyOnPermanent == null ? "" : ". " + effectToApplyOnPermanent.getText(mode));
    }
}
