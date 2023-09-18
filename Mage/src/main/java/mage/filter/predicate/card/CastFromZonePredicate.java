package mage.filter.predicate.card;

import mage.MageObject;
import mage.constants.Zone;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.stack.Spell;

/**
 * @author LevelX2
 */
public class CastFromZonePredicate implements Predicate<MageObject> {

    private final Zone zone;

    public CastFromZonePredicate(Zone zone) {
        this.zone = zone;
    }

    @Override
    public boolean apply(MageObject input, Game game) {
        if (input instanceof Spell) {
            return zone.match(((Spell) input).getFromZone());
        } else {
            return zone.match(game.getState().getZone(input.getId()));
        }
    }

    @Override
    public String toString() {
        return "Spells you cast from your " + zone.toString();
    }
}
