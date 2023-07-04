
package mage.cards.b;

import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ChooseExpansionSetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.cards.ExpansionSet;
import mage.cards.Sets;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author spjspj & L_J
 */
public final class BoosterTutor extends CardImpl {

    public BoosterTutor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{B}");

        // Open a sealed Magic booster pack, reveal the cards, and put one of those cards into your hand.
        this.getSpellAbility().addEffect(new BoosterTutorEffect());
    }

    private BoosterTutor(final BoosterTutor card) {
        super(card);
    }

    @Override
    public BoosterTutor copy() {
        return new BoosterTutor(this);
    }
}

class BoosterTutorEffect extends OneShotEffect {

    public BoosterTutorEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Open a sealed Magic booster pack, reveal the cards, and put one of those cards into your hand";
    }

    public BoosterTutorEffect(final BoosterTutorEffect effect) {
        super(effect);
    }

    @Override
    public BoosterTutorEffect copy() {
        return new BoosterTutorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        ChooseExpansionSetEffect effect = new ChooseExpansionSetEffect(Outcome.UnboostCreature);
        effect.apply(game, source);
        Player controller = game.getPlayer(source.getControllerId());

        String setChosen = null;
        if (effect.getValue("setchosen") != null) {
            setChosen = (String) effect.getValue("setchosen");
        } else if (game.getState().getValue(this.getId() + "_set") != null) {
            setChosen = (String) game.getState().getValue(this.getId() + "_set");
        }

        if (setChosen != null && controller != null) {
            //ExpansionInfo set = ExpansionRepository.instance.getSetByName(setChosen);
            ExpansionSet expansionSet = Sets.findSet(setChosen);
            if (expansionSet != null) {
                List<Card> boosterPack = expansionSet.create15CardBooster();
                if (boosterPack != null) {
                    StringBuilder message = new StringBuilder(controller.getLogName()).append(" opened: ");
                    for (Card card : boosterPack) {
                        message.append(card.getName()).append(" ");
                    }
                    game.informPlayers(message.toString());

                    TargetCard targetCard = new TargetCard(Zone.ALL, new FilterCard());
                    Set<Card> cardsToLoad = new HashSet<Card>(boosterPack);
                    game.loadCards(cardsToLoad, controller.getId());
                    CardsImpl cards = new CardsImpl();
                    cards.addAllCards(boosterPack);
                    if (controller.choose(Outcome.Benefit, cards, targetCard, source, game)) {
                        Card card = game.getCard(targetCard.getFirstTarget());
                        if (card != null) {
                            controller.moveCards(card, Zone.HAND, source, game);
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
}
