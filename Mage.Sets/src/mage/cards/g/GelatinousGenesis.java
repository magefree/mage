
package mage.cards.g;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.token.OozeToken;

/**
 *
 * @author jeffwadsworth
 */
public final class GelatinousGenesis extends CardImpl {

    public GelatinousGenesis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{X}{X}{G}");


        // create X X/X green Ooze creature tokens.
        this.getSpellAbility().addEffect(new GelatinousGenesisEffect());
    }

    private GelatinousGenesis(final GelatinousGenesis card) {
        super(card);
    }

    @Override
    public GelatinousGenesis copy() {
        return new GelatinousGenesis(this);
    }
}

class GelatinousGenesisEffect extends OneShotEffect {

    public GelatinousGenesisEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "create X X/X green Ooze creature tokens";
    }

    public GelatinousGenesisEffect(GelatinousGenesisEffect ability) {
        super(ability);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int count = source.getManaCostsToPay().getX();

        OozeToken oozeToken = new OozeToken();
        oozeToken.setPower(count);
        oozeToken.setToughness(count);
        oozeToken.putOntoBattlefield(count, game, source, source.getControllerId());
        return true;
    }

    @Override
    public GelatinousGenesisEffect copy() {
        return new GelatinousGenesisEffect(this);
    }
}

