package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceCardType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;

import java.util.Arrays;
import java.util.Locale;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author nantuko
 */
public final class CreepingRenaissance extends CardImpl {

    public CreepingRenaissance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{G}{G}");

        // Choose a permanent type. Return all cards of the chosen type from your graveyard to your hand.
        this.getSpellAbility().addEffect(new CreepingRenaissanceEffect());

        // Flashback {5}{G}{G}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl("{5}{G}{G}")));
    }

    private CreepingRenaissance(final CreepingRenaissance card) {
        super(card);
    }

    @Override
    public CreepingRenaissance copy() {
        return new CreepingRenaissance(this);
    }
}

class CreepingRenaissanceEffect extends OneShotEffect {

    public CreepingRenaissanceEffect() {
        super(Outcome.Detriment);
        staticText = "Choose a permanent type. Return all cards of the chosen type from your graveyard to your hand";
    }

    public CreepingRenaissanceEffect(final CreepingRenaissanceEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Choice typeChoice = new ChoiceCardType(true, Arrays.stream(CardType.values()).filter(CardType::isPermanentType).collect(Collectors.toList()));
        typeChoice.setMessage("Choose a permanent type");

        if (!controller.choose(Outcome.ReturnToHand, typeChoice, game)) {
            return false;
        }
        String typeName = typeChoice.getChoice();
        CardType chosenType = CardType.fromString(typeChoice.getChoice());
        if (chosenType == null) {
            return false;
        }
        FilterCard filter = new FilterCard(chosenType.toString().toLowerCase(Locale.ENGLISH) + " card");
        filter.add(chosenType.getPredicate());
        return controller.moveCards(controller.getGraveyard().getCards(filter, controller.getId(), source, game), Zone.HAND, source, game);
    }

    @Override
    public CreepingRenaissanceEffect copy() {
        return new CreepingRenaissanceEffect(this);
    }
}
