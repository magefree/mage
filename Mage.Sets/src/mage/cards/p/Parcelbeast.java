package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.MutateAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Parcelbeast extends CardImpl {

    public Parcelbeast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{U}");

        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Mutate {G}{U}
        this.addAbility(new MutateAbility(this, "{G}{U}"));

        // {1}, {T}: Look at the top card of your library. If it's a land card, you may put it onto the battlefield. If you don't put the card onto the battlefield, put it into your hand.
        Ability ability = new SimpleActivatedAbility(new ParcelbeastEffect(), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private Parcelbeast(final Parcelbeast card) {
        super(card);
    }

    @Override
    public Parcelbeast copy() {
        return new Parcelbeast(this);
    }
}

class ParcelbeastEffect extends OneShotEffect {

    ParcelbeastEffect() {
        super(Outcome.Benefit);
        staticText = "look at the top card of your library. " +
                "If it's a land card, you may put it onto the battlefield. " +
                "If you don't put the card onto the battlefield, put it into your hand";
    }

    private ParcelbeastEffect(final ParcelbeastEffect effect) {
        super(effect);
    }

    @Override
    public ParcelbeastEffect copy() {
        return new ParcelbeastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Card card = player.getLibrary().getFromTop(game);
        if (card == null) {
            return false;
        }
        player.lookAtCards("", card, game);
        if (card.isLand(game) && player.chooseUse(
                outcome, "Put " + card.getName() + " onto the battlefield?",
                "(otherwise put it into your hand", "To battlefield",
                "To hand", source, game)) {
            player.moveCards(card, Zone.BATTLEFIELD, source, game);
        } else {
            player.moveCards(card, Zone.HAND, source, game);
        }
        return true;
    }
}
