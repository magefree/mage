package mage.cards.s;

import mage.ApprovingObject;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterNonlandCard;
import mage.game.Game;
import mage.game.permanent.token.IcyManalithToken;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInLibrary;

import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SvellaIceShaper extends CardImpl {

    public SvellaIceShaper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{G}");

        this.addSuperType(SuperType.LEGENDARY);
        this.addSuperType(SuperType.SNOW);
        this.subtype.add(SubType.TROLL);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // {3}, {T}: Create a colorless snow artifact token named Icy Manalith with "{T}: Add one mana of any color."
        Ability ability = new SimpleActivatedAbility(
                new CreateTokenEffect(new IcyManalithToken()), new GenericManaCost(3)
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        // {6}{R}{G}, {T}: Look at the top four cards of your library. You may cast a spell from among them without paying its mana cost. Put the rest on the bottom of your library in a random order.
        ability = new SimpleActivatedAbility(new SvellaIceShaperEffect(), new ManaCostsImpl("{6}{R}{G}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private SvellaIceShaper(final SvellaIceShaper card) {
        super(card);
    }

    @Override
    public SvellaIceShaper copy() {
        return new SvellaIceShaper(this);
    }
}

class SvellaIceShaperEffect extends OneShotEffect {

    SvellaIceShaperEffect() {
        super(Outcome.Benefit);
        staticText = "Look at the top four cards of your library." +
                " You may cast a spell from among them without paying its mana cost. " +
                "Put the rest on the bottom of your library in a random order.";
    }

    private SvellaIceShaperEffect(final SvellaIceShaperEffect effect) {
        super(effect);
    }

    @Override
    public SvellaIceShaperEffect copy() {
        return new SvellaIceShaperEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Set<Card> cardsSet = controller.getLibrary().getTopCards(game, 4);
        Cards cards = new CardsImpl(cardsSet);
        TargetCard target = new TargetCardInLibrary(0, 1,
                new FilterNonlandCard("card to cast without paying its mana cost"));
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
