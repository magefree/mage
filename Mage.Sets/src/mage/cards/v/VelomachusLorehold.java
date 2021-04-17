package mage.cards.v;

import mage.ApprovingObject;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.*;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInLibrary;

import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VelomachusLorehold extends CardImpl {

    public VelomachusLorehold(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{R}{W}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELDER);
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever Velomachus Lorehold attacks, look at the top seven cards of your library. You may cast an instant or sorcery spell with mana value less than or equal to Velomachus Lorehold's power from among them without paying its mana cost. Put the rest on the bottom of your library in a random order.
        this.addAbility(new AttacksTriggeredAbility(new VelomachusLoreholdEffect(), false));
    }

    private VelomachusLorehold(final VelomachusLorehold card) {
        super(card);
    }

    @Override
    public VelomachusLorehold copy() {
        return new VelomachusLorehold(this);
    }
}

class VelomachusLoreholdEffect extends OneShotEffect {

    VelomachusLoreholdEffect() {
        super(Outcome.Benefit);
        staticText = "look at the top seven cards of your library. You may cast an instant or sorcery spell " +
                "with mana value less than or equal to {this}'s power from among them without paying its mana cost. " +
                "Put the rest on the bottom of your library in a random order";
    }

    private VelomachusLoreholdEffect(final VelomachusLoreholdEffect effect) {
        super(effect);
    }

    @Override
    public VelomachusLoreholdEffect copy() {
        return new VelomachusLoreholdEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = source.getSourcePermanentOrLKI(game);
        if (controller == null || permanent == null) {
            return false;
        }
        Set<Card> cardsSet = controller.getLibrary().getTopCards(game, 7);
        Cards cards = new CardsImpl(cardsSet);
        FilterCard filter = new FilterInstantOrSorceryCard(
                "instant or sorcery card with mana value " + permanent.getPower().getValue() + " or less"
        );
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, permanent.getPower().getValue() + 1));
        TargetCard target = new TargetCardInLibrary(0, 1, filter);
        controller.choose(Outcome.PlayForFree, cards, target, game);
        Card card = controller.getLibrary().getCard(target.getFirstTarget(), game);
        if (card == null) {
            controller.putCardsOnBottomOfLibrary(cards, game, source, false);
            return true;
        }
        game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), Boolean.TRUE);
        Boolean cardWasCast = controller.cast(controller.chooseAbilityForCast(card, game, true),
                game, true, new ApprovingObject(source, game));
        game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), null);
        if (cardWasCast) {
            cards.remove(card);
        }
        controller.putCardsOnBottomOfLibrary(cards, game, source, false);
        return true;
    }
}
