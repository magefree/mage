package mage.cards.v;

import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author muz
 */
public final class VowToErebor extends CardImpl {

    public VowToErebor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Untap target creature you control. It gets +2/+2 until end of turn. If it's a Dwarf, you may attach an Equipment you control to it.
        this.getSpellAbility().addEffect(new UntapTargetEffect().setText("Untap target creature you control"));
        this.getSpellAbility().addEffect(new BoostTargetEffect(2, 2).setText("It gets +2/+2 until end of turn"));
        this.getSpellAbility().addEffect(new VowToEreborEffect());
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
    }

    private VowToErebor(final VowToErebor card) {
        super(card);
    }

    @Override
    public VowToErebor copy() {
        return new VowToErebor(this);
    }
}

class VowToEreborEffect extends OneShotEffect {

    VowToEreborEffect() {
        super(Outcome.Benefit);
        staticText = "If it's a Dwarf, you may attach an Equipment you control to it.";
    }

    private VowToEreborEffect(final VowToEreborEffect effect) {
        super(effect);
    }

    @Override
    public VowToEreborEffect copy() {
        return new VowToEreborEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        Permanent creature = game.getPermanent(this.getTargetPointer().getFirst(game, source));
        if (creature == null || !creature.hasSubtype(SubType.DWARF, game)) {
            return true;
        }

        List<Permanent> equipments = game.getBattlefield().getAllActivePermanents(
            StaticFilters.FILTER_CONTROLLED_PERMANENT_EQUIPMENT,
            controller.getId(), game);
        if (equipments.isEmpty()) {
            return true;
        }

        if (!controller.chooseUse(Outcome.Benefit, "Attach an Equipment you control to " + creature.getLogName() + "?", source, game)) {
            return true;
        }

        TargetPermanent target = new TargetPermanent(0, 1, StaticFilters.FILTER_CONTROLLED_PERMANENT_EQUIPMENT, true);
        target.withChooseHint("equip to " + creature.getLogName());
        controller.choose(outcome, target, source, game);
        UUID equipmentId = target.getFirstTarget();
        if (equipmentId != null) {
            creature.addAttachment(equipmentId, source, game);
        }
        return true;
    }
}
