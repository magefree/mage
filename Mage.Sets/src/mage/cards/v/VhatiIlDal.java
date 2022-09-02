
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class VhatiIlDal extends CardImpl {

    public VhatiIlDal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}{G}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {tap}: Until end of turn, target creature has base power 1 or base toughness 1.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new VhatiIlDalEffect(), new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private VhatiIlDal(final VhatiIlDal card) {
        super(card);
    }

    @Override
    public VhatiIlDal copy() {
        return new VhatiIlDal(this);
    }
}

class VhatiIlDalEffect extends OneShotEffect {

    public VhatiIlDalEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "Until end of turn, target creature has base power 1 or base toughness 1";
    }

    public VhatiIlDalEffect(final VhatiIlDalEffect effect) {
        super(effect);
    }

    @Override
    public VhatiIlDalEffect copy() {
        return new VhatiIlDalEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            DynamicValue power = null;
            DynamicValue toughness = null;
            if (controller.chooseUse(outcome, "Set power? (otherwise toughness is set)", source, game)) {
                power = StaticValue.get(1);
            } else {
                toughness = StaticValue.get(1);
            }
            ContinuousEffect effect = new SetBasePowerToughnessTargetEffect(power, toughness, Duration.EndOfTurn);
            game.addEffect(effect, source);
            return true;
        }
        return false;
    }
}
