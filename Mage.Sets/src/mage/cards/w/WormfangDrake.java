
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.effects.common.ReturnFromExileForSourceEffect;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.util.CardUtil;

/**
 *
 * @author Temba21
 */
public final class WormfangDrake extends CardImpl {

    public WormfangDrake(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}");
        this.subtype.add(SubType.NIGHTMARE);
        this.subtype.add(SubType.DRAKE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Wormfang Drake enters the battlefield, sacrifice it unless you exile a creature you control other than Wormfang Drake.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new SacrificeSourceUnlessPaysEffect(new WormfangDrakeExileCost()), false));

        // When Wormfang Drake leaves the battlefield, return the exiled card to the battlefield under its owner's control.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new ReturnFromExileForSourceEffect(Zone.BATTLEFIELD), false));
    }

    public WormfangDrake(final WormfangDrake card) {
        super(card);
    }

    @Override
    public WormfangDrake copy() {
        return new WormfangDrake(this);
    }
}

class WormfangDrakeExileCost extends CostImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent();

    static {
        filter.add(new AnotherPredicate());
    }

    public WormfangDrakeExileCost() {
        this.addTarget(new TargetControlledCreaturePermanent(1, 1, filter, true));
        this.text = "Exile a creature you control other than {this}";
    }

    public WormfangDrakeExileCost(WormfangDrakeExileCost cost) {
        super(cost);
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana, Cost costToPay) {
        Player controller = game.getPlayer(controllerId);
        MageObject sourceObject = ability.getSourceObject(game);
        if (controller != null && sourceObject != null) {
            if (targets.choose(Outcome.Exile, controllerId, sourceId, game)) {
                UUID exileId = CardUtil.getExileZoneId(game, ability.getSourceId(), ability.getSourceObjectZoneChangeCounter());
                for (UUID targetId : targets.get(0).getTargets()) {
                    Permanent permanent = game.getPermanent(targetId);
                    if (permanent == null) {
                        return false;
                    }
                    paid |= controller.moveCardToExileWithInfo(permanent, exileId, sourceObject.getIdName() + " exiled permanents", sourceId, game, Zone.BATTLEFIELD, true);
                }
            }
        }
        return paid;
    }

    @Override
    public boolean canPay(Ability ability, UUID sourceId, UUID controllerId, Game game) {
        return targets.canChoose(controllerId, game);
    }

    @Override
    public WormfangDrakeExileCost copy() {
        return new WormfangDrakeExileCost(this);
    }
}
