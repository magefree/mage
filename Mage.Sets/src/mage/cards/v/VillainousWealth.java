package mage.cards.v;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterNonlandCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInExile;
import mage.target.common.TargetOpponent;
import mage.util.CardUtil;

import java.util.UUID;
import mage.ApprovingObject;

/**
 * @author LevelX2
 */
public final class VillainousWealth extends CardImpl {

    public VillainousWealth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{B}{G}{U}");

        // Target opponent exiles the top X cards of their library. You may cast any number of nonland cards 
        // with converted mana cost X or less from among them without paying their mana cost.
        this.getSpellAbility().addTarget(new TargetOpponent());
        this.getSpellAbility().addEffect(new VillainousWealthEffect());

    }

    private VillainousWealth(final VillainousWealth card) {
        super(card);
    }

    @Override
    public VillainousWealth copy() {
        return new VillainousWealth(this);
    }
}

class VillainousWealthEffect extends OneShotEffect {

    public VillainousWealthEffect() {
        super(Outcome.PlayForFree);
        this.staticText = "Target opponent exiles the top X cards of their library. "
                + "You may cast any number of spells with mana value X "
                + "or less from among them without paying their mana costs";
    }

    public VillainousWealthEffect(final VillainousWealthEffect effect) {
        super(effect);
    }

    @Override
    public VillainousWealthEffect copy() {
        return new VillainousWealthEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject mageObject = game.getObject(source.getSourceId());
        if (controller != null && mageObject != null) {
            Player player = game.getPlayer(targetPointer.getFirst(game, source));
            FilterCard filter = new FilterNonlandCard();
            filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, source.getManaCostsToPay().getX() + 1));
            UUID exileId = CardUtil.getCardExileZoneId(game, source);
            if (player != null) {
                Cards cardsToExile = new CardsImpl();
                cardsToExile.addAll(player.getLibrary().getTopCards(game, source.getManaCostsToPay().getX()));
                controller.moveCards(cardsToExile, Zone.EXILED, source, game);
                if (controller.chooseUse(Outcome.PlayForFree, "Cast cards exiled with " + mageObject.getLogName()
                        + "  without paying its mana cost?", source, game)) {
                    OuterLoop:
                    while (cardsToExile.count(filter, game) > 0) {
                        if (!controller.canRespond()) {
                            return false;
                        }
                        TargetCardInExile target = new TargetCardInExile(0, 1, filter, exileId, false);
                        target.setNotTarget(true);
                        while (controller.canRespond()
                                && cardsToExile.count(filter, game) > 0
                                && controller.choose(Outcome.PlayForFree, cardsToExile, target, game)) {
                            Card card = game.getCard(target.getFirstTarget());
                            if (card != null) {
                                game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), Boolean.TRUE);
                                Boolean cardWasCast = controller.cast(controller.chooseAbilityForCast(card, game, true),
                                        game, true, new ApprovingObject(source, game));
                                game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), null);
                                if (cardWasCast) {
                                    cardsToExile.remove(card);
                                }
                            } else {
                                break OuterLoop;
                            }
                            target.clearChosen();
                        }
                    }
                }
            }
            return true;
        }

        return false;
    }
}
