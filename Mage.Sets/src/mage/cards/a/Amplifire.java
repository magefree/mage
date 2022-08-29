package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.cards.*;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Amplifire extends CardImpl {

    public Amplifire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");

        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // At the beginning of your upkeep, reveal cards from the top of your library until you reveal a creature card.
        // Until your next turn, Amplifire's base power becomes twice that card's power and its base toughness becomes twice that card's toughness.
        // Put the revealed cards on the bottom of your library in a random order.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new AmplifireEffect(), TargetController.YOU, false
        ));
    }

    private Amplifire(final Amplifire card) {
        super(card);
    }

    @Override
    public Amplifire copy() {
        return new Amplifire(this);
    }
}

class AmplifireEffect extends OneShotEffect {

    AmplifireEffect() {
        super(Outcome.Benefit);
        staticText = "reveal cards from the top of your library until you reveal a creature card. " +
                "Until your next turn, {this}'s base power becomes twice that card's power " +
                "and its base toughness becomes twice that card's toughness. " +
                "Put the revealed cards on the bottom of your library in a random order.";
    }

    private AmplifireEffect(final AmplifireEffect effect) {
        super(effect);
    }

    @Override
    public AmplifireEffect copy() {
        return new AmplifireEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl();
        Card lastCard = null;
        for (Card card : player.getLibrary().getCards(game)) {
            if (card != null) {
                cards.add(card);
                if (card.isCreature(game)) {
                    lastCard = card;
                    break;
                }
            }
        }
        player.revealCards(source, cards, game);
        if (lastCard != null) {
            SetBasePowerToughnessSourceEffect setBasePowerToughnessSourceEffect = new SetBasePowerToughnessSourceEffect(
                    2*lastCard.getPower().getValue(),
                    2*lastCard.getToughness().getValue(),
                    Duration.UntilYourNextTurn,
                    SubLayer.SetPT_7b,
                    true
            );
            game.addEffect(setBasePowerToughnessSourceEffect, source);
        }
        player.putCardsOnBottomOfLibrary(cards, game, source, false);
        return true;
    }
}
