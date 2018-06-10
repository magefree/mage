
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

/**
 *
 * @author North
 */
public final class BeguilerOfWills extends CardImpl {

    public BeguilerOfWills(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}{U}");
        this.subtype.add(SubType.HUMAN, SubType.WIZARD);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {tap}: Gain control of target creature with power less than or equal to the number of creatures you control.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new GainControlTargetEffect(Duration.Custom),
                new TapSourceCost());
        ability.addTarget(new BeguilerOfWillsTarget());
        this.addAbility(ability);
    }

    public BeguilerOfWills(final BeguilerOfWills card) {
        super(card);
    }

    @Override
    public BeguilerOfWills copy() {
        return new BeguilerOfWills(this);
    }
}

class BeguilerOfWillsTarget extends TargetPermanent {

    public BeguilerOfWillsTarget() {
        super(new FilterCreaturePermanent("creature with power less than or equal to the number of creatures you control"));
    }

    public BeguilerOfWillsTarget(final BeguilerOfWillsTarget target) {
        super(target);
    }

    @Override
    public BeguilerOfWillsTarget copy() {
        return new BeguilerOfWillsTarget(this);
    }

    @Override
    public boolean canTarget(UUID controllerId, UUID id, Ability source, Game game) {
        Permanent permanent = game.getPermanent(id);
        int count = game.getBattlefield().countAll(this.filter, source.getControllerId(), game);

        if (permanent != null && permanent.getPower().getValue() <= count) {
            return super.canTarget(controllerId, id, source, game);
        }
        return false;
    }
}
