
package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.token.OozeToken;
import mage.util.CardUtil;

import java.util.UUID;

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

    GelatinousGenesisEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "create X X/X green Ooze creature tokens";
    }

    private GelatinousGenesisEffect(final GelatinousGenesisEffect ability) {
        super(ability);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int count = CardUtil.getSourceCostsTag(game, source, "X", 0);

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
