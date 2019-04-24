package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.token.MoltenBirthElementalToken;
import mage.players.Player;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class MoltenBirth extends CardImpl {

    public MoltenBirth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{R}{R}");

        // Create two 1/1 red Elemental creature tokens. Then flip a coin. If you win the flip, return Molten Birth to its owner's hand.
        this.getSpellAbility().addEffect(new MoltenBirthEffect());

    }

    public MoltenBirth(final MoltenBirth card) {
        super(card);
    }

    @Override
    public MoltenBirth copy() {
        return new MoltenBirth(this);
    }
}

class MoltenBirthEffect extends OneShotEffect {

    public MoltenBirthEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "Create two 1/1 red Elemental creature tokens. Then flip a coin. If you win the flip, return {this} to its owner's hand";
    }

    public MoltenBirthEffect(final MoltenBirthEffect effect) {
        super(effect);
    }

    @Override
    public MoltenBirthEffect copy() {
        return new MoltenBirthEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            MoltenBirthElementalToken token = new MoltenBirthElementalToken();
            token.putOntoBattlefield(2, game, source.getSourceId(), source.getControllerId());
            if (controller.flipCoin(source, game, true)) {
                Card molten = game.getCard(source.getSourceId());
                if (molten != null) {
                    molten.moveToZone(Zone.HAND, source.getSourceId(), game, true);
                    game.informPlayers(controller.getLogName() + " won the flip.  " + molten.getLogName() + " is returned to " + controller.getLogName() + "'s hand.");
                }
            }
            return true;
        }
        return false;
    }

}
