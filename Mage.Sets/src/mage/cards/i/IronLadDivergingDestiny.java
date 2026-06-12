package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.Game;
import mage.players.Player;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.LookAtTopCardOfLibraryAnyTimeEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;

/**
 *
 * @author muz
 */
public final class IronLadDivergingDestiny extends CardImpl {

    public IronLadDivergingDestiny(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // You may look at the top card of your library any time.
        this.addAbility(new SimpleStaticAbility(new LookAtTopCardOfLibraryAnyTimeEffect()));

        // {T}: Reveal the top card of your library. If it's an artifact card, draw a card.
        this.addAbility(new SimpleActivatedAbility(new IronLadDivergingDestinyEffect(), new TapSourceCost()));
    }

    private IronLadDivergingDestiny(final IronLadDivergingDestiny card) {
        super(card);
    }

    @Override
    public IronLadDivergingDestiny copy() {
        return new IronLadDivergingDestiny(this);
    }
}

class IronLadDivergingDestinyEffect extends OneShotEffect {

    IronLadDivergingDestinyEffect() {
        super(Outcome.Benefit);
        staticText = "Reveal the top card of your library. If it's an artifact card, draw a card";
    }

    private IronLadDivergingDestinyEffect(final IronLadDivergingDestinyEffect effect) {
        super(effect);
    }

    @Override
    public IronLadDivergingDestinyEffect copy() {
        return new IronLadDivergingDestinyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Card card = player.getLibrary().getFromTop(game);
        if (card == null) {
            return true;
        }
        player.revealCards(source, new CardsImpl(card), game);
        if (card.isArtifact(game)) {
            player.drawCards(1, source, game);
        }
        return true;
    }
}
