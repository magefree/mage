
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksEachCombatStaticAbility;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DontUntapInControllersUntapStepSourceEffect;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author North
 */
public final class GalvanicJuggernaut extends CardImpl {

    public GalvanicJuggernaut(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{4}");
        this.subtype.add(SubType.JUGGERNAUT);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Galvanic Juggernaut attacks each turn if able.
        this.addAbility(new AttacksEachCombatStaticAbility());
        // Galvanic Juggernaut doesn't untap during your untap step.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new DontUntapInControllersUntapStepSourceEffect()));
        // Whenever another creature dies, untap Galvanic Juggernaut.
        this.addAbility(new DiesCreatureTriggeredAbility(new UntapSourceEffect(), false, true));
    }

    private GalvanicJuggernaut(final GalvanicJuggernaut card) {
        super(card);
    }

    @Override
    public GalvanicJuggernaut copy() {
        return new GalvanicJuggernaut(this);
    }
}
