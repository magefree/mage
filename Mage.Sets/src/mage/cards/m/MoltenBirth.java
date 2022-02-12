package mage.cards.m;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.token.RedElementalToken;
import mage.players.Player;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class MoltenBirth extends CardImpl {

    public MoltenBirth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{R}{R}");

        // Create two 1/1 red Elemental creature tokens. Then flip a coin. If you win the flip, return Molten Birth to its owner's hand.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new RedElementalToken(), 2));
        this.getSpellAbility().addEffect(new MoltenBirthEffect());
    }

    private MoltenBirth(final MoltenBirth card) {
        super(card);
    }

    @Override
    public MoltenBirth copy() {
        return new MoltenBirth(this);
    }
}

class MoltenBirthEffect extends OneShotEffect {

    MoltenBirthEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "Then flip a coin. If you win the flip, return {this} to its owner's hand";
    }

    private MoltenBirthEffect(final MoltenBirthEffect effect) {
        super(effect);
    }

    @Override
    public MoltenBirthEffect copy() {
        return new MoltenBirthEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        return controller != null
                && controller.flipCoin(source, game, true)
                && sourceObject instanceof Card
                && controller.moveCards((Card) sourceObject, Zone.HAND, source, game);
    }
}
