
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.DontUntapInControllersUntapStepSourceEffect;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author LoneFox
 *
 */
public final class DwarvenPatrol extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a nonred spell");

    static {
        filter.add(Predicates.not(new ColorPredicate(ObjectColor.RED)));
    }

    public DwarvenPatrol(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");
        this.subtype.add(SubType.DWARF);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // Dwarven Patrol doesn't untap during your untap step.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new DontUntapInControllersUntapStepSourceEffect()));
        // Whenever you cast a nonred spell, untap Dwarven Patrol.
        this.addAbility(new SpellCastControllerTriggeredAbility(new UntapSourceEffect(), filter, false));
    }

    private DwarvenPatrol(final DwarvenPatrol card) {
        super(card);
    }

    @Override
    public DwarvenPatrol copy() {
        return new DwarvenPatrol(this);
    }
}
