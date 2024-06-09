
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Abilities;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.SearchEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.EmbalmAbility;
import mage.abilities.keyword.EternalizeAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.StackAbility;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author spjspj
 */
public final class VizierOfTheAnointed extends CardImpl {

    private static final FilterCard filter = new FilterCard("a creature card with eternalize or embalm");

    static {
        filter.add(CardType.CREATURE.getPredicate());
        filter.add(new VizierOfTheAnointedAbilityPredicate());
    }

    public VizierOfTheAnointed(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // When Vizier of the Anointed enters the battlefield, you may search your library for a creature card with eternalize or embalm, put that card into your graveyard, then shuffle your library.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SearchLibraryPutInGraveyard(filter), true));

        // Whenever you activate an eternalize or embalm ability, draw a card.
        this.addAbility(new VizierOfTheAnointedTriggeredAbility());

    }

    private VizierOfTheAnointed(final VizierOfTheAnointed card) {
        super(card);
    }

    @Override
    public VizierOfTheAnointed copy() {
        return new VizierOfTheAnointed(this);
    }
}

class VizierOfTheAnointedAbilityPredicate implements Predicate<MageObject> {

    public VizierOfTheAnointedAbilityPredicate() {
    }

    @Override
    public boolean apply(MageObject input, Game game) {
        Abilities<Ability> abilities = input.getAbilities();
        for (int i = 0; i < abilities.size(); i++) {
            if (abilities.get(i) instanceof EmbalmAbility) {
                return true;
            }
            if (abilities.get(i) instanceof EternalizeAbility) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Creature card with Eternalize or Embalm";
    }
}

class VizierOfTheAnointedTriggeredAbility extends TriggeredAbilityImpl {

    VizierOfTheAnointedTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), false);
    }

    private VizierOfTheAnointedTriggeredAbility(final VizierOfTheAnointedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public VizierOfTheAnointedTriggeredAbility copy() {
        return new VizierOfTheAnointedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ACTIVATED_ABILITY;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getPlayerId().equals(getControllerId())) {
            StackAbility stackAbility = (StackAbility) game.getStack().getStackObject(event.getSourceId());
            if (stackAbility.getStackAbility() instanceof EternalizeAbility
                    || stackAbility.getStackAbility() instanceof EmbalmAbility) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever you activate an eternalize or embalm ability, draw a card.";
    }
}

class SearchLibraryPutInGraveyard extends SearchEffect {

    public SearchLibraryPutInGraveyard(FilterCard filter) {
        super(new TargetCardInLibrary(filter), Outcome.Neutral);
        staticText = "search your library for a creature card with eternalize or embalm, put that card into your graveyard, then shuffle.";
    }

    private SearchLibraryPutInGraveyard(final SearchLibraryPutInGraveyard effect) {
        super(effect);
    }

    @Override
    public SearchLibraryPutInGraveyard copy() {
        return new SearchLibraryPutInGraveyard(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            if (controller.searchLibrary(target, source, game)) {
                if (!target.getTargets().isEmpty()) {
                    Card card = controller.getLibrary().getCard(target.getFirstTarget(), game);
                    if (card != null) {
                        controller.moveCards(card, Zone.GRAVEYARD, source, game);
                    }
                }
            }
            controller.shuffleLibrary(source, game);
            return true;
        }
        return false;
    }
}
