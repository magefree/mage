
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.choices.Choice;
import mage.choices.ChoiceCreatureType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class ElvishSoultiller extends CardImpl {

    public ElvishSoultiller(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.MUTANT);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // When Elvish Soultiller dies, choose a creature type. Shuffle all creature cards of that type from your graveyard into your library.
        addAbility(new DiesTriggeredAbility(new ElvishSoultillerEffect()));

    }

    public ElvishSoultiller(final ElvishSoultiller card) {
        super(card);
    }

    @Override
    public ElvishSoultiller copy() {
        return new ElvishSoultiller(this);
    }
}

class ElvishSoultillerEffect extends OneShotEffect {

    public ElvishSoultillerEffect() {
        super(Outcome.Benefit);
        this.staticText = "choose a creature type. Shuffle all creature cards of that type from your graveyard into your library";
    }

    public ElvishSoultillerEffect(final ElvishSoultillerEffect effect) {
        super(effect);
    }

    @Override
    public ElvishSoultillerEffect copy() {
        return new ElvishSoultillerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject mageObject = game.getObject(source.getSourceId());
        Choice typeChoice = new ChoiceCreatureType(mageObject);
        if (controller != null && mageObject != null && controller.choose(outcome, typeChoice, game)) {
            if (!game.isSimulation()) {
                game.informPlayers(mageObject.getName() + ": " + controller.getLogName() + " has chosen " + typeChoice.getChoice());
            }
            Cards cardsToLibrary = new CardsImpl();
            FilterCreatureCard filter = new FilterCreatureCard();
            filter.add(new SubtypePredicate(SubType.byDescription(typeChoice.getChoice())));
            cardsToLibrary.addAll(controller.getGraveyard().getCards(filter, source.getSourceId(), source.getControllerId(), game));
            controller.putCardsOnTopOfLibrary(cardsToLibrary, game, source, false);
            controller.shuffleLibrary(source, game);
            return true;
        }
        return false;
    }
}
