
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.Card;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Library;
import mage.players.Player;

/**
 *
 * @author TheElk801
 */
public final class Bioplasm extends CardImpl {

    public Bioplasm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");

        this.subtype.add(SubType.OOZE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever Bioplasm attacks, exile the top card of your library. If it's a creature card, Bioplasm gets +X/+Y until end of turn, where X is the exiled creature card's power and Y is its toughness.
        this.addAbility(new AttacksTriggeredAbility(new BioplasmEffect(), false));
    }

    private Bioplasm(final Bioplasm card) {
        super(card);
    }

    @Override
    public Bioplasm copy() {
        return new Bioplasm(this);
    }
}

class BioplasmEffect extends OneShotEffect {

    BioplasmEffect() {
        super(Outcome.Benefit);
        this.staticText = "exile the top card of your library. If it's a creature card, {this} gets +X/+Y until end of turn, where X is the exiled creature card's power and Y is its toughness";
    }

    private BioplasmEffect(final BioplasmEffect effect) {
        super(effect);
    }

    @Override
    public BioplasmEffect copy() {
        return new BioplasmEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Library library = player.getLibrary();
        if (library == null || !library.hasCards()) {
            return false;
        }
        Card card = library.getFromTop(game);
        if (card == null) {
            return false;
        }
        if (player.moveCards(card, Zone.EXILED, source, game) && card.isCreature(game)) {
            game.addEffect(new BoostSourceEffect(card.getPower().getValue(), card.getToughness().getValue(), Duration.EndOfTurn), source);
        }
        return true;
    }
}
