package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public enum CastFromGraveyardSourceCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return Optional
                .ofNullable(source)
                .map(Ability::getSourceId)
                .map(sourceId -> getSpell(game, sourceId))
                .map(Spell::getFromZone)
                .map(Zone.GRAVEYARD::match)
                .orElse(false);
    }

    private static Spell getSpell(Game game, UUID sourceId) {
        Spell spell = game.getSpell(sourceId);
        if (spell != null) {
            return spell;
        }
        // permanent's abilities (e.g. ETB triggers) must look for the spell it was cast as
        Permanent permanent = game.getPermanentEntering(sourceId);
        int zccDiff = 0;
        if (permanent == null) {
            permanent = game.getPermanentOrLKIBattlefield(sourceId); // can be already removed from battlefield so also check LKI
            zccDiff = -1;
        }
        if (permanent == null) {
            return null;
        }
        spell = game.getSpellOrLKIStack(permanent);
        if (spell == null || spell.getZoneChangeCounter(game) != permanent.getZoneChangeCounter(game) + zccDiff) {
            return null;
        }
        return spell;
    }

    @Override
    public String toString() {
        return "this spell was cast from a graveyard";
    }
}
