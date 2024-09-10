
package mage.abilities.keyword;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DevourEffect;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;

/**
 * 502.82. Devour
 *
 * 502.82a Devour is a static ability. "Devour N" means "As this object comes
 * into play, you may sacrifice any number of creatures. This permanent comes
 * into play with N +1/+1 counters on it for each creature sacrificed this way."
 *
 * 502.82b Some objects have abilities that refer to the number of creatures the
 * permanent devoured. "It devoured" means "sacrificed as a result of its devour
 * ability as it came into play."
 *
 * Devour appears only on creature cards.
 *
 * A creature with devour can devour other creatures no matter how it comes into
 * play.
 *
 * You may choose to not sacrifice any creatures.
 *
 * If you play a creature with devour as a spell, you choose how many and which
 * creatures to devour as part of the resolution of that spell. (It can't be
 * countered at this point.) The same is true of a spell or ability that lets
 * you put a creature with devour into play.
 *
 * You may sacrifice only creatures that are already in play. If a creature with
 * devour and another creature are coming into play under your control at the
 * same time, the creature with devour can't devour that other creature. The
 * creature with devour also can't devour itself.
 *
 * If multiple creatures with devour are coming into play under your control at
 * the same time, you may use each one's devour ability. A creature you already
 * control can be devoured by only one of them, however. (In other words, you
 * can't sacrifice the same creature to satisfy multiple devour abilities.) All
 * creatures devoured this way are sacrificed at the same time.
 *
 * @author LevelX2, Susucr
 */
public class DevourAbility extends SimpleStaticAbility {

    private static final FilterControlledPermanent filterCreature = new FilterControlledCreaturePermanent("creature");

    // Integer.MAX_VALUE is a special value
    // for "devour X, where X is the number of devored permanents"
    // see DevourEffect for the full details.
    public static DevourAbility DevourX() {
        return new DevourAbility(Integer.MAX_VALUE);
    }

    public DevourAbility(int devourFactor) {
        this(devourFactor, filterCreature);
    }

    public DevourAbility(int devourFactor, FilterControlledPermanent filterDevoured) {
        super(Zone.ALL, new DevourEffect(devourFactor, filterDevoured));
    }

    private DevourAbility(final DevourAbility ability) {
        super(ability);
    }

    @Override
    public DevourAbility copy() {
        return new DevourAbility(this);
    }
}
