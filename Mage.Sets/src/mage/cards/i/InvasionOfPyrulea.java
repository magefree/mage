package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SiegeAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InvasionOfPyrulea extends CardImpl {

    public InvasionOfPyrulea(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.BATTLE}, "{G}{U}");

        this.subtype.add(SubType.SIEGE);
        this.setStartingDefense(4);
        this.secondSideCardClazz = mage.cards.g.GargantuanSlabhorn.class;

        // (As a Siege enters, choose an opponent to protect it. You and others can attack it. When it's defeated, exile it, then cast it transformed.)
        this.addAbility(new SiegeAbility());

        // When Invasion of Pyrulea enters the battlefield, scry 3, then reveal the top card of your library. If it's a land or double-faced card, draw a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new InvasionOfPyruleaEffect()));
    }

    private InvasionOfPyrulea(final InvasionOfPyrulea card) {
        super(card);
    }

    @Override
    public InvasionOfPyrulea copy() {
        return new InvasionOfPyrulea(this);
    }
}

class InvasionOfPyruleaEffect extends OneShotEffect {

    InvasionOfPyruleaEffect() {
        super(Outcome.Benefit);
        staticText = "scry 3, then reveal the top card of your library. If it's a land or double-faced card, draw a card";
    }

    private InvasionOfPyruleaEffect(final InvasionOfPyruleaEffect effect) {
        super(effect);
    }

    @Override
    public InvasionOfPyruleaEffect copy() {
        return new InvasionOfPyruleaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        player.scry(3, source, game);
        Card card = player.getLibrary().getFromTop(game);
        player.revealCards(source, new CardsImpl(card), game);
        if (card != null && (card.isLand(game) || card instanceof ModalDoubleFacedCard || card.getSecondCardFace() != null)) {
            player.drawCards(1, source, game);
        }
        return true;
    }
}
