package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FrostAugur extends CardImpl {

    public FrostAugur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}");

        this.addSuperType(SuperType.SNOW);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {S}, {T}: Look at the top card of your library. If it's a snow card, you may reveal it and put it into your hand.
        Ability ability = new SimpleActivatedAbility(new FrostAugurEffect(), new ManaCostsImpl<>("{S}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private FrostAugur(final FrostAugur card) {
        super(card);
    }

    @Override
    public FrostAugur copy() {
        return new FrostAugur(this);
    }
}

class FrostAugurEffect extends OneShotEffect {

    FrostAugurEffect() {
        super(Outcome.Benefit);
        staticText = "Look at the top card of your library. " +
                "If it's a snow card, you may reveal it and put it into your hand.";
    }

    private FrostAugurEffect(final FrostAugurEffect effect) {
        super(effect);
    }

    @Override
    public FrostAugurEffect copy() {
        return new FrostAugurEffect(this);
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
        if (card.isSnow() && player.chooseUse(
                outcome, "Reveal " + card.getName() + " and put it into your hand?", source, game
        )) {
            player.revealCards(source, new CardsImpl(card), game);
            player.moveCards(card, Zone.HAND, source, game);
        }
        return true;
    }
}
