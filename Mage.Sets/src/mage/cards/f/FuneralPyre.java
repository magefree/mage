
package mage.cards.f;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.token.SpiritWhiteToken;
import mage.game.permanent.token.Token;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;
import mage.util.CardUtil;

/**
 *
 * @author jeffwadsworth
 */
public final class FuneralPyre extends CardImpl {

    public FuneralPyre(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}");
        

        // Exile target card from a graveyard. Its owner puts a 1/1 white Spirit creature token with flying onto the battlefield.
        this.getSpellAbility().addEffect(new FuneralPyreEffect());
        this.getSpellAbility().addTarget(new TargetCardInGraveyard());
        
    }

    public FuneralPyre(final FuneralPyre card) {
        super(card);
    }

    @Override
    public FuneralPyre copy() {
        return new FuneralPyre(this);
    }
}

class FuneralPyreEffect extends OneShotEffect {

    public FuneralPyreEffect() {
        super(Outcome.Benefit);
        this.staticText = "Exile target card from a graveyard. Its owner puts a 1/1 white Spirit creature token with flying onto the battlefield";
    }

    public FuneralPyreEffect(final FuneralPyreEffect effect) {
        super(effect);
    }

    @Override
    public FuneralPyreEffect copy() {
        return new FuneralPyreEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card exiledCard = game.getCard(source.getTargets().getFirstTarget());
        if (exiledCard != null) {
            UUID exileId = CardUtil.getExileZoneId(game, source.getSourceId(), 0);
            if (exiledCard.moveToExile(exileId, "Funeral Pyre", source.getSourceId(), game)) {
                Player owner = game.getPlayer(exiledCard.getOwnerId());
                if (owner != null) {
                    Token token = new SpiritWhiteToken();
                    return token.putOntoBattlefield(1, game, source.getSourceId(), owner.getId());
                }
            }
        }
        return false;
    }
}
