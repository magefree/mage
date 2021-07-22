package mage.cards.e;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

import java.util.UUID;
import mage.ApprovingObject;

/**
 * @author LevelX2
 */
public final class EpicExperiment extends CardImpl {

    public EpicExperiment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{U}{R}");

        // Exile the top X cards of your library. For each instant and sorcery card with
        // converted mana cost X or less among them, you may cast that card without paying
        // its mana cost. Then put all cards exiled this way that weren't cast into your graveyard.
        this.getSpellAbility().addEffect(new EpicExperimentEffect());
    }

    private EpicExperiment(final EpicExperiment card) {
        super(card);
    }

    @Override
    public EpicExperiment copy() {
        return new EpicExperiment(this);
    }
}

class EpicExperimentEffect extends OneShotEffect {

    public EpicExperimentEffect() {
        super(Outcome.PlayForFree);
        staticText = "Exile the top X cards of your library. For each instant and "
                + "sorcery card with mana value X or less among them, "
                + "you may cast that card without paying its mana cost. Then put all "
                + "cards exiled this way that weren't cast into your graveyard";
    }

    public EpicExperimentEffect(final EpicExperimentEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && sourceObject != null) {
            // move cards from library to exile
            controller.moveCardsToExile(controller.getLibrary().getTopCards(game,
                    source.getManaCostsToPay().getX()), source, game, true,
                    source.getSourceId(), sourceObject.getIdName());
            // cast the possible cards without paying the mana
            ExileZone epicExperimentExileZone = game.getExile().getExileZone(source.getSourceId());
            FilterCard filter = new FilterInstantOrSorceryCard();
            filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN,
                    source.getManaCostsToPay().getX() + 1));
            filter.setMessage("instant and sorcery cards with mana value "
                    + source.getManaCostsToPay().getX() + " or less");
            Cards cardsToCast = new CardsImpl();
            if (epicExperimentExileZone == null) {
                return true;
            }
            cardsToCast.addAll(epicExperimentExileZone.getCards(filter, source.getSourceId(),
                    source.getControllerId(), game));
            while (controller.canRespond() && !cardsToCast.isEmpty()) {
                if (!controller.chooseUse(Outcome.PlayForFree, "Cast (another) a card exiled with "
                        + sourceObject.getLogName() + " without paying its mana cost?", source, game)) {
                    break;
                }
                TargetCard targetCard = new TargetCard(1, Zone.EXILED, new FilterCard(
                        "instant or sorcery card to cast for free"));
                if (controller.choose(Outcome.PlayForFree, cardsToCast, targetCard, game)) {
                    Card card = game.getCard(targetCard.getFirstTarget());
                    if (card != null) {
                        game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), Boolean.TRUE);
                        Boolean cardWasCast = controller.cast(controller.chooseAbilityForCast(card, game, true),
                                game, true, new ApprovingObject(source, game));
                        game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), null);
                        if (!cardWasCast) {
                            game.informPlayer(controller, "You're not able to cast "
                                    + card.getIdName() + " or you canceled the casting.");
                        }
                        cardsToCast.remove(card);
                    } else {
                        break;
                    }
                } else {
                    break;
                }
            }
            // move cards not cast to graveyard
            ExileZone exileZone = game.getExile().getExileZone(source.getSourceId());
            if (exileZone != null) {
                controller.moveCards(exileZone.getCards(game), Zone.GRAVEYARD, source, game);
            }
            return true;
        }

        return false;
    }

    @Override
    public EpicExperimentEffect copy() {
        return new EpicExperimentEffect(this);
    }
}
