package mage.cards.g;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.choices.Choice;
import mage.choices.ChoiceCreatureType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author LevelX2
 */
public final class GraveSifter extends CardImpl {

    public GraveSifter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{G}");
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(5);
        this.toughness = new MageInt(7);

        // When Grave Sifter enters the battlefield, each player chooses a creature type and returns any number of cards of that type from their graveyard to their hand.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GraveSifterEffect(), false));
    }

    private GraveSifter(final GraveSifter card) {
        super(card);
    }

    @Override
    public GraveSifter copy() {
        return new GraveSifter(this);
    }
}

class GraveSifterEffect extends OneShotEffect {

    public GraveSifterEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "each player chooses a creature type and returns any number of cards of that type from their graveyard to their hand";
    }

    public GraveSifterEffect(final GraveSifterEffect effect) {
        super(effect);
    }

    @Override
    public GraveSifterEffect copy() {
        return new GraveSifterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Choice typeChoice = new ChoiceCreatureType(game.getObject(source));
        typeChoice.setMessage("Choose creature type to return cards from your graveyard");
        Player controller = game.getPlayer(source.getControllerId());
        Set<Card> toHand = new HashSet<>();
        if (controller != null) {
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    typeChoice.clearChoice();
                    if (player.choose(outcome, typeChoice, game)) {
                        game.informPlayers(player.getLogName() + " has chosen: " + typeChoice.getChoice());
                        FilterCard filter = new FilterCreatureCard("creature cards with creature type " + typeChoice.getChoice() + " from your graveyard");
                        filter.add(SubType.byDescription(typeChoice.getChoice()).getPredicate());
                        Target target = new TargetCardInYourGraveyard(0, Integer.MAX_VALUE, filter);
                        player.chooseTarget(outcome, target, source, game);
                        toHand.addAll(new CardsImpl(target.getTargets()).getCards(game));
                    }

                }
            }

            // must happen simultaneously Rule 101.4
            controller.moveCards(toHand, Zone.HAND, source, game, false, false, true, null);
            return true;
        }
        return false;
    }
}
