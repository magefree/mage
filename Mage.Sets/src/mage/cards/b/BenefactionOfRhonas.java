package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardAndOrCardInLibrary;

import java.util.UUID;

/**
 *
 * @author fireshoes
 */
public final class BenefactionOfRhonas extends CardImpl {

    public BenefactionOfRhonas(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}");

        // Reveal the top five cards of your library. You may put a creature card and/or enchantment card from among them into your hand. Put the rest into your graveyard.
        getSpellAbility().addEffect(new BenefactionOfRhonasEffect());
    }

    private BenefactionOfRhonas(final BenefactionOfRhonas card) {
        super(card);
    }

    @Override
    public BenefactionOfRhonas copy() {
        return new BenefactionOfRhonas(this);
    }
}

class BenefactionOfRhonasEffect extends OneShotEffect {

    BenefactionOfRhonasEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Reveal the top five cards of your library. " +
                "You may put a creature card and/or an enchantment card from among them into your hand. " +
                "Put the rest into your graveyard";
    }

    private BenefactionOfRhonasEffect(final BenefactionOfRhonasEffect effect) {
        super(effect);
    }

    @Override
    public BenefactionOfRhonasEffect copy() {
        return new BenefactionOfRhonasEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, 5));
        player.revealCards(source, cards, game);
        TargetCard target = new TargetCardAndOrCardInLibrary(CardType.CREATURE, CardType.ENCHANTMENT);
        player.choose(outcome, cards, target, source, game);
        Cards toHand = new CardsImpl();
        toHand.addAll(target.getTargets());
        player.moveCards(toHand, Zone.HAND, source, game);
        cards.removeAll(toHand);
        player.moveCards(cards, Zone.GRAVEYARD, source, game);
        return true;
    }
}
