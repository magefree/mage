package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author notgreat
 */
public final class BattlefieldImprovisation extends CardImpl {

    public BattlefieldImprovisation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Target creature gets +2/+2 until end of turn. If that creature is attacking, you may attach any number of Equipment you control to it.
        this.getSpellAbility().addEffect(new BoostTargetEffect(2, 2));
        this.getSpellAbility().addEffect(new BattlefieldImprovisationEquipEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private BattlefieldImprovisation(final BattlefieldImprovisation card) {
        super(card);
    }

    @Override
    public BattlefieldImprovisation copy() {
        return new BattlefieldImprovisation(this);
    }
}

// Based on Armed And Armored
class BattlefieldImprovisationEquipEffect extends OneShotEffect {

    BattlefieldImprovisationEquipEffect() {
        super(Outcome.Benefit);
        staticText = "If that creature is attacking, you may attach any number of Equipment you control to it.";
    }

    private BattlefieldImprovisationEquipEffect(final BattlefieldImprovisationEquipEffect effect) {
        super(effect);
    }

    @Override
    public BattlefieldImprovisationEquipEffect copy() {
        return new BattlefieldImprovisationEquipEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        FilterControlledPermanent equipmentFilter = new FilterControlledPermanent(SubType.EQUIPMENT);
        Permanent attacker = game.getPermanent(this.getTargetPointer().getFirst(game, source));
        if (attacker != null && attacker.isAttacking()) {
            Target target = new TargetPermanent(0, Integer.MAX_VALUE, equipmentFilter, true);
            target.withChooseHint("equip to " + attacker.getLogName());
            controller.choose(outcome, target, source, game);
            for (UUID targetId : target.getTargets()) {
                attacker.addAttachment(targetId, source, game);
                game.informPlayers(game.getPermanent(targetId).getLogName() + " was attached to " + attacker.getLogName());
            }
        }
        return true;
    }
}
