
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author fireshoes
 */
public final class VaporousDjinn extends CardImpl {

    public VaporousDjinn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}{U}");
        this.subtype.add(SubType.DJINN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // At the beginning of your upkeep, Vaporous Djinn phases out unless you pay {U}{U}.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new VaporousDjinnEffect(), TargetController.YOU, false));
    }

    private VaporousDjinn(final VaporousDjinn card) {
        super(card);
    }

    @Override
    public VaporousDjinn copy() {
        return new VaporousDjinn(this);
    }
}

class VaporousDjinnEffect extends OneShotEffect {

    public VaporousDjinnEffect() {
        super(Outcome.Damage);
        this.staticText = "{this} phases out unless you pay {U}{U}";
    }

    public VaporousDjinnEffect(final VaporousDjinnEffect effect) {
        super(effect);
    }

    @Override
    public VaporousDjinnEffect copy() {
        return new VaporousDjinnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Cost cost = new ManaCostsImpl<>("{U}{U}");
            String message = "Pay {U}{U} to prevent this permanent from phasing out?";
            if (!(controller.chooseUse(Outcome.Benefit, message, source, game)
                    && cost.pay(source, game, source, controller.getId(), false, null))) {
                permanent.phaseOut(game);
            }
            return true;
        }
        return false;
    }

}
