
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
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
 * @author Plopman
 */
public final class AuriokWindwalker extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Equipment you control");

    static {
        filter.add(SubType.EQUIPMENT.getPredicate());
    }

    public AuriokWindwalker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // {T}: Attach target Equipment you control to target creature you control.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AttachTargetEquipmentEffect(), new TapSourceCost());
        ability.addTarget(new TargetControlledPermanent(filter));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private AuriokWindwalker(final AuriokWindwalker card) {
        super(card);
    }

    @Override
    public AuriokWindwalker copy() {
        return new AuriokWindwalker(this);
    }
}

class AttachTargetEquipmentEffect extends OneShotEffect {

    public AttachTargetEquipmentEffect() {
        super(Outcome.BoostCreature);
        staticText = "Attach target Equipment you control to target creature you control";
    }

    private AttachTargetEquipmentEffect(final AttachTargetEquipmentEffect effect) {
        super(effect);
    }

    @Override
    public AttachTargetEquipmentEffect copy() {
        return new AttachTargetEquipmentEffect(this);
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
