package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class MadcapExperiment extends CardImpl {

    public MadcapExperiment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}");

        // Reveal cards from the top of your library until you reveal an artifact card. Put that card onto the battlefield and the rest on the bottom of your library in a random order. Madcap Experiment deals damage to you equal to the number of cards revealed this way.
        this.getSpellAbility().addEffect(new MadcapExperimentEffect());
    }

    private MadcapExperiment(final MadcapExperiment card) {
        super(card);
    }

    @Override
    public MadcapExperiment copy() {
        return new MadcapExperiment(this);
    }
}

class MadcapExperimentEffect extends OneShotEffect {

    public MadcapExperimentEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "Reveal cards from the top of your library until you reveal an artifact card. Put that card onto the battlefield and the rest on the bottom of your library in a random order. {this} deals damage to you equal to the number of cards revealed this way";
    }

    private MadcapExperimentEffect(final MadcapExperimentEffect effect) {
        super(effect);
    }

    @Override
    public MadcapExperimentEffect copy() {
        return new MadcapExperimentEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            CardsImpl toReveal = new CardsImpl();
            Card toBattlefield = null;
            for (Card card : controller.getLibrary().getCards(game)) {
                toReveal.add(card);
                game.fireUpdatePlayersEvent();
                if (card.isArtifact(game)) {
                    toBattlefield = card;

                    break;
                }
            }
            controller.revealCards(source, toReveal, game);
            controller.moveCards(toBattlefield, Zone.BATTLEFIELD, source, game);
            int damage = toReveal.size();
            toReveal.remove(toBattlefield);
            controller.putCardsOnBottomOfLibrary(toReveal, game, source, false);
            controller.damage(damage, source.getSourceId(), source, game);

            return true;
        }
        return false;
    }
}
