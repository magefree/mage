package mage.cards.i;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.token.TreasureToken;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IgnisScientia extends CardImpl {

    public IgnisScientia(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Ignis Scientia enters, look at the top six cards of your library. You may put a land card from among them onto the battlefield tapped. Put the rest on the bottom of your library in a random order.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new LookLibraryAndPickControllerEffect(
                6, 1, StaticFilters.FILTER_CARD_LAND_A,
                PutCards.BATTLEFIELD_TAPPED, PutCards.BOTTOM_RANDOM
        )));

        // I've Come Up with a New Recipe! -- {1}{G}{U}, {T}: Exile target card from a graveyard. If a creature card was exiled this way, create a Food token.
        Ability ability = new SimpleActivatedAbility(new IgnisScientiaEffect(), new ManaCostsImpl<>("{1}{G}{U}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCardInGraveyard());
        this.addAbility(ability.withFlavorWord("I've Come Up with a New Recipe!"));
    }

    private IgnisScientia(final IgnisScientia card) {
        super(card);
    }

    @Override
    public IgnisScientia copy() {
        return new IgnisScientia(this);
    }
}

class IgnisScientiaEffect extends OneShotEffect {

    IgnisScientiaEffect() {
        super(Outcome.Benefit);
        staticText = "exile target card from a graveyard. If a creature card was exiled this way, create a Food token";
    }

    private IgnisScientiaEffect(final IgnisScientiaEffect effect) {
        super(effect);
    }

    @Override
    public IgnisScientiaEffect copy() {
        return new IgnisScientiaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (player == null || card == null) {
            return false;
        }
        boolean flag = card.isCreature(game);
        player.moveCards(card, Zone.EXILED, source, game);
        if (flag) {
            new TreasureToken().putOntoBattlefield(1, game, source);
        }
        return true;
    }
}
