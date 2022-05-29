
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.LookLibraryControllerEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX
 */
public final class BrutalDeceiver extends CardImpl {

    public BrutalDeceiver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {1}: Look at the top card of your library.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new LookLibraryControllerEffect(), new GenericManaCost(1)));

        // {2}: Reveal the top card of your library. If it's a land card, {this} gets +1/+0 and gains first strike until end of turn.
        this.addAbility(new LimitedTimesPerTurnActivatedAbility(Zone.BATTLEFIELD, new BrutalDeceiverEffect(), new ManaCostsImpl<>("{2}")));
    }

    private BrutalDeceiver(final BrutalDeceiver card) {
        super(card);
    }

    @Override
    public BrutalDeceiver copy() {
        return new BrutalDeceiver(this);
    }
}

class BrutalDeceiverEffect extends OneShotEffect {

    public BrutalDeceiverEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "Reveal the top card of your library. If it's a land card, {this} gets +1/+0 and gains first strike until end of turn";
    }

    public BrutalDeceiverEffect(final BrutalDeceiverEffect effect) {
        super(effect);
    }

    @Override
    public BrutalDeceiverEffect copy() {
        return new BrutalDeceiverEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && sourceObject != null) {
            Cards cards = new CardsImpl();
            Card card = controller.getLibrary().getFromTop(game);
            if (card != null) {
                cards.add(card);
                controller.revealCards(sourceObject.getIdName(), cards, game);
                if (card.isLand(game)) {
                    game.addEffect(new BoostSourceEffect(1, 0, Duration.EndOfTurn), source);
                    game.addEffect(new GainAbilitySourceEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn), source);
                }
            }
            return true;
        }
        return false;
    }
}
