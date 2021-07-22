package mage.cards.s;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 * @author MarcoMarin
 */
public final class Sindbad extends CardImpl {

    public Sindbad(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.subtype.add(SubType.HUMAN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {tap}: Draw a card and reveal it. If it isn't a land card, discard it.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new SindbadEffect(), new TapSourceCost()));
    }

    private Sindbad(final Sindbad card) {
        super(card);
    }

    @Override
    public Sindbad copy() {
        return new Sindbad(this);
    }
}

class SindbadEffect extends OneShotEffect {

    SindbadEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Draw a card and reveal it. If it isn't a land card, discard it";
    }

    private SindbadEffect(final SindbadEffect effect) {
        super(effect);
    }

    @Override
    public SindbadEffect copy() {
        return new SindbadEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Card card = controller.getLibrary().getFromTop(game);
        // Gatherer ruling (2007-02-01)
        // If the draw is replaced by another effect, none of the rest of Fa’adiyah Seer’s ability applies,
        // even if the draw is replaced by another draw (such as with Enduring Renewal).
        if (controller.drawCards(1, source, game) != 1) {
            return true;
        }
        controller.revealCards(source, new CardsImpl(card), game);
        if (!card.isLand(game)) {
            controller.discard(card, false, source, game);
        }
        return true;
    }
}
