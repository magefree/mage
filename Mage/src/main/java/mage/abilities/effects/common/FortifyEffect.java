package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;

public class FortifyEffect extends AttachEffect{

    public FortifyEffect(Outcome outcome) {
        super(outcome, "Fortify");
    }

    public FortifyEffect(FortifyEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        //Some artifacts have the subtype “Fortification.” A Fortification can be attached to a land. It can’t legally
        // be attached to an object that isn’t a land. Fortification’s analog to the equip keyword ability is the
        // fortify keyword ability. Rules 301.5a–e apply to Fortifications in relation to lands just as they apply to
        // Equipment in relation to creatures, with one clarification relating to rule 301.5c: a Fortification that’s
        // also a creature (not a land) can’t fortify a land. (See rule 702.66, “Fortify.”)
        if (sourcePermanent != null && sourcePermanent.hasSubtype(SubType.FORTIFICATION, game) && !sourcePermanent.isCreature(game)
                && !sourcePermanent.isLand(game)) {
            return super.apply(game, source);
        }
        return false;
    }

    @Override
    public FortifyEffect copy(){
        return new FortifyEffect(this);
    }
}
