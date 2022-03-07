
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author spjspj
 */
public final class EmpyrealVoyager extends CardImpl {

    public EmpyrealVoyager(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}{U}");
        this.subtype.add(SubType.VEDALKEN);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // Whenever Empyreal Voyager deals combat damage to a player you get that many {E}
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new EmpyrealVoyagerEffect(), false, true));
    }

    private EmpyrealVoyager(final EmpyrealVoyager card) {
        super(card);
    }

    @Override
    public EmpyrealVoyager copy() {
        return new EmpyrealVoyager(this);
    }
}

class EmpyrealVoyagerEffect extends OneShotEffect {

    public EmpyrealVoyagerEffect() {
        super(Outcome.Benefit);
        this.staticText = "you get that many {E}";
    }

    public EmpyrealVoyagerEffect(final EmpyrealVoyagerEffect effect) {
        super(effect);
    }

    @Override
    public EmpyrealVoyagerEffect copy() {
        return new EmpyrealVoyagerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int amount = (Integer) getValue("damage");
        if (amount > 0) {
            Player controller = game.getPlayer(source.getControllerId());
            if (controller != null) {
                return new GetEnergyCountersControllerEffect(amount).apply(game, source);
            }
        }
        return false;
    }
}
