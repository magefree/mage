package mage.cards.h;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DontUntapInControllersUntapStepAllEffect;
import mage.cards.*;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

import java.util.UUID;
import mage.ApprovingObject;

/**
 * @author ciaccona007
 */
public final class HazoretsUndyingFury extends CardImpl {

    public HazoretsUndyingFury(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{R}{R}");

        //Shuffle your library, then exile the top four cards.
        //You may cast any number of nonland cards with converted mana
        //cost 5 or less from among them without paying their mana costs.
        getSpellAbility().addEffect(new HazoretsUndyingFuryEffect());

        //Land you control don't untap during your next untap step.
        this.getSpellAbility().addEffect(new DontUntapInControllersUntapStepAllEffect(
                Duration.UntilYourNextTurn, TargetController.YOU,
                new FilterControlledLandPermanent("Lands you control"))
                .setText("Lands you control don't untap during your next untap phase"));
    }

    public HazoretsUndyingFury(final HazoretsUndyingFury card) {
        super(card);
    }

    @Override
    public HazoretsUndyingFury copy() {
        return new HazoretsUndyingFury(this);
    }
}

class HazoretsUndyingFuryEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard(
            "nonland cards with converted mana cost 5 or less");

    static {
        filter.add(Predicates.not(CardType.LAND.getPredicate()));
        filter.add(new ConvertedManaCostPredicate(ComparisonType.FEWER_THAN, 6));
    }

    public HazoretsUndyingFuryEffect() {
        super(Outcome.PlayForFree);
        this.staticText = "Shuffle your library, then exile the top four cards. "
                + "You may cast any number of nonland cards with converted mana "
                + "cost 5 or less from among them without paying their mana costs";
    }

    public HazoretsUndyingFuryEffect(final HazoretsUndyingFuryEffect effect) {
        super(effect);
    }

    @Override
    public HazoretsUndyingFuryEffect copy() {
        return new HazoretsUndyingFuryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null
                && sourceObject != null) {
            controller.shuffleLibrary(source, game);
            // move cards from library to exile
            controller.moveCardsToExile(controller.getLibrary().getTopCards(game, 4),
                    source, game, true, source.getSourceId(), sourceObject.getIdName());
            // cast the possible cards without paying the mana
            ExileZone hazoretsUndyingFuryExileZone = game.getExile().getExileZone(source.getSourceId());
            Cards cardsToCast = new CardsImpl();
            if (hazoretsUndyingFuryExileZone == null) {
                return true;
            }
            cardsToCast.addAll(hazoretsUndyingFuryExileZone.getCards(filter,
                    source.getSourceId(), source.getControllerId(), game));
            while (controller.canRespond() && !cardsToCast.isEmpty()) {
                if (!controller.chooseUse(Outcome.PlayForFree,
                        "Cast (another) a card exiled with "
                                + sourceObject.getLogName() + " without paying its mana cost?", source, game)) {
                    break;
                }
                TargetCard targetCard = new TargetCard(1, Zone.EXILED,
                        new FilterCard("nonland card to cast for free"));
                if (controller.choose(Outcome.PlayForFree, cardsToCast, targetCard, game)) {
                    Card card = game.getCard(targetCard.getFirstTarget());
                    if (card != null) {
                        game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), Boolean.TRUE);
                        Boolean cardWasCast = controller.cast(controller.chooseAbilityForCast(card, game, true),
                                game, true, new ApprovingObject(source, game));
                        game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), null);
                        cardsToCast.remove(card);
                        if (!cardWasCast) {
                            game.informPlayer(controller, "You're not able to cast "
                                    + card.getIdName() + " or you canceled the casting.");
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
}
