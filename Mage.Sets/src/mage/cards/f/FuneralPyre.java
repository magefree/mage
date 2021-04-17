package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.token.SpiritWhiteToken;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class FuneralPyre extends CardImpl {

    public FuneralPyre(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}");

        // Exile target card from a graveyard. Its owner puts a 1/1 white Spirit creature token with flying onto the battlefield.
        this.getSpellAbility().addEffect(new FuneralPyreEffect());
        this.getSpellAbility().addTarget(new TargetCardInGraveyard());
    }

    private FuneralPyre(final FuneralPyre card) {
        super(card);
    }

    @Override
    public FuneralPyre copy() {
        return new FuneralPyre(this);
    }
}

class FuneralPyreEffect extends OneShotEffect {

    FuneralPyreEffect() {
        super(Outcome.Benefit);
        this.staticText = "Exile target card from a graveyard. " +
                "Its owner creates a 1/1 white Spirit creature token with flying";
    }

    private FuneralPyreEffect(final FuneralPyreEffect effect) {
        super(effect);
    }

    @Override
    public FuneralPyreEffect copy() {
        return new FuneralPyreEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card exiledCard = game.getCard(source.getFirstTarget());
        if (player == null || exiledCard == null) {
            return false;
        }
        Player owner = game.getPlayer(exiledCard.getOwnerId());
        player.moveCards(exiledCard, Zone.EXILED, source, game);
        if (owner != null) {
            new SpiritWhiteToken().putOntoBattlefield(1, game, source, owner.getId());
        }
        return true;
    }
}
