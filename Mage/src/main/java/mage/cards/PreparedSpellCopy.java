package mage.cards;

import mage.abilities.SpellAbility;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * A standalone card copy whose normal characteristics are the prepare spell's.
 *
 * @author nandmp
 */
public final class PreparedSpellCopy extends CardImpl {

    private final UUID preparedPermanentId;

    PreparedSpellCopy(PrepareSpellCard characteristics, UUID preparedPermanentId) {
        super(characteristics);
        SpellAbility standaloneAbility
                = ((SpellOptionCardSpellAbility) characteristics.getSpellAbility()).copyForStandaloneCard();
        replaceSpellAbility(standaloneAbility);
        spellAbility = standaloneAbility;
        this.preparedPermanentId = preparedPermanentId;
    }

    private PreparedSpellCopy(final PreparedSpellCopy card) {
        super(card);
        this.preparedPermanentId = card.preparedPermanentId;
    }

    @Override
    public PreparedSpellCopy copy() {
        return new PreparedSpellCopy(this);
    }

    /**
     * Prepared permanent of this copy or null. The permanent must keep the prepared designation that created
     * this copy (a permanent after leaving and returning to the battlefield is a new object with a new copy).
     */
    public Permanent getPreparedPermanent(Game game) {
        Permanent permanent = game.getPermanent(preparedPermanentId);
        if (permanent != null && permanent.isPrepared() && getId().equals(permanent.getPreparedSpellCopyId())) {
            return permanent;
        }
        return null;
    }

    @Override
    public boolean cast(Game game, Zone fromZone, SpellAbility ability, UUID controllerId) {
        boolean cast = super.cast(game, fromZone, ability, controllerId);
        if (cast) {
            // Rule 722.3c removes the designation only after the casting process completes.
            Permanent permanent = getPreparedPermanent(game);
            if (permanent != null) {
                permanent.setPrepared(false, game);
            }
        }
        return cast;
    }
}
