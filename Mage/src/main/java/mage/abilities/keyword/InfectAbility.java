package mage.abilities.keyword;

import mage.abilities.MageSingleton;
import mage.abilities.StaticAbility;
import mage.abilities.icon.abilities.InfectAbilityIcon;
import mage.constants.Zone;

import java.io.ObjectStreamException;

/**
 * 702.87. Infect
 * <p>
 * 702.87a. Infect is a static ability.
 * <p>
 * 702.87b. Damage dealt to a player by a source with infect doesn't cause that player to lose life. Rather, it causes the player to get that many poison counters. See rule 119.3.
 * <p>
 * 702.87c. Damage dealt to a creature by a source with infect isn't marked on that creature. Rather, it causes that many -1/-1 counters to be put on that creature. See rule 119.3.
 * <p>
 * 702.87d. If a permanent leaves the battlefield before an effect causes it to deal damage, its last known information
 * (Last Known Information: Information about an object that's no longer in the zone it's expected to be in, or information about a player that's no longer in the game. This information captures that object's last existence in that zone or that player's last existence in the game....)
 * 112.7a. Once activated or triggered, an ability exists on the stack independently of its source. Destruction or removal of the source after that time won't affect the ability. Note that some abilities cause a source to do something (for example, "Prodigal Sorcerer deals 1 damage...
 * 608.2b. If the spell or ability specifies targets, it checks whether the targets are still legal. A target that's no longer in the zone it was in when it was targeted is illegal. Other changes to the game state may cause a target to no longer be legal; for example, its...
 * 608.2g. If an effect requires information from the game (such as the number of creatures on the battlefield), the answer is determined only once, when the effect is applied. If the effect requires information from a specific object, including the source of the ability itself or a...
 * 800.4f. If an effect requires information about a specific player, the effect uses the current information about that player if they are still in the game; otherwise, the effect uses the last known information about that player before they left the game.
 * is used to determine whether it had infect.
 * <p>
 * 702.87e. The infect rules function no matter what zone an object with infect deals damage from.
 * <p>
 * 702.87f. Multiple instances of infect on the same object are redundant.
 *
 * @author nantuko
 */
public class InfectAbility extends StaticAbility implements MageSingleton {

    private static final InfectAbility instance;

    static {
        instance = new InfectAbility();
        instance.addIcon(InfectAbilityIcon.instance);
    }

    private Object readResolve() throws ObjectStreamException {
        return instance;
    }

    public static InfectAbility getInstance() {
        return instance;
    }

    private InfectAbility() {
        super(Zone.ALL, null);
    }

    @Override
    public String getRule() {
        return "infect <i>(This creature deals damage to creatures in the form of -1/-1 counters and to players in the form of poison counters.)</i>";
    }

    @Override
    public InfectAbility copy() {
        return instance;
    }

}
