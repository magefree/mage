
package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetPlayerOrPlaneswalker;

import java.util.UUID;

/**
 * @author nantuko
 */
public final class ScepterOfEmpires extends CardImpl {

    public ScepterOfEmpires(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // {tap}: Scepter of Empires deals 1 damage to target player. It deals 3 damage to that player instead if you control artifacts named Crown of Empires and Throne of Empires.
        Ability ability = new SimpleActivatedAbility(new ScepterOfEmpiresEffect(), new TapSourceCost());
        ability.addTarget(new TargetPlayerOrPlaneswalker());
        this.addAbility(ability);
    }

    private ScepterOfEmpires(final ScepterOfEmpires card) {
        super(card);
    }

    @Override
    public ScepterOfEmpires copy() {
        return new ScepterOfEmpires(this);
    }
}

class ScepterOfEmpiresEffect extends OneShotEffect {

    ScepterOfEmpiresEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "{this} deals 1 damage to target player or planeswalker. It deals 3 damage instead if you control artifacts named Crown of Empires and Throne of Empires";
    }

    private ScepterOfEmpiresEffect(final ScepterOfEmpiresEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean throne = false;
        boolean crown = false;
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(source.getControllerId())) {
            if (permanent.getName().equals("Throne of Empires")) {
                throne = true;
            } else if (permanent.getName().equals("Crown of Empires")) {
                crown = true;
            }
            if (throne && crown) {
                break;
            }
        }
        int amount = throne && crown ? 3 : 1;
        return game.damagePlayerOrPermanent(source.getFirstTarget(), amount, source.getSourceId(), source, game, false, true) > 0;
    }

    @Override
    public ScepterOfEmpiresEffect copy() {
        return new ScepterOfEmpiresEffect(this);
    }
}
