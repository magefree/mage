package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.token.IcyManalithToken;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SvellaIceShaper extends CardImpl {

    public SvellaIceShaper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.supertype.add(SuperType.SNOW);
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
        ability = new SimpleActivatedAbility(new SvellaIceShaperEffect(), new ManaCostsImpl<>("{6}{R}{G}"));
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
        Cards cards = new CardsImpl(controller.getLibrary().getTopCards(game, 4));
        CardUtil.castSpellWithAttributesForFree(controller, source, game, cards, StaticFilters.FILTER_CARD);
        cards.retainZone(Zone.LIBRARY, game);
        controller.putCardsOnBottomOfLibrary(cards, game, source, false);
        return true;
    }
}
