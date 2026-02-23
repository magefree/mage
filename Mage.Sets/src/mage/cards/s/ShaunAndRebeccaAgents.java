package mage.cards.s;

import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.*;
import mage.constants.*;
import mage.abilities.keyword.VigilanceAbility;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author jantizio
 */
public final class ShaunAndRebeccaAgents extends CardImpl {

    public ShaunAndRebeccaAgents(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{W}{U}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ASSASSIN);
        this.subtype.add(SubType.SCIENTIST);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // When Shaun | Rebecca, Agents enters the battlefield, search your graveyard, hand, and library for a card named The Animus and put it onto the battlefield, then shuffle.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ShaunAndRebeccaAgentsEffect(), false));

        // {T}: Add {C}. When you do, mill two cards.
        Ability ability = new ColorlessManaAbility();
        ability.addEffect(new ShaunAndRebeccaMillEffect());
        this.addAbility(ability);
    }

    private ShaunAndRebeccaAgents(final ShaunAndRebeccaAgents card) {
        super(card);
    }

    @Override
    public ShaunAndRebeccaAgents copy() {
        return new ShaunAndRebeccaAgents(this);
    }
}


class ShaunAndRebeccaAgentsEffect extends OneShotEffect {
    private static final String animus = "The Animus";

    ShaunAndRebeccaAgentsEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "search your graveyard, hand, and library for a card named The Animus" +
                " and put it onto the battlefield, then shuffle.";
    }

    private ShaunAndRebeccaAgentsEffect(final mage.cards.s.ShaunAndRebeccaAgentsEffect effect) {
        super(effect);
    }

    @Override
    public mage.cards.s.ShaunAndRebeccaAgentsEffect copy() {
        return new mage.cards.s.ShaunAndRebeccaAgentsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Card animusCard = null;
        FilterCard filter = new FilterCard("card named The Animus");
        filter.add(new NamePredicate(animus));
        TargetCardInLibrary libraryTarget = new TargetCardInLibrary(filter);
        if (controller.searchLibrary(libraryTarget, source, game)) {
            for (UUID id : libraryTarget.getTargets()) {
                animusCard = game.getCard(id);
            }
        }

        if (animusCard == null) {
            FilterCard filter2 = new FilterCard("card from your hand named The Animus");
            filter2.add(new NamePredicate(animus));
            Cards animusList = new CardsImpl();
            animusList.addAllCards(controller.getHand().getCards(filter, source.getControllerId(), source, game));
            Set<Card> animusListInGraveyard = controller.getGraveyard().getCards(filter, source.getControllerId(), source, game);
            TargetCard target;
            if (animusListInGraveyard.isEmpty()) {
                // Hand is a hidden zone - you may fail to find a Spear of Leonidas that's in your hand.
                // 701.19b. If a player is searching a hidden zone for cards with a stated quality, such as a card with a
                //   certain card type or color, that player isn't required to find some or all of those cards even if they're
                //   present in that zone.
                // This is true even if your hand is revealed:
                // 400.2. ... Hidden zones are zones in which not all players can be expected to see the cards' faces.
                //   Library and hand are hidden zones, even if all the cards in one such zone happen to be revealed.
                target = new TargetCard(0, 1, Zone.HAND, filter);
            } else {
                animusList.addAllCards(animusListInGraveyard);
                // You cannot fail to find a spear if there is one in your graveyard, because the graveyard is not hidden.
                target = new TargetCard(1, 1, Zone.HAND, filter);
            }
            target.withNotTarget(true);
            if (!animusList.isEmpty()) {
                controller.choose(outcome, animusList, target, source, game);
                for (UUID id : target.getTargets()) {
                    animusCard = game.getCard(id);
                }
            }
        }

        if (animusCard != null) {
            controller.moveCards(animusCard, Zone.BATTLEFIELD, source, game);
        }
        controller.shuffleLibrary(source, game);
        return true;
    }
}

class ShaunAndRebeccaMillEffect extends OneShotEffect {

    ShaunAndRebeccaMillEffect() {
        super(Outcome.Neutral);
        staticText = "When you do, mill two cards";
    }

    private ShaunAndRebeccaMillEffect(final ShaunAndRebeccaMillEffect effect) {
        super(effect);
    }

    @Override
    public ShaunAndRebeccaMillEffect copy() {
        return new ShaunAndRebeccaMillEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(new MillCardsControllerEffect(2), false, "Mill two cards");
        game.fireReflexiveTriggeredAbility(ability, source);
        return true;
    }
}