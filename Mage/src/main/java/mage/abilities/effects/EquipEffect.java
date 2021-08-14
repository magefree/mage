package mage.abilities.effects;

import mage.abilities.Ability;
import mage.abilities.effects.common.AttachEffect;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;

public class EquipEffect extends AttachEffect {

    public EquipEffect(Outcome outcome) {
        super(outcome, "Equip");
    }

    public EquipEffect(EquipEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        //301.5c An Equipment that’s also a creature can’t equip a creature. An Equipment that loses the subtype
        // “Equipment” can’t equip a creature. An Equipment can’t equip itself. An Equipment that equips an illegal or
        // nonexistent permanent becomes unattached from that permanent but remains on the battlefield. (This is a
        // state-based action. See rule 704.) An Equipment can’t equip more than one creature. If a spell or ability
        // would cause an Equipment to equip more than one creature, the Equipment’s controller chooses which creature
        // it equips.
        if (sourcePermanent != null && sourcePermanent.hasSubtype(SubType.EQUIPMENT, game) && !sourcePermanent.isCreature(game)) {
            return super.apply(game, source);
        }
        return false;
    }

    @Override
    public EquipEffect copy(){
        return new EquipEffect(this);
    }
}
