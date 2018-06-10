
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.RevealLibraryPutIntoHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.repository.CardRepository;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class WoodSage extends CardImpl {

    public WoodSage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {tap}: Name a creature card. Reveal the top four cards of your library and put all of them with that name into your hand. Put the rest into your graveyard.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new WoodSageEffect(), new TapSourceCost()));

    }

    public WoodSage(final WoodSage card) {
        super(card);
    }

    @Override
    public WoodSage copy() {
        return new WoodSage(this);
    }
}

class WoodSageEffect extends OneShotEffect {

    public WoodSageEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Name a creature card. Reveal the top four cards of your library and put all of them with that name into your hand. Put the rest into your graveyard";
    }

    public WoodSageEffect(final WoodSageEffect effect) {
        super(effect);
    }

    @Override
    public WoodSageEffect copy() {
        return new WoodSageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (controller != null && sourceObject != null) {
            Choice cardChoice = new ChoiceImpl();
            cardChoice.setChoices(CardRepository.instance.getCreatureNames());
            cardChoice.setMessage("Name a creature card");
            if (!controller.choose(Outcome.Detriment, cardChoice, game)) {
                return false;
            }
            String cardName = cardChoice.getChoice();
            if (!game.isSimulation()) {
                game.informPlayers(sourceObject.getLogName() + ", named card: [" + cardName + ']');
            }

            FilterCreatureCard filter = new FilterCreatureCard("all of them with that name");
            filter.add(new NamePredicate(cardName));
            new RevealLibraryPutIntoHandEffect(4, filter, Zone.GRAVEYARD).apply(game, source);

            return true;
        }

        return false;
    }
}
