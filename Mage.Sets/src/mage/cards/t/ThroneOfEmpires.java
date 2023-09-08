
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.SoldierToken;
import mage.game.permanent.token.Token;

/**
 * @author nantuko
 */
public final class ThroneOfEmpires extends CardImpl {

    public ThroneOfEmpires(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{4}");

        // {1}, {tap}: Create a 1/1 white Soldier creature token. Create five of those tokens instead if you control artifacts named Crown of Empires and Scepter of Empires.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ThroneOfEmpiresEffect(), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private ThroneOfEmpires(final ThroneOfEmpires card) {
        super(card);
    }

    @Override
    public ThroneOfEmpires copy() {
        return new ThroneOfEmpires(this);
    }
}

class ThroneOfEmpiresEffect extends OneShotEffect {

    public ThroneOfEmpiresEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "Create a 1/1 white Soldier creature token. Create five of those tokens instead if you control artifacts named Crown of Empires and Scepter of Empires";
    }

    private ThroneOfEmpiresEffect(final ThroneOfEmpiresEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean scepter = false;
        boolean crown = false;
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(source.getControllerId())) {
            if (permanent.getName().equals("Scepter of Empires")) {
                scepter = true;
            } else if (permanent.getName().equals("Crown of Empires")) {
                crown = true;
            }
            if (scepter && crown) break;
        }
        Token soldier = new SoldierToken();
        int count = scepter && crown ? 5 : 1;
        soldier.putOntoBattlefield(count, game, source, source.getControllerId());
        return false;
    }

    @Override
    public ThroneOfEmpiresEffect copy() {
        return new ThroneOfEmpiresEffect(this);
    }
}
