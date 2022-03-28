package mage.cards.a;

import java.util.List;
import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

/**
 *
 * @author weirddan455
 */
public final class ArmedAndArmored extends CardImpl {

    public ArmedAndArmored(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Vehicles you control become artifact creatures until end of turn.
        this.getSpellAbility().addEffect(new ArmedAndArmoredEffect());

        // Choose a Dwarf you control. Attach any number of Equipment you control to it.
        this.getSpellAbility().addEffect(new ArmedAndArmoredEquipEffect());
    }

    private ArmedAndArmored(final ArmedAndArmored card) {
        super(card);
    }

    @Override
    public ArmedAndArmored copy() {
        return new ArmedAndArmored(this);
    }
}

class ArmedAndArmoredEffect extends ContinuousEffectImpl {

    public ArmedAndArmoredEffect() {
        super(Duration.EndOfTurn, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.BecomeCreature);
        staticText = "Vehicles you control become artifact creatures until end of turn";
    }

    private ArmedAndArmoredEffect(final ArmedAndArmoredEffect effect) {
        super(effect);
    }

    @Override
    public ArmedAndArmoredEffect copy() {
        return new ArmedAndArmoredEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(source.getControllerId())) {
            if (permanent != null && permanent.hasSubtype(SubType.VEHICLE, game)) {
                if (sublayer == SubLayer.NA) {
                    permanent.addCardType(game, CardType.ARTIFACT);
                    permanent.addCardType(game, CardType.CREATURE);// TODO: Check if giving CREATURE Type is correct
                }
            }
        }
        return true;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }
}

class ArmedAndArmoredEquipEffect extends OneShotEffect {

    public ArmedAndArmoredEquipEffect() {
        super(Outcome.Benefit);
        staticText = "Choose a Dwarf you control. Attach any number of Equipment you control to it.";
    }

    private ArmedAndArmoredEquipEffect(final ArmedAndArmoredEquipEffect effect) {
        super(effect);
    }

    @Override
    public ArmedAndArmoredEquipEffect copy() {
        return new ArmedAndArmoredEquipEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        FilterControlledPermanent dwarfFilter = new FilterControlledPermanent(SubType.DWARF);
        List<Permanent> dwarves = game.getBattlefield().getAllActivePermanents(dwarfFilter, controller.getId(), game);

        FilterControlledPermanent equipmentFilter = new FilterControlledPermanent(SubType.EQUIPMENT);
        List<Permanent> equipment = game.getBattlefield().getAllActivePermanents(equipmentFilter, controller.getId(), game);

        if (!dwarves.isEmpty() && !equipment.isEmpty()) {
            TargetPermanent target = new TargetPermanent(0, 1, dwarfFilter, true);
            target.withChooseHint("dwarf to be equipped");
            controller.choose(outcome, target, source, game);
            Permanent dwarf = game.getPermanent(target.getFirstTarget());
            if (dwarf != null) {
                target = new TargetPermanent(0, Integer.MAX_VALUE, equipmentFilter, true);
                target.withChooseHint("equip to " + dwarf.getLogName());
                controller.choose(outcome, target, source, game);
                for (UUID targetId : target.getTargets()) {
                    dwarf.addAttachment(targetId, source, game);
                    game.informPlayers(game.getPermanent(targetId).getLogName() + " was attached to " + dwarf.getLogName());
                }
            }
        }
        return true;
    }
}
