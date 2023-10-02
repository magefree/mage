
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author North
 */
public final class BrassSquire extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Equipment you control");

    static {
        filter.add(SubType.EQUIPMENT.getPredicate());
    }

    public BrassSquire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{3}");
        this.subtype.add(SubType.MYR);

        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // {tap}: Attach target Equipment you control to target creature you control.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new EquipEffect(), new TapSourceCost());
        ability.addTarget(new TargetControlledPermanent(filter));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private BrassSquire(final BrassSquire card) {
        super(card);
    }

    @Override
    public BrassSquire copy() {
        return new BrassSquire(this);
    }
}

class EquipEffect extends OneShotEffect {

    public EquipEffect() {
        super(Outcome.BoostCreature);
        staticText = "Attach target Equipment you control to target creature you control";
    }

    private EquipEffect(final EquipEffect effect) {
        super(effect);
    }

    @Override
    public EquipEffect copy() {
        return new EquipEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent equipment = game.getPermanent(source.getFirstTarget());
        Permanent creature = game.getPermanent(source.getTargets().get(1).getFirstTarget());
        if (creature != null && equipment != null) {
            return creature.addAttachment(equipment.getId(), source, game);
        }
        return false;
    }
}
