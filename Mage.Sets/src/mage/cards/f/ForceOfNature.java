
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public final class ForceOfNature extends CardImpl {

    public ForceOfNature(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}{G}{G}{G}");
        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(8);
        this.toughness = new MageInt(8);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // At the beginning of your upkeep, Force of Nature deals 8 damage to you unless you pay {G}{G}{G}{G}.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new ForceOfNatureEffect(), TargetController.YOU, false));

    }

    public ForceOfNature(final ForceOfNature card) {
        super(card);
    }

    @Override
    public ForceOfNature copy() {
        return new ForceOfNature(this);
    }
}

class ForceOfNatureEffect extends OneShotEffect {

    public ForceOfNatureEffect() {
        super(Outcome.Damage);
        this.staticText = "{this} deals 8 damage to you unless you pay {G}{G}{G}{G}";
    }

    public ForceOfNatureEffect(final ForceOfNatureEffect effect) {
        super(effect);
    }

    @Override
    public ForceOfNatureEffect copy() {
        return new ForceOfNatureEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Cost cost = new ManaCostsImpl("{G}{G}{G}{G}");
            String message = "Would you like to pay {G}{G}{G}{G} to prevent taking 8 damage from {this}?";
            if (!(controller.chooseUse(Outcome.Benefit, message, source, game)
                    && cost.pay(source, game, source.getSourceId(), controller.getId(), false, null))) {
                controller.damage(8, source.getSourceId(), game, false, true);
            }
            return true;
        }
        return false;
    }

}
